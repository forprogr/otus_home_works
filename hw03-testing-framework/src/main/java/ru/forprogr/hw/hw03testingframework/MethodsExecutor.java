package ru.forprogr.hw.hw03testingframework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 20:16
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodsExecutor {
	private Object testedObject;

	private boolean resultRunTest;
	private StringBuffer errorMessages;

	public MethodsExecutor(Class<?> p_testedCLass){
		errorMessages = new StringBuffer();
		resultRunTest = true;
		createTestedObject(p_testedCLass);
	}

	private void createTestedObject(Class<?> p_testedCLass){
		try {
			Constructor<?> constructor = p_testedCLass.getConstructor();
			testedObject = constructor.newInstance();
		} catch (NoSuchMethodException
				| InstantiationException
				| IllegalAccessException
				| InvocationTargetException e) {
			addError("Error when create object: "+p_testedCLass.getCanonicalName());
			addError(e.toString());
		}

	}

	public void executeMethod(Method p_method){
		try {
			p_method.invoke(testedObject);
		} catch (IllegalAccessException | InvocationTargetException e) {
			addError("Error when execute method: "+p_method.getName());
			addError(e.toString());
		}
	}

	public void executeMethods(ArrayList<Method> p_methods){
		for(Method method : p_methods){
			executeMethod(method);
		}
	}

	private void addError(String p_errorMessage){
		if (errorMessages.length() != 0){
			errorMessages.append("\n");
		}
		errorMessages.append("\t");
		errorMessages.append(p_errorMessage);
		resultRunTest = false;
	}

	public String getErrors(){
		return errorMessages.toString();
	}

	public boolean isRunOk(){
		return resultRunTest;
	}

	public void printRunResult(){
		if(!isRunOk()){
			System.out.println(getErrors());
		}
	}
}
