package ru.forprogr.hw.hw04autolog;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   01.05.2019 20:06
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

//Run:
//> java -javaagent:hw04-auto-log.jar -jar hw04-auto-log.jar

public class Main {
	public static void main(String[] args){
		System.out.println("\n<i> Start main(String[] args) - ru.forprogr.hw.hw04autolog");

		TestLogging testLogging = new TestLogging();
		testLogging.calculation(22,"test",2.2);
		testLogging.calculationReturn(33,"test return1",3.3);
		testLogging.calculationNoParams();
		testLogging.calculationNoParamsVoid();
	}
}
