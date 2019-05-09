package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 20:28
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class LogMethodsCreator {

//	private String getClearClassName(String p_descriptor){
//		return p_descriptor.replaceAll("(^[L])|([;]$)","").replaceAll("/",".");
//	}
//
//	private Handle getConcatFunctionHandle(){
//		String owner = Type.getInternalName(java.lang.invoke.StringConcatFactory.class);
//
//		String descriptor = MethodType.methodType(CallSite.class
//				, MethodHandles.Lookup.class
//				, String.class
//				, MethodType.class
//				, String.class
//				, Object[].class).toMethodDescriptorString();
//
//		return new Handle(H_INVOKESTATIC,owner,"makeConcatWithConstants",descriptor,false);
//	}
//
//
//
//	private int getOpcodeWithType(String p_paramType,int p_iOpcode){
//		return Type.getType(p_paramType).getOpcode(p_iOpcode);
//	}
//
//	private void loadParam(MethodParam p_methodParam, MethodVisitor p_newMethod){
//		int loadOpcode = getOpcodeWithType(p_methodParam.paramType, Opcodes.ILOAD);
//
//		p_newMethod.visitVarInsn(loadOpcode,p_methodParam.paramIndex);
//	}
//
//	private void printLogParam(MethodParam p_methodParam, MethodVisitor p_newMethod, Handle p_concatFunctionHandle){
//		p_newMethod.visitFieldInsn(Opcodes.GETSTATIC
//				, "java/lang/System"
//				, "out"
//				, "Ljava/io/PrintStream;");
//
//		loadParam(p_methodParam,p_newMethod);
//
//		p_newMethod.visitInvokeDynamicInsn("makeConcatWithConstants"
//				, "("+p_methodParam.paramType+")Ljava/lang/String;"
//				, p_concatFunctionHandle
//				, "logged param "+p_methodParam.paramName+": \u0001");
//		p_newMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL
//				, "java/io/PrintStream"
//				, "println"
//				, "(Ljava/lang/String;)V"
//				, false);
//	}

	//@Override
	public void visitEnd() {
//		mv.visitEnd();

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

	}
}
