package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 12:05
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import ru.forprogr.hw.hw04autolog.descriptions.ClassDescription;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.ProtectionDomain;

public class LogClassTransformer extends CashedClassFileTransformer {

	public LogClassTransformer(){
		super();
	}

	private void findLogAnnonation(ClassDescription p_classDescription, byte[] p_classByteCodes){
		ClassReader classReader = new ClassReader(p_classByteCodes);
		ClassWriter classWriter = new ClassWriter(classReader, 0);

		FindLogAnnotations findLogVisitor = new FindLogAnnotations(Opcodes.ASM5, classWriter, p_classDescription);

		classReader.accept(findLogVisitor, Opcodes.ASM5);
	}

	private byte[] renameAndCreateMethodsWithLog(ClassDescription p_classDescription, byte[] p_classByteCodes){
		ClassReader classReader = new ClassReader(p_classByteCodes);
		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

		RenameMethodsWithLog renameMethodsVisitor = new RenameMethodsWithLog(Opcodes.ASM5, classWriter, p_classDescription);

		classReader.accept(renameMethodsVisitor, Opcodes.ASM5);

		LogMethodsCreator logMethodsCreator = new LogMethodsCreator(classWriter,p_classDescription);
		logMethodsCreator.createLogMethods();

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

	@Override
	protected byte[] getTransformedClass(ClassLoader p_classLoader
			, String p_className
			, Class<?> p_classBeingRedefined
			, ProtectionDomain p_protectionDomain
			, byte[] p_classByteCodes){

		ClassDescription classDescription = new ClassDescription(p_className);

		findLogAnnonation(classDescription,p_classByteCodes);

		if(!classDescription.getMethods().isEmpty()) {

			byte[] classByteCodes = renameAndCreateMethodsWithLog(classDescription, p_classByteCodes);

			writeToFileModifedClass(p_className, classByteCodes);

			return classByteCodes;
		}

		return p_classByteCodes;
	}
}
