package ru.forprogr.hw.hw02diylist.test;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   21.04.2019 10:53
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import ru.forprogr.hw.hw02diylist.*;

import java.util.ArrayList;
import java.util.ListIterator;

public class DIYListIteratorTest {
	private DIYarrayList<String> diy_list;
	private ArrayList<String> arr_list;

	private ListIterator<String> diy_list_itr;
	private ListIterator<String> arr_list_itr;

	@BeforeAll
	public static void beforeClass() {
		System.out.println("\n\nТест соответствия реализации DIYListIterator эталону");
	}


	@BeforeEach
	public void beforeTest() {
		diy_list = new DIYarrayList<>();
		arr_list = new ArrayList<>();

		for (int i = 0; i<30; i++ ){
			diy_list.add("Str--"+i);
			arr_list.add("Str--"+i);
		}
		diy_list_itr = diy_list.listIterator();
		arr_list_itr = arr_list.listIterator();
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
		Assertions.assertTrue(diy_list_itr.hasNext());

		diy_list.clear();
		arr_list.clear();
		arr_list_itr = arr_list.listIterator();

		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());
		Assertions.assertFalse(diy_list_itr.hasNext());


		diy_list.add("test");
		arr_list.add("test");
		arr_list_itr = arr_list.listIterator();

		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());
		Assertions.assertTrue(diy_list_itr.hasNext());

		arr_list_itr = arr_list.listIterator();
		diy_list_itr.next();
		arr_list_itr.next();

		Assertions.assertEquals(diy_list_itr.hasNext(),arr_list_itr.hasNext());
		Assertions.assertFalse(diy_list_itr.hasNext());
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

	@Test
	public void chk_hasPrevious() {

		System.out.println("<i> Тест boolean hasPrevious()..");

		Assertions.assertEquals(diy_list_itr.hasPrevious(),arr_list_itr.hasPrevious());
		Assertions.assertFalse(diy_list_itr.hasPrevious());

		diy_list_itr.next();
		arr_list_itr.next();

		Assertions.assertEquals(diy_list_itr.hasPrevious(),arr_list_itr.hasPrevious());
		Assertions.assertTrue(diy_list_itr.hasPrevious());

		diy_list.clear();
		arr_list.clear();
		arr_list_itr = arr_list.listIterator();

		Assertions.assertEquals(diy_list_itr.hasPrevious(),arr_list_itr.hasPrevious());
		Assertions.assertFalse(diy_list_itr.hasPrevious());


	}

	@Test
	public void chk_previous(){

		System.out.println("<i> Тест T previous()..");

		while (diy_list_itr.hasNext() && arr_list_itr.hasNext()){
			diy_list_itr.next();
			arr_list_itr.next();
		}

		while (diy_list_itr.hasPrevious() && arr_list_itr.hasPrevious()){
			String diy_elem = diy_list_itr.previous();
			String arr_elem = arr_list_itr.previous();
			Assertions.assertEquals(diy_elem,arr_elem);
		}
	}

	@Test
	public void chk_next_previous_border(){
		System.out.println("<i> Тест границ next() && previous() ..");

		String diy_elem = diy_list_itr.next();
		String arr_elem = arr_list_itr.next();

		Assertions.assertEquals(diy_elem,arr_elem);
		arr_elem = "";
		diy_elem = "";

		arr_elem = arr_list_itr.previous();
		diy_elem = diy_list_itr.previous();

		Assertions.assertEquals(diy_elem,arr_elem);

		while (diy_list_itr.hasNext() && arr_list_itr.hasNext()){
			diy_elem = diy_list_itr.next();
			arr_elem = arr_list_itr.next();
			Assertions.assertEquals(diy_elem,arr_elem);
		}
		arr_elem = "";
		diy_elem = "";

		arr_elem = arr_list_itr.previous();
		diy_elem = diy_list_itr.previous();

		Assertions.assertEquals(diy_elem,arr_elem);
		arr_elem = "";
		diy_elem = "";

		diy_elem = diy_list_itr.next();
		arr_elem = arr_list_itr.next();

		Assertions.assertEquals(diy_elem,arr_elem);
	}

	@Test
	public void chk_nextIndex() {
		System.out.println("<i> Тест nextIndex() ..");

		Assertions.assertEquals(diy_list_itr.nextIndex(),arr_list_itr.nextIndex());

		while (diy_list_itr.hasNext() && arr_list_itr.hasNext()){
			diy_list_itr.next();
			arr_list_itr.next();
			Assertions.assertEquals(diy_list_itr.nextIndex(),arr_list_itr.nextIndex());
		}
		Assertions.assertEquals(diy_list_itr.nextIndex(),arr_list_itr.nextIndex());
		diy_list.clear();
		arr_list.clear();
		arr_list_itr = arr_list.listIterator();
		Assertions.assertEquals(diy_list_itr.nextIndex(),arr_list_itr.nextIndex());
	}

	@Test
	public void chk_previousIndex() {
		System.out.println("<i> Тест previousIndex() ..");

		Assertions.assertEquals(diy_list_itr.previousIndex(),arr_list_itr.previousIndex());

		while (diy_list_itr.hasNext() && arr_list_itr.hasNext()){
			diy_list_itr.next();
			arr_list_itr.next();
		}
		Assertions.assertEquals(diy_list_itr.previousIndex(),arr_list_itr.previousIndex());

		while (diy_list_itr.hasPrevious() && arr_list_itr.hasPrevious()){
			diy_list_itr.previous();
			arr_list_itr.previous();
			Assertions.assertEquals(diy_list_itr.previousIndex(),arr_list_itr.previousIndex());
		}

		diy_list.clear();
		arr_list.clear();
		arr_list_itr = arr_list.listIterator();
		Assertions.assertEquals(diy_list_itr.previousIndex(),arr_list_itr.previousIndex());
	}

	@Test
	public void chk_set() {
		System.out.println("<i> Тест set(T t)..");
		String set_str = "test";

		Assertions.assertThrows(IllegalStateException.class,()->{
			arr_list_itr.set(set_str);
		});

		Assertions.assertThrows(IllegalStateException.class,()->{
			diy_list_itr.set(set_str);
		});

		diy_list_itr.next();
		arr_list_itr.next();

		arr_list_itr.set(set_str);
		diy_list_itr.set(set_str);

		Assertions.assertIterableEquals(diy_list,arr_list);

		while (diy_list_itr.hasNext() && arr_list_itr.hasNext()){
			diy_list_itr.next();
			arr_list_itr.next();
			arr_list_itr.set(set_str);
			diy_list_itr.set(set_str);
		}
		Assertions.assertIterableEquals(diy_list,arr_list);

		String set_str2 = "test2";
		Assertions.assertDoesNotThrow(()->{
			arr_list_itr.set(set_str2);
		});

		Assertions.assertDoesNotThrow(()->{
			diy_list_itr.set(set_str2);
		});

		Assertions.assertIterableEquals(diy_list,arr_list);

		while (diy_list_itr.hasPrevious() && arr_list_itr.hasPrevious()){
			diy_list_itr.previous();
			arr_list_itr.previous();
			arr_list_itr.set(set_str2);
			diy_list_itr.set(set_str2);
		}
		Assertions.assertIterableEquals(diy_list,arr_list);

		arr_list_itr.set(set_str);
		diy_list_itr.set(set_str);

		Assertions.assertIterableEquals(diy_list,arr_list);
	}


}
