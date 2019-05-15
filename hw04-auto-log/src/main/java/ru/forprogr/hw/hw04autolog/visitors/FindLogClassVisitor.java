package ru.forprogr.hw.hw04autolog.visitors;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 12:38
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;

import java.util.Map;
import java.util.HashMap;

public class FindLogClassVisitor extends ClassVisitor {

	final private Map<String, FindLogMethodVisitor> classAllMethods;

	public FindLogClassVisitor(final int p_api, final ClassWriter p_classWriter){
		super(p_api,p_classWriter);

		classAllMethods = new HashMap<>();
	}

	public Map<String, MethodDescription> getLoggedClassMethods() {
		Map retLoggedClassMethods = new HashMap<>();

		for (String key : classAllMethods.keySet()){
			FindLogMethodVisitor methodVisitor = classAllMethods.get(key);
			if (methodVisitor.isHaveLogAnnotation()){
				retLoggedClassMethods.put(key,methodVisitor.getMethodDescription());
			}
		}

		return retLoggedClassMethods;
	}

	private String getMethodKey(String p_methodName,int p_methodAccess,String p_methodDescriptor){
		return p_methodName + "#" + p_methodAccess + "#" + p_methodDescriptor;
	}

	@Override
	public MethodVisitor visitMethod(int p_methodAccess
									, String p_methodName
									, String p_methodDescriptor
									, String p_methodSignature
									, String[] p_methodExceptions) {
		MethodVisitor methodVisitor = cv.visitMethod(p_methodAccess
													, p_methodName
													, p_methodDescriptor
													, p_methodSignature
													, p_methodExceptions);

		if (methodVisitor != null) {
			String methodKey = getMethodKey(p_methodName,p_methodAccess,p_methodDescriptor);



			methodVisitor = new FindLogMethodVisitor(api
														, methodVisitor
														, p_methodName
														, p_methodAccess
														, p_methodDescriptor
														, p_methodSignature
														, p_methodExceptions);

			classAllMethods.put(methodKey,(FindLogMethodVisitor) methodVisitor);
		}
		return methodVisitor;

	}

}
