package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 19:58
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.*;
import ru.forprogr.hw.hw04autolog.anno.Log;

public class AnnoMethodVisitor extends MethodVisitor {
	private ClassVisitor classVisitor;

	private MethodForTransform methodParams;
	private ClassForTransform transformedClass;

	public AnnoMethodVisitor(final int p_api
							, final MethodVisitor p_methodVisitor
							, MethodForTransform p_methodParams
							, ClassForTransform p_transformedClass){
		super(p_api,p_methodVisitor);
		methodParams = p_methodParams;
		transformedClass = p_transformedClass;
	}


	@Override
	public void visitParameter(final String name, final int access) {
		System.out.println("\t[visitParameter]" + name);

		super.visitParameter(name,access);
	}

	@Override
	public void visitAnnotableParameterCount(final int parameterCount, final boolean visible) {
		System.out.println("\t[visitAnnotableParameterCount]" + parameterCount);
		super.visitAnnotableParameterCount(parameterCount,visible);
	}

	@Override
	public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
		System.out.println("\t[visitAnnotation]" + descriptor);

		if(descriptor.endsWith(Log.class.getName().replaceAll("[.]","/").concat(";"))){
			transformedClass.addMethodWithAnnotation(methodParams.getMethodKey());
		}

		return super.visitAnnotation(descriptor,visible);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(
			final int typeRef, final TypePath typePath, final String descriptor, final boolean visible) {
		System.out.println("\t[visitTypeAnnotation]" + descriptor);
		return super.visitTypeAnnotation(typeRef,typePath,descriptor,visible);
	}

	@Override
	public void visitFrame(
			final int type,
			final int numLocal,
			final Object[] local,
			final int numStack,
			final Object[] stack) {
		super.visitFrame(type,numLocal,local,numStack,stack);
	}

	@Override
	public void visitVarInsn(final int opcode, final int var) {
		System.out.println("\t[visitVarInsn] " + var);

		if (Opcodes.ALOAD == opcode){
			System.out.println("\t\t[Opcodes.ALOAD] ");
		}

		super.visitVarInsn(opcode, var);

	}
}
