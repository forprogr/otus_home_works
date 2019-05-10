package ru.forprogr.hw.hw04autolog.descriptions;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 19:47
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.Collection;
import java.util.Hashtable;


public class ClassDescription {
	private String className;

	private Hashtable<String,MethodDescription> classMethods;

	public ClassDescription(String p_className){
		classMethods = new Hashtable<>();
		className = p_className;
	}

	public String getClassName() {
		return className;
	}

	public void addClassMethod(MethodDescription p_classMethod){
		classMethods.put(p_classMethod.getMethodKey(),p_classMethod);
	}

	public Collection<MethodDescription> getMethods(){
		return classMethods.values();
	}

	public MethodDescription getMethod(String p_methodKey){
		return classMethods.get(p_methodKey);
	}
}
