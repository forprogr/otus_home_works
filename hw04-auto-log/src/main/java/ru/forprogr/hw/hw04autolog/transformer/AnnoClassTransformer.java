package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 9:40
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.*;

import java.security.ProtectionDomain;
import java.util.HashMap;
import java.lang.instrument.ClassFileTransformer;

public class AnnoClassTransformer implements ClassFileTransformer{
	private HashMap<String,byte[]> classesSource;

	public AnnoClassTransformer(){
		classesSource = new HashMap<>();
	}

	private byte[] getClassSource(ClassForTransform p_transformedClass){
		return classesSource.get(p_transformedClass.getClassName());
	}

	private void setClassSource(ClassForTransform p_transformedClass){
		classesSource.put(p_transformedClass.getClassName(),p_transformedClass.getClassSource());
	}

	@Override
	public byte[] transform(ClassLoader p_classLoader
							,String p_className
							,Class<?> p_classBeingRedefined
							,ProtectionDomain p_protectionDomain
							,byte[] p_classfileBuffer){
		ClassForTransform transformedClass = new ClassForTransform(p_classLoader
																	,p_className
																	,p_protectionDomain
																	,p_classfileBuffer);
		return getTransformedClass(transformedClass);
	}

	public byte[] getTransformedClass(ClassForTransform p_transformedClass){
		//System.out.println("<i> Load Class:" + p_transformedClass.getClassName());


		if (p_transformedClass.isNeedTransform()) {
			byte[] retClassSource = getClassSource(p_transformedClass);
			if (retClassSource == null) {
				transformClass(p_transformedClass);

				setClassSource(p_transformedClass);
			}
			return retClassSource;
		}
		return p_transformedClass.getClassSource();
	}

	private void transformClass(ClassForTransform p_transformedClass){
		ClassReader cr = new ClassReader(p_transformedClass.getClassSource());
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new AnnoClassVisitor(Opcodes.ASM5, cw,p_transformedClass);

		cr.accept(cv, Opcodes.ASM5);

		cv = new AnnoClassVisitor(Opcodes.ASM5, cw,p_transformedClass);

		cr.accept(cv, Opcodes.ASM5);

		for(MethodForTransform methodParams : p_transformedClass.getMethodsParams()){

//			MethodVisitor mv = cw.visitMethod(methodParams.getMethodAccess()
//								, methodParams.getMethodName()
//								, methodParams.getMethodDescriptor()
//								, methodParams.getMethodSignature()
//								, null);
//
//			Handle handle = new Handle(
//					H_INVOKESTATIC,
//					Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
//					"makeConcatWithConstants",
//					MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
//					false);
//
//			mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//			mv.visitVarInsn(Opcodes.ALOAD, 1);
//			mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(Ljava/lang/String;)Ljava/lang/String;", handle, "logged param:\u0001");
//
//			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//
//			mv.visitVarInsn(Opcodes.ALOAD, 0);
//			mv.visitVarInsn(Opcodes.ALOAD, 1);
//			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "ru/otus/aop/instrumentation/MyClassImpl", "secureAccessProxied", "(Ljava/lang/String;)V", false);
//
//			mv.visitInsn(Opcodes.RETURN);
//			mv.visitMaxs(0, 0);
//			mv.visitEnd();
		}



	}


}
