package ru.forprogr.hw.hw03testingframework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 17:04
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestExecutor extends MethodsExecutor{
	private String testMethodName;

	private void setTestMethodName(Method p_method){
		testMethodName = p_method.getName();
	}

	public TestExecutor(Class<?> p_testedCLass){
		super(p_testedCLass);
	}

	public void executeTest(Method p_testMethod
							, ArrayList<Method> p_methodsBeforeTest
							, ArrayList<Method> p_methodsAfterTest){
		executeMethods(p_methodsBeforeTest);

		setTestMethodName(p_testMethod);
		executeMethod(p_testMethod);

		executeMethods(p_methodsAfterTest);
	}

	@Override
	public void printRunResult(){
		if(isRunOk()){
			System.out.println("[OK] === Running test method: "+testMethodName);
		} else {
			System.out.println("[ERROR] *** Running test method: "+testMethodName);
			System.out.println(getErrors());
		}
	}

}
