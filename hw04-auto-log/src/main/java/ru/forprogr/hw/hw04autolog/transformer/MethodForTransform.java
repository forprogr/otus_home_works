package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 22:26
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

public class MethodForTransform {
	private int methodAccess;
	private String methodName;
	private String methodProxyName;
	private String methodDescriptor;
	private String methodSignature;
	//private String[] methodExceptions;

	public String getMethodKey() {
		return methodName+":"+methodDescriptor;
	}

	public int getMethodAccess() {
		return methodAccess;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMethodProxyName() {
		return methodProxyName;
	}

	public String getMethodDescriptor() {
		return methodDescriptor;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public MethodForTransform(String p_methodName
								,String p_methodProxyName
								,int p_methodAccess
								,String p_methodDescriptor
								,String p_methodSignature
								){
		methodAccess = p_methodAccess;
		methodName = p_methodName;
		methodProxyName = p_methodProxyName;
		methodDescriptor = p_methodDescriptor;
		methodSignature = p_methodSignature;
		;
	}
}
