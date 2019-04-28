package ru.forprogr.hw.hw03testingframework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 11:44
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

public class Main {

	public static void main(String[] args){
		//запускаю на немного переделанным классом тестирования из Домашнего задания #2
		TestsRunner testsRunner = new TestsRunner("ru.forprogr.hw.hw03testingframework.DIYarrayListTest");
		testsRunner.runTests();
	}
}
