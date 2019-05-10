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
	private Label endLabel;
	private String signature;

	public MethodParamDescription(String p_paramName,String p_paramType,String p_signature, int p_paramIndex,Label p_startLabel,Label p_endLabel){
		paramName = p_paramName;
		paramType = p_paramType;
		paramIndex = p_paramIndex;
		startLabel = p_startLabel;
		endLabel = p_endLabel;
		signature = p_signature;
	}

	public String getSignature() {
		return signature;
	}

	public String getParamType() {
		return paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public int getParamIndex() {
		return paramIndex;
	}

	public Label getStartLabel() {
		return startLabel;
	}

	public Label getEndLabel() {
		return endLabel;
	}
}
