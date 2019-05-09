package ru.forprogr.hw.hw04autolog;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   05.05.2019 15:19
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import ru.forprogr.hw.hw04autolog.anno.Log;

public class TestLogging {
	@Log
	public void calculation(int param,String p_str,Double p_double) {
		int i = 10+param;
		System.out.println(" i ="+i);
		System.out.println("<calculation> param = "+param);
		System.out.println("\tp_str ="+p_str);
		System.out.println("\tp_double ="+p_double);
	}

//	public void calculation2(int param,String p_str,Double p_double) {
//		System.out.println("logged param param = "+param);
//		System.out.println("logged param p_str ="+p_str);
//		System.out.println("logged param p_double ="+p_double);
//
//		calculationProxy(param,p_str,p_double);
//	}
//
//	public void calculationProxy(int param,String p_str,Double p_double) {
//		int i = 10+param;
//		System.out.println(" i ="+i);
//		System.out.println("<calculation> param = "+param);
//		System.out.println("\tp_str ="+p_str);
//		System.out.println("\tp_double ="+p_double);
//	}
}
