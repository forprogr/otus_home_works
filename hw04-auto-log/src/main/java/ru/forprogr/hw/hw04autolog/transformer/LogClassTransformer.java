package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 12:05
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import ru.forprogr.hw.hw04autolog.descriptions.MethodDescription;
import ru.forprogr.hw.hw04autolog.visitors.FindLogClassVisitor;
import ru.forprogr.hw.hw04autolog.visitors.RenameMethodsClassVisitor;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;

public class LogClassTransformer implements ClassFileTransformer {

	final private List<String> skippedPackages;
	final private int asm_api;

	public LogClassTransformer(List<String> p_skippedPackages,int p_asm_api){
		skippedPackages = p_skippedPackages;
		asm_api = p_asm_api;
	}

	public boolean isSkippedClassName(String p_className){

		for( String skippedPackage : skippedPackages){
			if (p_className.startsWith(skippedPackage)){
				return true;
			}
		}

		return false;
	}

	public final byte[] transform(ClassLoader p_classLoader
			, String p_className
			, Class<?> p_classBeingRedefined
			, ProtectionDomain p_protectionDomain
			, byte[] p_classByteCodes){

		if (!isSkippedClassName(p_className)) {

			Map<String, MethodDescription> methodDescriptionHash = findLogAnnonation(p_classByteCodes);

			if (!methodDescriptionHash.isEmpty()) {

				byte[] classByteCodes = renameAndCreateMethodsWithLog(p_className,methodDescriptionHash, p_classByteCodes);

				writeToFileModifedClass(p_className, classByteCodes);

				return classByteCodes;
			}
		}

		return p_classByteCodes;
	}


	private Map<String, MethodDescription> findLogAnnonation(byte[] p_classByteCodes){
		ClassReader classReader = new ClassReader(p_classByteCodes);
		ClassWriter classWriter = new ClassWriter(classReader, 0);

		FindLogClassVisitor findLogVisitor = new FindLogClassVisitor(asm_api, classWriter);

		classReader.accept(findLogVisitor, asm_api);

		return findLogVisitor.getLoggedClassMethods();
	}

	private byte[] renameAndCreateMethodsWithLog(String p_className
												,Map<String, MethodDescription> methodDescriptionHash
												,byte[] p_classByteCodes){
		ClassReader classReader = new ClassReader(p_classByteCodes);
		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

		RenameMethodsClassVisitor renameMethodsVisitor = new RenameMethodsClassVisitor(asm_api
																						, classWriter
																						, methodDescriptionHash);

		classReader.accept(renameMethodsVisitor, asm_api);

		CreatorLogMethods creatorLogMethods = new CreatorLogMethods(classWriter,p_className);
		creatorLogMethods.createMethods(methodDescriptionHash.values());

		return classWriter.toByteArray();
	}

	private void writeToFileModifedClass(String p_className, byte[] p_classByteCodes){
		String classFileName = p_className.replaceAll("[/]","_").concat("@MOD.class");
		try (OutputStream fos = new FileOutputStream(classFileName)) {
			fos.write(p_classByteCodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
