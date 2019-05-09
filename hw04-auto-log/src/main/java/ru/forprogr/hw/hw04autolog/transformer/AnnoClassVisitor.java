package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 19:46
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.signature.*;

public class AnnoClassVisitor extends ClassVisitor {
	private ClassForTransform transformedClass;

	public AnnoClassVisitor(final int p_api, final ClassVisitor p_classVisitor,ClassForTransform p_transformedClass){
		super(p_api,p_classVisitor);
		transformedClass = p_transformedClass;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		System.out.println("[visit method] "+name);
		System.out.println("\t<i> descriptor: "+descriptor);
		System.out.println("\t<i> signature: "+signature);

		String proxyName = name+"Proxied";
		MethodForTransform methodParams = new MethodForTransform(name, proxyName,access,descriptor,signature);

		if (transformedClass.isMethodWithAnnotation(methodParams.getMethodKey())) {

			transformedClass.addMethodParams(methodParams);

			return super.visitMethod(access, proxyName, descriptor, signature, exceptions);

		}

		MethodVisitor methodVisitor = cv.visitMethod(access,name,descriptor,signature,exceptions);

		AnnoMethodVisitor annoMethodVisitor = new AnnoMethodVisitor(api,methodVisitor,methodParams,transformedClass);


		return annoMethodVisitor;
	}

}
