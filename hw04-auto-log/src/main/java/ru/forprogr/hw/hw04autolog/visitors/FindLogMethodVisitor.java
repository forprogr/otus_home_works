package ru.forprogr.hw.hw04autolog.visitors;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 14:05
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.*;

import ru.forprogr.hw.hw04autolog.anno.Log;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;

public class FindLogMethodVisitor extends MethodVisitor {

	final private MethodDescription methodDescription;

	private boolean haveLogAnnotation;

	public MethodDescription getMethodDescription() {
		return methodDescription;
	}

	public boolean isHaveLogAnnotation() {
		return haveLogAnnotation;
	}

	public FindLogMethodVisitor(final int p_api
									, final MethodVisitor p_methodVisitor
									, String p_methodName
									, int p_methodAccess
									, String p_methodDescriptor
									, String p_methodSignature
									, String[] p_methodExceptions){
		super(p_api,p_methodVisitor);

		haveLogAnnotation = false;

		methodDescription = new MethodDescription(p_methodName
													, p_methodAccess
													, p_methodDescriptor
													, p_methodSignature
													, p_methodExceptions);

	}

	public String descriptorToClassName(String p_descriptor){
		return p_descriptor.replaceAll("(^[L])|([;]$)","").replaceAll("/",".");
	}

	@Override
	public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
		String annotationClassName = descriptorToClassName(descriptor);

		if (!haveLogAnnotation) {
			haveLogAnnotation = Log.class.getName().equals(annotationClassName);
		}

		return mv.visitAnnotation(descriptor,visible);
	}

	@Override
	public void visitMaxs(final int maxStack, final int maxLocals) {
		methodDescription.setMethodMaxStack(maxStack);
		methodDescription.setMethodMaxLocals(maxLocals);

		mv.visitMaxs(maxStack, maxLocals);
	}

	@Override
	public void visitLocalVariable(
			final String name,
			final String descriptor,
			final String signature,
			final Label start,
			final Label end,
			final int index) {

		methodDescription.addMethodParamOrVar(name,descriptor,signature,index,start,end);

		mv.visitLocalVariable(name, descriptor, signature, start, end, index);
	}

}
