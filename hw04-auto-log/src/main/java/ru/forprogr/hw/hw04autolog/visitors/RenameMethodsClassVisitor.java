package ru.forprogr.hw.hw04autolog.visitors;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 21:13
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;

import java.util.Map;

public class RenameMethodsClassVisitor extends ClassVisitor {

	final private Map<String, MethodDescription> methodDescriptionHash;

	public RenameMethodsClassVisitor(final int p_api
									, final ClassWriter p_classWriter
									, final Map<String, MethodDescription> p_methodDescriptionHash){
		super(p_api,p_classWriter);

		methodDescriptionHash = p_methodDescriptionHash;
	}

	private String getMethodKey(String p_methodName,int p_methodAccess,String p_methodDescriptor){
		return p_methodName + "#" + p_methodAccess + "#" + p_methodDescriptor;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodDescription methodDescription = methodDescriptionHash.get(getMethodKey(name,access,descriptor));

		if (methodDescription != null){
			return super.visitMethod(access, methodDescription.getMethodProxyName(), descriptor, signature, exceptions);
		} else {
			return super.visitMethod(access, name, descriptor, signature, exceptions);
		}
	}
}
