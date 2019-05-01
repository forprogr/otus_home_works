package ru.forprogr.hw.hw03testingframework.framework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 19:39
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import ru.forprogr.hw.hw03testingframework.anno.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Modifier;

public class TestsRunner {
	private Class<?> testedClass;
	private String testedClassName;

	private List<Method> methodsBeforeAll;
	private List<Method> methodsAfterAll;
	private List<Method> methodsBeforeTest;
	private List<Method> methodsAfterTest;

	private List<Method> methodsTest;

	private int countTestRun;
	private int countTestOk;
	private int countTestError;

	private boolean stopWhenError;

	private boolean setTestedClass(String p_testClassName){
		boolean retIsFind = true;
		try {
			testedClass = Class.forName(p_testClassName);
		} catch (ClassNotFoundException e) {
			retIsFind = false;
		}

		return retIsFind;
	}

	public TestsRunner(String p_testClassName,boolean p_stopWhenError){
		methodsBeforeAll = new ArrayList<>();
		methodsAfterAll = new ArrayList<>();
		methodsBeforeTest = new ArrayList<>();
		methodsAfterTest = new ArrayList<>();
		methodsTest = new ArrayList<>();

		testedClassName = p_testClassName;

		countTestRun = 0;
		countTestOk = 0;
		countTestError = 0;

		stopWhenError = p_stopWhenError;
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

	private boolean chkStaticMethod(Method p_method,String p_anno){
		if (!Modifier.isStatic(p_method.getModifiers())) {
			System.out.println("[WARNING - SKIP] Method: "+p_method.getName()+" - skipped run");
			System.out.println("\t[INFO] Method marked as "+p_anno+" should be static");
			return false;
		}
		return true;
	}

	private void readTestMethods() {
		for(Method method : testedClass.getDeclaredMethods()){
			if (method.isAnnotationPresent(Test.class)){
				methodsTest.add(method);
			} else if (method.isAnnotationPresent(BeforeAll.class)){
				if (chkStaticMethod(method,"@BeforeAll")) {
					methodsBeforeAll.add(method);
				}
			} else if (method.isAnnotationPresent(BeforeTest.class)){
				methodsBeforeTest.add(method);
			} else if (method.isAnnotationPresent(AfterAll.class)){
				if (chkStaticMethod(method,"@AfterAll")) {
					methodsAfterAll.add(method);
				}
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

	private boolean runBeforeAfterAllMethods(List<Method> p_methodsAll){
		StaticMethodsExecutor executor  = new StaticMethodsExecutor(stopWhenError);
		executor.executeMethods(p_methodsAll);
		executor.printRunResult();
		return !executor.isBreakRun();
	}

	private void runTestMethods(){
		boolean isRunOk = runBeforeAfterAllMethods(methodsBeforeAll);

		if(isRunOk){
			for(Method method : methodsTest){
				TestExecutor test = new TestExecutor(testedClass,stopWhenError);
				test.executeTest(method,methodsBeforeTest,methodsAfterTest);
				test.printRunResult();

				if(test.isRunOk()){
					countTestOk++;
				} else {
					countTestError++;
				}
				countTestRun++;
			}
		}

		runBeforeAfterAllMethods(methodsAfterAll);
	}
}
