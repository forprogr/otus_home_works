package ru.forprogr.hw.hw04autolog.transformer;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   08.05.2019 12:05
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
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

	@Override
//	protected byte[] getTransformedClass(ClassLoader p_classLoader
//			, String p_className
//			, Class<?> p_classBeingRedefined
//			, ProtectionDomain p_protectionDomain
//			, byte[] p_classByteCodes){
//
//
//		ClassNode classNode = new ClassNode();
//		ClassReader classReader = new ClassReader(p_classByteCodes);
//
//		classReader.accept(classNode, Opcodes.ASM5);
//
//		Iterator<MethodNode> iteratorMethod = classNode.methods.iterator();
//
//		String logAnnotation = "L"+ Log.class.getName()+";";
//
////		while (iteratorMethod.hasNext()){
////			MethodNode methodNode = iteratorMethod.next();
////
////
//////			if (!methodNode.visibleAnnotations.isEmpty()){
//////				Iterator<AnnotationNode> iteratorAnno = methodNode.visibleAnnotations.iterator();
//////				while(iteratorAnno.hasNext()){
//////					AnnotationNode annotationNode = iteratorAnno.next();
//////
//////					if (annotationNode.desc.equals(logAnnotation)){
//////						System.out.println(annotationNode.toString());
//////					}
//////				}
//////			}
////		}
//
//		for(MethodNode methodNode : classNode.methods){
//			System.out.println("<<< "+methodNode.desc);
//
//			for(LocalVariableNode localVariableNode : methodNode.localVariables){
//				System.out.println("<>>>"+localVariableNode.name+" "+localVariableNode.start.getLabel().toString());
//			}
//
////			for (AnnotationNode annotationNode : methodNode.invisibleAnnotations){
////				System.out.println("<>>>"+annotationNode.desc);
////			}
//		}
//
//
//		ClassWriter classWriter = new ClassWriter(0);
//		classNode.accept(classWriter);
//
//		byte[] finalClass = classWriter.toByteArray();
//
//		try (OutputStream fos = new FileOutputStream("proxyTree.class")) {
//			fos.write(finalClass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//return finalClass;
//
//
//		return p_classByteCodes;
//	}

//	protected byte[] getTransformedClass(ClassLoader p_classLoader
//			, String p_className
//			, Class<?> p_classBeingRedefined
//			, ProtectionDomain p_protectionDomain
//			, byte[] p_classByteCodes){
//
//		ClassReader classReader = new ClassReader(p_classByteCodes);
//		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
//
//		ClassVisitor classVisitor = new FindLogAnnotations(Opcodes.ASM5, classWriter,p_className);
//
//		classReader.accept(classVisitor, Opcodes.ASM5);
//
//		byte[] finalClass = classWriter.toByteArray();
//
//		try (OutputStream fos = new FileOutputStream("proxy.class")) {
//			fos.write(finalClass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//return finalClass;
//
//
//		return p_classByteCodes;
//	}

	protected byte[] getTransformedClass(ClassLoader p_classLoader
			, String p_className
			, Class<?> p_classBeingRedefined
			, ProtectionDomain p_protectionDomain
			, byte[] p_classByteCodes){

		ClassDescription classDescription = new ClassDescription(p_className);

		ClassReader classReader = new ClassReader(p_classByteCodes);
		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

		FindLogAnnotations findLogVisitor = new FindLogAnnotations(Opcodes.ASM5, classWriter, classDescription);

		classReader.accept(findLogVisitor, Opcodes.ASM5);

		RenameMethodsWithLog renameMethodsVisitor = new RenameMethodsWithLog(Opcodes.ASM5, classWriter, classDescription);

		classReader.accept(renameMethodsVisitor, Opcodes.ASM5);

		LogMethodsCreator logMethodsCreator = new LogMethodsCreator(classWriter,classDescription);

		byte[] finalClass = classWriter.toByteArray();

		try (OutputStream fos = new FileOutputStream("proxy2.class")) {
			fos.write(finalClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return finalClass;


		return p_classByteCodes;
	}
}
