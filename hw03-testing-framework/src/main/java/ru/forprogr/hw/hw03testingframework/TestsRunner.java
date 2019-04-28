package ru.forprogr.hw.hw03testingframework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 19:39
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestsRunner {
	private Class<?> testedClass;
	private String testedClassName;

	private ArrayList<Method> methodsBeforeAll;
	private ArrayList<Method> methodsAfterAll;
	private ArrayList<Method> methodsBeforeTest;
	private ArrayList<Method> methodsAfterTest;

	private ArrayList<Method> methodsTest;

	private int countTestRun;
	private int countTestOk;
	private int countTestError;

	private boolean setTestedClass(String p_testClassName){
		boolean retIsFind = true;
		try {
			testedClass = Class.forName(p_testClassName);
		} catch (ClassNotFoundException e) {
			retIsFind = false;
		}

		return retIsFind;
	}

	public TestsRunner(String p_testClassName){
		methodsBeforeAll = new ArrayList<>();
		methodsAfterAll = new ArrayList<>();
		methodsBeforeTest = new ArrayList<>();
		methodsAfterTest = new ArrayList<>();
		methodsTest = new ArrayList<>();

		testedClassName = p_testClassName;

		countTestRun = 0;
		countTestOk = 0;
		countTestError = 0;
	}

	public void runTests(){
		printInfoHeader();

		boolean isFindClass = setTestedClass(testedClassName);
		if (isFindClass) {
			readTestMethods();
			runTestMethods();
			printInfoFooter();
		} else {
			System.out.println("[ERROR] Tests NOT run. Not found class - "+testedClassName);
		}
	}

	private void readTestMethods() {
		for(Method method : testedClass.getDeclaredMethods()){
			if (method.isAnnotationPresent(Test.class)){
				methodsTest.add(method);
			} else if (method.isAnnotationPresent(BeforeAll.class)){
				methodsBeforeAll.add(method);
			} else if (method.isAnnotationPresent(BeforeTest.class)){
				methodsBeforeTest.add(method);
			} else if (method.isAnnotationPresent(AfterAll.class)){
				methodsAfterAll.add(method);
			} else if (method.isAnnotationPresent(AfterTest.class)){
				methodsAfterTest.add(method);
			}
		}
	}

	private void printInfoHeader(){
		System.out.println("[INFO] -------------------------------------------------------");
		System.out.println("[INFO]  T E S T S");
		System.out.println("[INFO] -------------------------------------------------------");
		System.out.println("[INFO] Running "+testedClassName);

	}

	private void printInfoFooter(){
		System.out.println("[INFO] Tests run: "+countTestRun
							+", Errors: "+countTestError
							+", Ok: "+countTestOk
							+" - in "+testedClassName);
	}

	private void runBeforeAfterAllMethods(ArrayList<Method> p_methodsAll){
		MethodsExecutor executor  = new MethodsExecutor(testedClass);
		executor.executeMethods(p_methodsAll);
		executor.printRunResult();
	}

	private void runTestMethods(){
		runBeforeAfterAllMethods(methodsBeforeAll);

		for(Method method : methodsTest){
			TestExecutor test = new TestExecutor(testedClass);
			test.executeTest(method,methodsBeforeTest,methodsAfterTest);
			test.printRunResult();

			if(test.isRunOk()){
				countTestOk++;
			} else {
				countTestError++;
			}
			countTestRun++;
		}

		runBeforeAfterAllMethods(methodsAfterAll);
	}
}
