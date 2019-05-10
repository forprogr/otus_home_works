package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 12:38
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import ru.forprogr.hw.hw04autolog.descriptions.ClassDescription;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;

public class FindLogAnnotations extends ClassVisitor {

	private ClassDescription classDescription;

	public FindLogAnnotations(final int p_api, final ClassVisitor p_classVisitor, ClassDescription p_classDescription){
		super(p_api,p_classVisitor);

		classDescription = p_classDescription;

	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);

		if (methodVisitor != null) {
			MethodDescription methodDescription = new MethodDescription(name, access, descriptor, signature, exceptions);

			methodVisitor = new FindMethodLogAnnotations(api
					, methodVisitor
					, classDescription
					, methodDescription);
		}
		return methodVisitor;

	}

}
