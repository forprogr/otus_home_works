package ru.forprogr.hw.hw04autolog.descriptions;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   09.05.2019 19:48
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.Label;

public class MethodParamDescription {

	private String paramType;
	private String paramName;
	private int paramIndex;
	private Label startLabel;

	public MethodParamDescription(String p_paramName,String p_paramType,int p_paramIndex,Label p_startLabel){
		paramName = p_paramName;
		paramType = p_paramType;
		paramIndex = p_paramIndex;
		startLabel = p_startLabel;
	}


}
