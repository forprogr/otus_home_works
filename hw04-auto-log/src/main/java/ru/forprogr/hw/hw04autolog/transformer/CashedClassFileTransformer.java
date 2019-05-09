package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 11:47
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashedClassFileTransformer implements ClassFileTransformer {

	private HashMap<String,byte[]> cashedClassByteCodes;
	private List<String> skippedClassNames;

	public CashedClassFileTransformer(){
		cashedClassByteCodes = new HashMap<>();
		skippedClassNames = new ArrayList<>();
	}

	public void addSkippedClassNames(String p_className){
		skippedClassNames.add(p_className);
	}

	public void clearSkippedClassNames(){
		skippedClassNames.clear();
	}

	public boolean isSkippedClassName(String p_className){

		for( String skippedName : skippedClassNames){
			if (p_className.startsWith(skippedName)){
				return true;
			}
		}

		return false;
	}

	private byte[] getClassByteCodes(String p_className){
		return cashedClassByteCodes.get(p_className);
	}

	private void setClassByteCodes(String p_className,byte[] p_classByteCodes){
		if (p_classByteCodes != null && p_classByteCodes.length>0) {
			cashedClassByteCodes.put(p_className, p_classByteCodes);
		}
	}

	protected void clearCash(){
		if (cashedClassByteCodes != null){
			cashedClassByteCodes.clear();
		}
	}

	@Override
	public final byte[] transform(ClassLoader p_classLoader
			, String p_className
			, Class<?> p_classBeingRedefined
			, ProtectionDomain p_protectionDomain
			, byte[] p_classByteCodes){

		byte[] classByteCodes = getClassByteCodes(p_className);

		if (classByteCodes == null && !isSkippedClassName(p_className)){
			classByteCodes = getTransformedClass(p_classLoader
												,p_className
												,p_classBeingRedefined
												,p_protectionDomain
												,p_classByteCodes);

			setClassByteCodes(p_className,classByteCodes);
		}

		if (classByteCodes == null){
			classByteCodes = p_classByteCodes;
		}

		return classByteCodes;
	}

	protected byte[] getTransformedClass(ClassLoader p_classLoader
										, String p_className
										, Class<?> p_classBeingRedefined
										, ProtectionDomain p_protectionDomain
										, byte[] p_classByteCodes){
		return p_classByteCodes;
	}
}
