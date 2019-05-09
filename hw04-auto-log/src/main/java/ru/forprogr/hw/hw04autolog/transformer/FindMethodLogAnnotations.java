package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 14:05
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.*;

import ru.forprogr.hw.hw04autolog.anno.Log;
import ru.forprogr.hw.hw04autolog.descriptions.ClassDescription;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;

public class FindMethodLogAnnotations extends MethodVisitor {

	private ClassDescription classDescription;
	private MethodDescription methodDescription;

	private boolean haveLogAnnotation;

	public FindMethodLogAnnotations(final int api
									,final MethodVisitor p_methodVisitor
									,ClassDescription p_classDescription
									,MethodDescription p_methodDescription){
		super(api,p_methodVisitor);
		classDescription = p_classDescription;
		methodDescription = p_methodDescription;
		haveLogAnnotation = false;
	}

	@Override
	public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
		String annotationClassName = classDescription.descriptorToClassName(descriptor);

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

		methodDescription.addMethodParam(name,descriptor,index,start);

		mv.visitLocalVariable(name, descriptor, signature, start, end, index);
	}

	@Override
	public void visitEnd() {
		if(haveLogAnnotation){
			classDescription.addClassMethod(methodDescription);
		}

		mv.visitEnd();
	}
}
