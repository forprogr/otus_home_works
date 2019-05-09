package ru.forprogr.hw.hw04autolog.descriptions;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 19:47
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.Label;

import java.util.ArrayList;
import java.util.List;

public class MethodDescription {

	private List<MethodParamDescription> methodParams;

	private Label methodParamStartLabel;
	private int methodAccess;
	private String methodName;
	private String methodProxyName;
	private String methodDescriptor;
	private String methodSignature;
	private String[] methodExceptions;

	private int methodMaxStack;
	private int methodMaxLocals;

	public MethodDescription(
			String p_methodName
			, int p_methodAccess
			, String p_methodDescriptor
			, String p_methodSignature
			, String[] p_methodExceptions){

		methodName = p_methodName;
		methodProxyName = methodName+"Proxy";

		methodAccess = p_methodAccess;
		methodDescriptor = p_methodDescriptor;
		methodSignature = p_methodSignature;
		methodExceptions = p_methodExceptions;

		methodMaxStack = 0;
		methodMaxLocals = 0;

		methodParams = new ArrayList<>();

	}

	public List<MethodParamDescription> getMethodParams() {
		return methodParams;
	}

	public int getMethodAccess() {
		return methodAccess;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMethodDescriptor() {
		return methodDescriptor;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public String[] getMethodExceptions() {
		return methodExceptions;
	}

	public int getMethodMaxStack() {
		return methodMaxStack;
	}

	public int getMethodMaxLocals() {
		return methodMaxLocals;
	}

	public String getMethodProxyName(){
		return methodProxyName;
	}

	public static String getMethodKey(String p_methodName,int p_methodAccess,String p_methodDescriptor){
		return p_methodName + "#" + p_methodAccess + "#" + p_methodDescriptor;
	}

	public String getMethodKey(){
		return methodName + "#" + methodAccess + "#" + methodDescriptor;
	}

	public void setMethodMaxStack(int p_methodMaxStack){
		methodMaxStack = p_methodMaxStack;
	}

	public void setMethodMaxLocals(int p_methodMaxLocals){
		methodMaxLocals = p_methodMaxLocals;
	}

	public boolean isMethodParam(Label p_startLabel){
		if (methodParams.isEmpty()){
			methodParamStartLabel = p_startLabel;
			return true;
		}
		return methodParamStartLabel.equals(p_startLabel);
	}

	public void addMethodParam(String p_paramName,String p_paramType,int p_paramIndex,Label p_startLabel){
		if (isMethodParam(p_startLabel)) {
			MethodParamDescription paramDescription = new MethodParamDescription(p_paramName
																				,p_paramType
																				,p_paramIndex
																				,p_startLabel);
			methodParams.add(paramDescription);
		}
	}
}
