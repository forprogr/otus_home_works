package ru.forprogr.hw.hw04autolog;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 10:56
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import ru.forprogr.hw.hw04autolog.transformer.*;

public class LogAgent {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("\n<i> Start premain(String agentArgs, Instrumentation inst) - ru.forprogr.hw.hw04autolog");

		ArrayList<String> skippedPackages = new ArrayList<>();
		skippedPackages.add("java");
		skippedPackages.add("sun");
		skippedPackages.add("jdk");
		skippedPackages.add("org/jetbrains");
		skippedPackages.add("com/intellij");

		LogClassTransformer logClassTransformer = new LogClassTransformer(skippedPackages, Opcodes.ASM5);

		inst.addTransformer(logClassTransformer);
	}


}
