package ru.forprogr.hw.hw04autolog;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   01.05.2019 20:06
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

public class Main {
	public static void main(String[] args){
		System.out.println("ru.forprogr.hw.hw04autolog");

		TestLogging testLogging = new TestLogging();
		testLogging.calculation(22,"test",2.2);
		//testLogging.calculation2(22,"test",2.2);
	}
}
