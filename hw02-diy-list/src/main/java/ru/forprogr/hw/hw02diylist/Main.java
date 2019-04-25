package ru.forprogr.hw.hw02diylist;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   12.04.2019 16:53
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	private static final boolean show_all = true;

	private static void printArrs(String p_strDiyList,String p_strArrList){
		System.out.println("\t--- diyList ---");
		System.out.println("\t"+p_strDiyList);
		System.out.println("\t--- arrList ---");
		System.out.println("\t"+p_strArrList);
	}

	private static void isEqual(String p_msg,List<String> p_diyList,List<String> p_arrList){
		System.out.println("\n** "+p_msg+" **");
		String strDiyList = p_diyList.toString();
		String strArrList = p_arrList.toString();

		if (strDiyList.equals(strArrList)){
			System.out.println("<Ok> diyList == arrList");
		} else {
			System.out.println("<ERR> diyList != arrList");
		}

		if (show_all || !strDiyList.equals(strArrList)){
			printArrs(strDiyList,strArrList);
		}
	}

	public static void main(String[] args){
		System.out.println("<*> test ru.forprogr.hw.hw02diylist <*>");

		DIYarrayList<String> diyList = new DIYarrayList<>();
		ArrayList<String> arrList = new ArrayList<>();
		ArrayList<String> arrListSrc = new ArrayList<>();

		//
		for (int i = 0;i<22;i++ ){
			diyList.add("Str--"+i);
			arrList.add("Str--"+i);
			arrListSrc.add(i+"--arrListSrc");
		}

		isEqual("add(T t)",diyList,arrList);

		//-------------------------
		String[] addAllArr = {"12","11","10"};
		Collections.addAll(diyList, addAllArr);
		Collections.addAll(arrList, addAllArr);

		isEqual("Collections.addAll",diyList,arrList);

		//-------------------------
		diyList.clear();
		arrList.clear();

		isEqual("clear()",diyList,arrList);

		//-------------------------
		for (int i = 24;i>0;i-- ){
			diyList.add("**Str--"+i);
			arrList.add("**Str--"+i);
		}

		isEqual("Before Collections.copy",diyList,arrList);

		Collections.copy(diyList, arrListSrc);
		Collections.copy(arrList, arrListSrc);
		isEqual("Collections.copy",diyList,arrList);

		//-------------------------

		Collections.sort(diyList, (str1,str2) -> str1.compareTo(str2));
		Collections.sort(arrList, (str1,str2) -> str1.compareTo(str2));
		isEqual("Collections.sort",diyList,arrList);

		//-------------------------

	}
}
