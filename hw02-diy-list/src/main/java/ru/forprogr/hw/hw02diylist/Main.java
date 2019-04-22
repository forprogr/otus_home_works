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

	private static void print_arrs(String p_str_diy_list,String p_str_arr_list){
		System.out.println("\t--- diy_list ---");
		System.out.println("\t"+p_str_diy_list);
		System.out.println("\t--- arr_list ---");
		System.out.println("\t"+p_str_arr_list);
	}

	private static void is_eq(String p_msg,List<String> p_diy_list,List<String> p_arr_list){
		System.out.println("\n** "+p_msg+" **");
		String str_diy_list = p_diy_list.toString();
		String str_arr_list = p_arr_list.toString();

		if (str_diy_list.equals(str_arr_list)){
			System.out.println("<Ok> diy_list == arr_list");
		} else {
			System.out.println("<ERR> diy_list != arr_list");
		}

		if (show_all || !str_diy_list.equals(str_arr_list)){
			print_arrs(str_diy_list,str_arr_list);
		}
	}

	public static void main(String[] args){
		System.out.println("<*> test ru.forprogr.hw.hw02diylist <*>");

		DIYarrayList<String> diy_list = new DIYarrayList<>();
		ArrayList<String> arr_list = new ArrayList<>();
		ArrayList<String> arr_list_src = new ArrayList<>();

		//
		for (int i = 0;i<22;i++ ){
			diy_list.add("Str--"+i);
			arr_list.add("Str--"+i);
			arr_list_src.add(i+"--arr_list_src");
		}

		is_eq("add(T t)",diy_list,arr_list);

		//-------------------------
		String[] add_all_arr = {"12","11","10"};
		Collections.addAll(diy_list, add_all_arr);
		Collections.addAll(arr_list, add_all_arr);

		is_eq("Collections.addAll",diy_list,arr_list);

		//-------------------------
		diy_list.clear();
		arr_list.clear();

		is_eq("clear()",diy_list,arr_list);

		//-------------------------
		for (int i = 24;i>0;i-- ){
			diy_list.add("**Str--"+i);
			arr_list.add("**Str--"+i);
		}

		is_eq("Before Collections.copy",diy_list,arr_list);

		Collections.copy(diy_list, arr_list_src);
		Collections.copy(arr_list, arr_list_src);
		is_eq("Collections.copy",diy_list,arr_list);

		//-------------------------

		Collections.sort(diy_list, (str1,str2) -> str1.compareTo(str2));
		Collections.sort(arr_list, (str1,str2) -> str1.compareTo(str2));
		is_eq("Collections.sort",diy_list,arr_list);

		//-------------------------

	}
}
