package ru.forprogr.hw.hw03testingframework.framework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 17:04
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.Method;
import java.util.List;

public class TestExecutor extends MethodsExecutor{
	private String testMethodName;

	private void setTestMethodName(Method p_method){
		testMethodName = p_method.getName();
	}

	public TestExecutor(Class<?> p_testedCLass,boolean p_stopWhenError){
		super(p_testedCLass,p_stopWhenError);
	}

	public void executeTest(Method p_testMethod
							, List<Method> p_methodsBeforeTest
							, List<Method> p_methodsAfterTest){
		System.out.println("\n[TESTED] --> method: "+p_testMethod);

		executeMethods(p_methodsBeforeTest);

		setTestMethodName(p_testMethod);

		if (!isBreakRun()) {
			executeMethod(p_testMethod);
		}

		executeMethods(p_methodsAfterTest);
	}

	@Override
	public void printRunResult(){
		if(isRunOk()){
			System.out.println("\t[OK] === Running test method: "+testMethodName);
		} else {
			System.out.println("\t[ERROR] *** Running test method: "+testMethodName);
			System.out.println(getErrors());
		}
	}

}
