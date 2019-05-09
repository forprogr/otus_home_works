package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 11:02
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClassForTransform {

	private ClassLoader classLoader;
	private String className;

	private ProtectionDomain protectionDomain;
	private byte[] classSource;

	private HashSet<String> methodsWithAnnotation;
	private List<MethodForTransform> methodsParams;

	public List<MethodForTransform> getMethodsParams(){
		return methodsParams;
	}

	public void addMethodParams(MethodForTransform p_methodParams){
		methodsParams.add(p_methodParams);
	}

	public void addMethodWithAnnotation(String p_methodName){
		methodsWithAnnotation.add(p_methodName);
	}

	public boolean isMethodWithAnnotation(String p_methodName){
		return methodsWithAnnotation.contains(p_methodName);
	}

	public String getClassName(){
		return className;
	}

	public ClassForTransform(ClassLoader p_classLoader
			,String p_className
			,ProtectionDomain p_protectionDomain
			,byte[] p_classFileBuffer){

		classLoader = p_classLoader;
		className = p_className;
		protectionDomain = p_protectionDomain;
		classSource = p_classFileBuffer;

		methodsWithAnnotation = new HashSet<>();
		methodsParams = new ArrayList<>();
	}

//	private boolean findAnnoMethods(){
//		boolean isFind = false;
//
//		try {
//
//			ClassReader cr = new ClassReader(originalClass);
//			ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
//			ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw)
//			cr.accept(cv, Opcodes.ASM5);
//
//			Class<?> transformedClass = classLoader.loadClass(className);
//
//			for (Method method : transformedClass.getDeclaredMethods()) {
//				if (method.isAnnotationPresent(Log.class)) {
//					methodsWithAnnotation.add(method);
//					isFind = true;
//				}
//			}
//		} catch (ClassNotFoundException e) {
//			isFind = false;
//		}
//
//		return isFind;
//	}

	public boolean isNeedTransform(){
		boolean retIsNeed =
					!className.startsWith("java")
					&& !className.startsWith("sun")
					&& !className.startsWith("jdk")
					&& !className.startsWith("org/jetbrains")
					&& !className.startsWith("com/intellij");

//		if (retIsNeed) {
//			retIsNeed = findAnnoMethods();
//		}

		return retIsNeed;
	}

	public byte[] getClassSource(){
		return classSource;
	}

	public void setClassSource(byte[] p_transformedClassSource){
		classSource = p_transformedClassSource;
	}


}
