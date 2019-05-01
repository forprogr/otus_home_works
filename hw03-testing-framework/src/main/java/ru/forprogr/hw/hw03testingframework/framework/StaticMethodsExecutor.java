package ru.forprogr.hw.hw03testingframework.framework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   01.05.2019 13:16
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class StaticMethodsExecutor {

	private boolean stopWhenError;

	private boolean resultRunTest;
	private StringBuffer errorMessages;

	public StaticMethodsExecutor(boolean p_stopWhenError){
		errorMessages = new StringBuffer();
		resultRunTest = true;
		stopWhenError = p_stopWhenError;
	}


	public void executeMethod(Method p_method){
		try {
			p_method.invoke(null);
		} catch (IllegalAccessException e){
			addError("Error when execute method: "+p_method.getName());
			addError(e.toString());
		} catch (InvocationTargetException e) {
			addError("Error when execute method: "+p_method.getName());
			addError(e.getCause().toString());
		}
	}

	public void executeMethods(List<Method> p_methods){
		for(Method method : p_methods){
			executeMethod(method);

			if (!resultRunTest && stopWhenError){
				return;
			}
		}
	}

	public void addError(String p_errorMessage){
		if (errorMessages.length() != 0){
			errorMessages.append("\n");
		}
		errorMessages.append("\t\t");
		errorMessages.append(p_errorMessage);
		resultRunTest = false;
	}

	public String getErrors(){
		return errorMessages.toString();
	}

	public boolean isRunOk(){
		return resultRunTest;
	}

	public boolean isBreakRun(){
		return !isRunOk() && stopWhenError;
	}

	public void printRunResult(){
		if(!isRunOk()){
			System.out.println(getErrors());
		}
	}
}
