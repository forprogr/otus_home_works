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
	public void calculation(int param
			,String p_str
			,Double p_double) {
		int i = 10+param;
		System.out.println(" i ="+i);
		System.out.println("*** calculation(int param,String p_str,Double p_double) ***");
		System.out.println("\tparam = "+param);
		System.out.println("\tp_str ="+p_str);
		System.out.println("\tp_double ="+p_double);
	}

	@Log
	public String calculationReturn(int param,String p_str,Double p_double) {
		int i = 11+param;
		System.out.println(" i ="+i);
		System.out.println("*** calculationReturn(int param,String p_str,Double p_double) ***");
		System.out.println("\tparam = "+param);
		System.out.println("\tp_str ="+p_str);
		System.out.println("\tp_double ="+p_double);

		String retValue = p_str+"33";
		System.out.println("\treturn value = "+retValue);
		return retValue;
	}

	@Log
	public String calculationNoParams() {
		System.out.println("*** calculationNoParams() ***");

		String retValue = "ret calculationNoParams()";
		System.out.println("\treturn value = "+retValue);
		return retValue;
	}

	@Log
	public void calculationNoParamsVoid() {
		System.out.println("*** calculationNoParamsVoid() ***");
	}

}
