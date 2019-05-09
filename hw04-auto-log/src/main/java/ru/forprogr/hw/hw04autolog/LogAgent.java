package ru.forprogr.hw.hw04autolog;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 10:56
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------
import java.lang.instrument.Instrumentation;

import ru.forprogr.hw.hw04autolog.transformer.*;

public class LogAgent {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("premain");

		LogClassTransformer logClassTransformer = new LogClassTransformer();
		logClassTransformer.addSkippedClassNames("java");
		logClassTransformer.addSkippedClassNames("sun");
		logClassTransformer.addSkippedClassNames("jdk");
		logClassTransformer.addSkippedClassNames("org/jetbrains");
		logClassTransformer.addSkippedClassNames("com/intellij");

		inst.addTransformer(logClassTransformer);
	}


}
