package ru.forprogr.hw.hw02diylist.test;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   21.04.2019 10:18
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.junit.jupiter.api.*;
import ru.forprogr.hw.hw02diylist.*;

import java.util.ArrayList;
import java.util.Iterator;

public class DIYIteratorTest {

	private DIYarrayList<String> diy_list;
	private ArrayList<String> arr_list;

	private Iterator<String> diy_list_itr;
	private Iterator<String> arr_list_itr;

	@BeforeAll
	public static void beforeClass() {
		System.out.println("\n\nТест соответствия реализации DIYIterator эталону");
	}


	@BeforeEach
	public void beforeTest() {
		diy_list = new DIYarrayList<>();
		arr_list = new ArrayList<>();

		for (int i = 0; i<30; i++ ){
			diy_list.add("Str--"+i);
			arr_list.add("Str--"+i);
		}
		diy_list_itr = diy_list.iterator();
		arr_list_itr = arr_list.iterator();
	}

	@AfterEach
	public void afterTest() {
		if (diy_list!=null) {
			diy_list.clear();
		}
		if (arr_list!=null) {
			arr_list.clear();
		}

		diy_list_itr = null;
		arr_list_itr = null;

	}

	@AfterAll
	public static void afterClass() {
		System.out.println("Тест завершен");
	}

	@Test
	public void chk_hasNext(){

		System.out.println("<i> Тест boolean hasNext()..");

		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());

		diy_list.clear();
		arr_list.clear();
		arr_list_itr = arr_list.iterator();

		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());

		diy_list.add("test");
		arr_list.add("test");
		arr_list_itr = arr_list.iterator();

		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());

		arr_list_itr = arr_list.iterator();
		diy_list_itr.next();
		arr_list_itr.next();
		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());
	}

	@Test
	public void chk_next(){

		System.out.println("<i> Тест T next()..");

		while (diy_list_itr.hasNext() && arr_list_itr.hasNext()){
			String diy_elem = diy_list_itr.next();
			String arr_elem = arr_list_itr.next();
			Assertions.assertEquals(diy_elem,arr_elem);
		}
	}
}
