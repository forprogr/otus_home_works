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
	private ClassWriter classWriter;
	private ClassDescription classDescription;

	public LogMethodsCreator(ClassWriter p_classWriter, ClassDescription p_classDescription){
		classWriter = p_classWriter;
		classDescription = p_classDescription;
		createLogMethods();

	}



	private MethodVisitor getNewMethod(MethodDescription p_methodDescription){
		return classWriter.visitMethod(p_methodDescription.getMethodAccess()
									, p_methodDescription.getMethodName()
									, p_methodDescription.getMethodDescriptor()
									, p_methodDescription.getMethodSignature()
									, null);
	}

	private int getOpcodeWithType(String p_paramType,int p_iOpcode){
		return Type.getType(p_paramType).getOpcode(p_iOpcode);
	}

	private void loadParam(MethodParamDescription p_paramDescription, MethodVisitor p_newMethod){
		int loadOpcode = getOpcodeWithType(p_paramDescription.getParamType(), Opcodes.ILOAD);

		p_newMethod.visitVarInsn(loadOpcode,p_paramDescription.getParamIndex());
	}

	private void loadMethodParams(MethodDescription p_methodDescription,MethodVisitor p_newMethod){
		for(MethodParamDescription paramDescription : p_methodDescription.getMethodParams()){
			loadParam(paramDescription,p_newMethod);
		}
	}

	private void printLogParam(MethodParamDescription p_paramDescription
								, MethodVisitor p_newMethod
								, Handle p_concatFunctionHandle){
		p_newMethod.visitFieldInsn(Opcodes.GETSTATIC
				, "java/lang/System"
				, "out"
				, "Ljava/io/PrintStream;");

		loadParam(p_paramDescription,p_newMethod);

		p_newMethod.visitInvokeDynamicInsn("makeConcatWithConstants"
				, "("+p_paramDescription.getParamType()+")Ljava/lang/String;"
				, p_concatFunctionHandle
				, "logged param "+p_paramDescription.getParamName()+": \u0001");
		p_newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
				, "java/io/PrintStream"
				, "println"
				, "(Ljava/lang/String;)V"
				, false);
	}

	private void printLogParams(MethodDescription p_methodDescription,MethodVisitor p_newMethod){
		Handle concatHandle = getConcatFunctionHandle();

		for(MethodParamDescription paramDescription : p_methodDescription.getMethodParams()){
			if(paramDescription.getParamIndex() != 0){
				printLogParam(paramDescription, p_newMethod ,concatHandle);
			}
		}
	}

	private void createLogMethods(){
		for(MethodDescription methodDescription : classDescription.getMethods()){

			MethodVisitor newMethod = getNewMethod(methodDescription);

			//printLogParams(methodDescription,newMethod);

			loadMethodParams(methodDescription,newMethod);

			newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
										,classDescription.getClassName()
										, methodDescription.getMethodProxyName()
										, methodDescription.getMethodDescriptor()
										, false);

			newMethod.visitInsn(Opcodes.RETURN);
			newMethod.visitMaxs(methodDescription.getMethodMaxStack(), methodDescription.getMethodMaxLocals());
			newMethod.visitEnd();
		}
	}

	private Handle getConcatFunctionHandle(){
		String owner = Type.getInternalName(java.lang.invoke.StringConcatFactory.class);

		String descriptor = MethodType.methodType(CallSite.class
				, MethodHandles.Lookup.class
				, String.class
				, MethodType.class
				, String.class
				, Object[].class).toMethodDescriptorString();

		return new Handle(H_INVOKESTATIC,owner,"makeConcatWithConstants",descriptor,false);
	}


////
//
//
//	//@Override
//	public void visitEnd() {
////		mv.visitEnd();
//
//		if(haveLogAnnotation){
////			mv = classVisitor.visitMethod(methodAccess
////					, methodProxyName
////					, methodDescriptor
////					, methodSignature
////					, methodExceptions);
//////			mv.visitInsn(Opcodes.RETURN);
//////			mv.visitMaxs(methodMaxStack, methodMaxLocals);
////			mv.visitEnd();
//
//			MethodVisitor newMethod = classVisitor.visitMethod(methodAccess
//																, methodName+"2"
//																, methodDescriptor
//																, methodSignature
//																, null);
//			Handle concatHandle = getConcatFunctionHandle();
//
//			for (MethodParam methodParam : paramsMethod){
//				if (methodParam.paramIndex != 0){
//					printLogParam(methodParam, newMethod,concatHandle);
//				}
//			}
//
////			for (MethodParam methodParam : paramsMethod){
////				if (methodParam.paramIndex != 0) {
////					loadParam(methodParam, newMethod);
////
////					newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
////							, "java/io/PrintStream"
////							, "println"
////							, "(Ljava/lang/String;)V"
////							, false);
////				}
////			}
//
//			for (MethodParam methodParam : paramsMethod){
//				loadParam(methodParam,newMethod);
//
//			}
//
//			newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
//										,classVisitor.getClassName()
//										, methodName //methodProxyName
//										, methodDescriptor
//										, false);
////
//			newMethod.visitInsn(Opcodes.RETURN);
//			newMethod.visitMaxs(methodMaxStack, methodMaxLocals);
//			newMethod.visitEnd();
//
//
//
//			System.out.println("WOW");
//
//
//		}
//
//		//mv.visitEnd();
//
//	}
}
