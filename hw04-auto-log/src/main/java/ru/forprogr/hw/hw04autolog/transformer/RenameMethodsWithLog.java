package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 21:13
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import ru.forprogr.hw.hw04autolog.descriptions.ClassDescription;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;

public class RenameMethodsWithLog extends ClassVisitor {

	private ClassDescription classDescription;

	public RenameMethodsWithLog(final int p_api, final ClassVisitor p_classVisitor, ClassDescription p_classDescription){
		super(p_api,p_classVisitor);

		classDescription = p_classDescription;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodDescription methodDescription = classDescription.getMethod(MethodDescription.getMethodKey(name,access,descriptor));

		if (methodDescription != null){
			return super.visitMethod(access, methodDescription.getMethodProxyName(), descriptor, signature, exceptions);
		} else {
			return super.visitMethod(access, name, descriptor, signature, exceptions);
		}
	}
}
