package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 20:28
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.*;
import ru.forprogr.hw.hw04autolog.descriptions.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class LogMethodsCreator {
	final private ClassWriter classWriter;
	final private ClassDescription classDescription;
	private Handle concatHandle;

	public LogMethodsCreator(ClassWriter p_classWriter, ClassDescription p_classDescription){
		classWriter = p_classWriter;
		classDescription = p_classDescription;
		concatHandle = null;
	}

	private MethodVisitor getNewMethod(MethodDescription p_methodDescription){
		return classWriter.visitMethod(p_methodDescription.getMethodAccess()
									, p_methodDescription.getMethodName()
									, p_methodDescription.getMethodDescriptor()
									, p_methodDescription.getMethodSignature()
									, null);
	}

	private Handle getConcatHandle(){
		if (concatHandle == null){

			String owner = Type.getInternalName(java.lang.invoke.StringConcatFactory.class);

			String descriptor = MethodType.methodType(CallSite.class
					, MethodHandles.Lookup.class
					, String.class
					, MethodType.class
					, String.class
					, Object[].class).toMethodDescriptorString();

			concatHandle = new Handle(H_INVOKESTATIC,owner,"makeConcatWithConstants",descriptor,false);
		}

		return concatHandle;
	}

	private int getOpcodeWithType(String p_paramType,int p_iOpcode){
		return Type.getType(p_paramType).getOpcode(p_iOpcode);
	}

	private void runVarInsnTypeVariable(int p_opcode,MethodParamDescription p_varDescription, MethodVisitor p_newMethod){
		int opcode = getOpcodeWithType(p_varDescription.getParamType(), p_opcode);

		p_newMethod.visitVarInsn(opcode,p_varDescription.getParamIndex());
	}

	private void runInsnTypeVariable(int p_opcode,MethodParamDescription p_varDescription, MethodVisitor p_newMethod){
		int opcode = getOpcodeWithType(p_varDescription.getParamType(), p_opcode);

		p_newMethod.visitInsn(opcode);
	}

	private void loadMethodParams(MethodDescription p_methodDescription,MethodVisitor p_newMethod){
		for(MethodParamDescription paramDescription : p_methodDescription.getMethodParams()){
			loadValueFromVariable(paramDescription,p_newMethod);
		}
	}

	private void printlnVariable(MethodParamDescription p_varDescription
								, MethodVisitor p_newMethod
								, String p_beforeText){

		p_newMethod.visitFieldInsn(Opcodes.GETSTATIC
				, "java/lang/System"
				, "out"
				, "Ljava/io/PrintStream;");

		loadValueFromVariable(p_varDescription,p_newMethod);

		p_newMethod.visitInvokeDynamicInsn("makeConcatWithConstants"
				, "("+p_varDescription.getParamType()+")Ljava/lang/String;"
				, getConcatHandle()
				, p_beforeText+"\u0001");

		p_newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
				, "java/io/PrintStream"
				, "println"
				, "(Ljava/lang/String;)V"
				, false);
	}

	private void printlnMethodParams(MethodDescription p_methodDescription
									,MethodVisitor p_newMethod
									,String p_beforeText){
		if (p_methodDescription.isHaveParams()){
			for(MethodParamDescription paramDescription : p_methodDescription.getMethodParams()){
				if(paramDescription.getParamIndex() != 0){
					printlnVariable(paramDescription
									, p_newMethod
									,p_beforeText + paramDescription.getParamName()+" = ");
				}
			}
		} else {
			printlnString(p_beforeText + "Method not have params" ,p_newMethod);
		}
	}


	private void printlnString(String p_string ,MethodVisitor p_newMethod){
		p_newMethod.visitFieldInsn(Opcodes.GETSTATIC
				, "java/lang/System"
				, "out"
				, "Ljava/io/PrintStream;");

		p_newMethod.visitLdcInsn(p_string);

		p_newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
				, "java/io/PrintStream"
				, "println"
				, "(Ljava/lang/String;)V"
				, false);
	}

	private void addNewVariable(MethodParamDescription p_varDescription,MethodVisitor p_newMethod){
		p_newMethod.visitLocalVariable(p_varDescription.getParamName()
				, p_varDescription.getParamType()
				, p_varDescription.getSignature()
				, p_varDescription.getStartLabel()
				, p_varDescription.getEndLabel()
				, p_varDescription.getParamIndex());
	}

	private void storeValueInVariable(MethodParamDescription p_varDescription,MethodVisitor p_newMethod){
		runVarInsnTypeVariable(Opcodes.ISTORE,p_varDescription,p_newMethod);
	}

	private void loadValueFromVariable(MethodParamDescription p_varDescription, MethodVisitor p_newMethod){
		runVarInsnTypeVariable(Opcodes.ILOAD,p_varDescription,p_newMethod);
	}


	private void returnFromVariable(MethodParamDescription p_varDescription, MethodVisitor p_newMethod){
		loadValueFromVariable(p_varDescription,p_newMethod);

		runInsnTypeVariable(Opcodes.IRETURN,p_varDescription,p_newMethod);
	}

	private void addProxedMethod(MethodDescription p_methodDescription, MethodVisitor p_newMethod){
		loadMethodParams(p_methodDescription,p_newMethod);

		p_newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
				, classDescription.getClassName()
				, p_methodDescription.getMethodProxyName()
				, p_methodDescription.getMethodDescriptor()
				, false);
	}

	private void returnFromMethod(String p_methodName
									,MethodDescription p_methodDescription
									,MethodVisitor p_newMethod){
		if (p_methodDescription.isHaveReturnParam()) {

			addNewVariable(p_methodDescription.getReturnMethodParam(),p_newMethod);

			storeValueInVariable(p_methodDescription.getReturnMethodParam(),p_newMethod);

			printlnVariable(p_methodDescription.getReturnMethodParam()
					, p_newMethod
					, "\t<"+p_methodName+" - logged RETURN> return = ");

			returnFromVariable(p_methodDescription.getReturnMethodParam(),p_newMethod);

		} else {
			printlnString("\t<"+p_methodName+" - logged RETURN> return = VOID",p_newMethod);
			p_newMethod.visitInsn(Opcodes.RETURN);
		}

		p_newMethod.visitMaxs(p_methodDescription.getMethodMaxStack(), p_methodDescription.getMethodMaxLocals());
		p_newMethod.visitEnd();
	}

	public void createLogMethods(){
		for(MethodDescription methodDescription : classDescription.getMethods()){

			MethodVisitor newMethod = getNewMethod(methodDescription);

			newMethod.visitCode();

			String methodName = "METHOD "+methodDescription.getMethodName();
			printlnString("\n["+methodName+"]",newMethod);

			printlnMethodParams(methodDescription,newMethod,"\t<"+methodName+" - logged PARAM> ");

			addProxedMethod(methodDescription,newMethod);

			returnFromMethod(methodName,methodDescription,newMethod);
		}
	}

}
