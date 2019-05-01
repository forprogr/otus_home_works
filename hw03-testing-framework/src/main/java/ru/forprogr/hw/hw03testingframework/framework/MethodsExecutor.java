package ru.forprogr.hw.hw03testingframework.framework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 20:16
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodsExecutor extends StaticMethodsExecutor {
	private Object testedObject;

	public MethodsExecutor(Class<?> p_testedCLass,boolean p_stopWhenError){
		super(p_stopWhenError);
		createTestedObject(p_testedCLass);
	}

	private void createTestedObject(Class<?> p_testedCLass){
		try {
			Constructor<?> constructor = p_testedCLass.getConstructor();
			testedObject = constructor.newInstance();
		} catch (InvocationTargetException e) {
			addError("Error when create object: "+p_testedCLass.getCanonicalName());
			addError(e.getCause().toString());
		} catch (NoSuchMethodException
				| InstantiationException
				| IllegalAccessException e) {
			addError("Error when create object: "+p_testedCLass.getCanonicalName());
			addError(e.toString());
		}

	}
	@Override
	public void executeMethod(Method p_method){
		try {
			p_method.invoke(testedObject);
		} catch (IllegalAccessException e){
			addError("Error when execute method: "+p_method.getName());
			addError(e.toString());
		} catch (InvocationTargetException e) {
			addError("Error when execute method: "+p_method.getName());
			addError(e.getCause().toString());
		}
	}

}
