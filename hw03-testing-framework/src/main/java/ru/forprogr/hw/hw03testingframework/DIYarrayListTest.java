package ru.forprogr.hw.hw03testingframework;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   28.04.2019 21:02
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import ru.forprogr.hw.hw03testingframework.anno.*;

public class DIYarrayListTest {

		private ArrayList<String> diy_list;
		private ArrayList<String> arr_list;
		private ArrayList<String> src_list;

		@BeforeAll
		public static void beforeClass() {
			System.out.println("\nТест соответствия реализации DIYarrayList эталону");
			//throw new NullPointerException("beforeClass - err"); //DO error
		}

		@BeforeAll
		public void beforeClass2() {
			System.out.println("\nне статичный");
			//throw new NullPointerException("beforeClass - err"); //DO error
		}


		@BeforeTest
		public void beforeTest() {
			System.out.println("Before Test init lists");

			diy_list = new ArrayList<>();
			arr_list = new ArrayList<>();

			src_list = new ArrayList<>();

			for (int i = 0; i<30; i++ ){
				diy_list.add("Str--"+i);
				arr_list.add("Str--"+i);
				src_list.add("--test-"+i+"-");
			}

			//throw new NullPointerException("beforeTest - err"); //DO error
		}

		@AfterTest
		public void afterTest() {
			if (diy_list!=null) {
				diy_list.clear();
			}
			if (arr_list!=null) {
				arr_list.clear();
			}
			System.out.println("After Test clear lists");
		}

		@AfterAll
		public static void afterClass() {
			System.out.println("Тест завершен\n");
		}


		@Test
		public void chk_add(){

			System.out.println("<i> Тест add()..");

			diy_list.add("testINTO");
			arr_list.add("testINTO");

			diy_list.add("testINTO2");
			arr_list.add("testINTO2");
			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");

			diy_list.clear();
			arr_list.clear();

			diy_list.add("testINTO2");
			arr_list.add("testINTO2");
			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");

		}

		@Test
		public void chk_add_indx_elem(){

			System.out.println("<i> Тест add(index,element)..");

			diy_list.add(6,"testINTO");
			arr_list.add(6,"testINTO");

			diy_list.add(0,"testBEG");
			arr_list.add(0,"testBEG1"); //DO error

			arr_list.add(arr_list.size(),"testEND");
			diy_list.add(diy_list.size(),"testEND");

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_clear(){

			System.out.println("<i> Тест clear()..");

			diy_list.clear();
			arr_list.clear();

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_addAll_coll(){

			System.out.println("<i> Тест addAll(Collection<? extends T> c)..");

			diy_list.addAll(src_list);
			arr_list.addAll(src_list);

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_retainAll_coll(){

			System.out.println("<i> Тест retainAll(Collection<?> c) ..");

			diy_list.addAll(src_list);
			arr_list.addAll(src_list);
			diy_list.retainAll(src_list);
			arr_list.retainAll(src_list);
			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_collection_add(){

			System.out.println("<i> Тест Collections.addAll(coll,arr)..");

			String[] add_all_arr = {"12","11","10"};
			Collections.addAll(diy_list, add_all_arr);
			Collections.addAll(arr_list, add_all_arr);
			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_collection_copy(){

			System.out.println("<i> Тест Collections.copy(diy_list, list_src)..");

			Collections.copy(diy_list, src_list);
			Collections.copy(arr_list, src_list);

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_collection_sort(){

			System.out.println("<i> Тест Collections.sort(diy_list,compr)..");

			Collections.sort(diy_list, (str1,str2) -> str1.compareTo(str2));
			Collections.sort(arr_list, (str1,str2) -> str1.compareTo(str2));

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_subList(){

			System.out.println("<i> Тест subList(int fromIndex, int toIndex);..");

			List<String> diy_sub_list = diy_list.subList(0,8);
			List<String> arr_sub_list = arr_list.subList(0,8);
			Assertions.assertIterableEquals(diy_sub_list,arr_sub_list,"<Err> begin - diy_sub_list != arr_sub_list");

			diy_sub_list = diy_list.subList(10,16);
			arr_sub_list =arr_list.subList(10,17); //DO error
			Assertions.assertIterableEquals(diy_sub_list,arr_sub_list,"<Err> into - diy_sub_list != arr_sub_list");

			diy_sub_list = diy_list.subList(5,diy_list.size()-1);
			arr_sub_list =arr_list.subList(5,arr_list.size()-1);
			Assertions.assertIterableEquals(diy_sub_list,arr_sub_list,"<Err> end - diy_sub_list != arr_sub_list");
		}

		@Test
		public void chk_indexOf(){

			System.out.println("<i> Тест indexOf(Object o)..");
			String find_str = "Str--22";
			Assertions.assertEquals(diy_list.indexOf(find_str),arr_list.indexOf(find_str));

		}
		@Test
		public void chk_lastIndexOf(){

			System.out.println("<i> Тест lastIndexOf(Object o)..");
			diy_list.add("Str--22");
			arr_list.add("Str--22");

			diy_list.add("Str--END");
			arr_list.add("Str--END");

			String find_str = "Str--22";
			Assertions.assertEquals(diy_list.lastIndexOf(find_str),arr_list.lastIndexOf(find_str));
		}
		@Test
		public void chk_set(){

			System.out.println("<i> Тест set(int index, T element);..");

			String old_elem_diy = diy_list.set(12,"setINTO");
			String old_elem_arr = arr_list.set(12,"setINTO");

			Assertions.assertEquals(old_elem_diy,old_elem_arr);

			old_elem_diy = diy_list.set(0,"setBEG");
			old_elem_arr = arr_list.set(0,"setBEG");

			Assertions.assertEquals(old_elem_diy,old_elem_arr);

			old_elem_arr = arr_list.set(arr_list.size()-1,"setEND");
			old_elem_diy = diy_list.set(diy_list.size()-1,"setEND");

			Assertions.assertEquals(old_elem_diy,old_elem_arr);

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}
		@Test
		public void chk_get(){

			System.out.println("<i> Тест get(int index)..");

			String elem_diy = diy_list.get(14);
			String elem_arr = arr_list.get(14);

			Assertions.assertEquals(elem_diy,elem_arr);

			elem_diy = diy_list.get(0);
			elem_arr = arr_list.get(0);

			Assertions.assertEquals(elem_diy,elem_arr);

			elem_diy = arr_list.get(arr_list.size()-1);
			elem_arr = diy_list.get(diy_list.size()-1);

			Assertions.assertEquals(elem_diy,elem_arr);

		}
		@Test
		public void chk_size(){

			System.out.println("<i> Тест size()..");

			Assertions.assertEquals(diy_list.size(),arr_list.size());

			for (int i = 0; i<40; i++ ){
				diy_list.add("S-"+i);
				arr_list.add("S-"+i);

			}

			Assertions.assertEquals(diy_list.size(),arr_list.size());

			diy_list.clear();
			arr_list.clear();

			Assertions.assertEquals(diy_list.size(),arr_list.size());

		}

		@Test
		public void chk_isEmpty(){

			System.out.println("<i> Тест isEmpty()..");

			Assertions.assertEquals(diy_list.isEmpty(),arr_list.isEmpty());

			diy_list.clear();
			arr_list.clear();

			Assertions.assertEquals(diy_list.isEmpty(),arr_list.isEmpty());

		}

		@Test
		public void chk_contains_obj(){

			System.out.println("<i> Тест contains(Object o)..");

			String cont_str = "--no_elem--";

			Assertions.assertEquals(diy_list.contains(cont_str),arr_list.contains(cont_str));

			cont_str = "Str--0";

			Assertions.assertEquals(diy_list.contains(cont_str),arr_list.contains(cont_str));

			cont_str = "Str--8";

			Assertions.assertEquals(diy_list.contains(cont_str),arr_list.contains(cont_str));

			cont_str = "Str--END";
			diy_list.add(cont_str);
			arr_list.add(cont_str);

			Assertions.assertEquals(diy_list.contains(cont_str),arr_list.contains(cont_str));

		}

		@Test
		public void chk_toArray(){

			System.out.println("<i> Тест toArray()..");

			Assertions.assertArrayEquals(diy_list.toArray(),arr_list.toArray(),"<Err> diy_list != arr_list");

			String[] diy_list_arr = new String[10];
			String[] arr_list_arr = new String[10];

			for (int i=0;i<diy_list_arr.length;i++){
				diy_list_arr[i]="sr+"+i;
				arr_list_arr[i]="sr+"+i;
			}

			Assertions.assertArrayEquals(diy_list.toArray(diy_list_arr),arr_list.toArray(arr_list_arr),"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_remove_index(){

			System.out.println("<i> Тест T remove(int index)..");

			String old_elem_diy = diy_list.remove(12);
			String old_elem_arr = arr_list.remove(12);

			Assertions.assertEquals(old_elem_diy,old_elem_arr);

			old_elem_diy = diy_list.remove(0);
			old_elem_arr = arr_list.remove(0);

			Assertions.assertEquals(old_elem_diy,old_elem_arr);

			old_elem_diy = diy_list.remove(diy_list.size()-1);
			old_elem_arr = arr_list.remove(arr_list.size()-1);

			Assertions.assertEquals(old_elem_diy,old_elem_arr);

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_remove_obj(){

			System.out.println("<i> Тест remove(Object o)..");

			Assertions.assertEquals(diy_list.remove("Str--0"),arr_list.remove("Str--0"));

			Assertions.assertEquals(diy_list.remove("Str--10"),arr_list.remove("Str--10"));

			Assertions.assertEquals(diy_list.remove("setEND"),arr_list.remove("setEND"));

			arr_list.set(arr_list.size()-1,"setEND");
			diy_list.set(diy_list.size()-1,"setEND");

			Assertions.assertEquals(diy_list.remove("setEND"),arr_list.remove("setEND"));

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_addAll_index_Coll(){

			System.out.println("<i> Тест boolean addAll(int index, Collection<? extends T> c)..");

			Assertions.assertEquals(diy_list.addAll(0,src_list),arr_list.addAll(0,src_list));

			Assertions.assertEquals(diy_list.addAll(8,src_list),arr_list.addAll(8,src_list));

			Assertions.assertEquals(diy_list.addAll(diy_list.size()-1,src_list),arr_list.addAll(arr_list.size()-1,src_list));

			Assertions.assertEquals(diy_list.addAll(diy_list.size(),src_list),arr_list.addAll(arr_list.size(),src_list));

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_containsAll_coll(){

			System.out.println("<i> Тест containsAll(Collection<?> c)..");

			String cont_str = "--no_elem--";

			Assertions.assertEquals(diy_list.containsAll(src_list),arr_list.containsAll(src_list));

			diy_list.addAll(7,src_list);
			arr_list.addAll(7,src_list);

			Assertions.assertEquals(diy_list.containsAll(src_list),arr_list.containsAll(src_list));

			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

		@Test
		public void chk_removeAll_coll(){

			System.out.println("<i> Тест removeAll(Collection<?> c)..");

			Assertions.assertEquals(diy_list.removeAll(src_list),arr_list.removeAll(src_list));

			diy_list.addAll(7,src_list);
			arr_list.addAll(7,src_list);

			Assertions.assertEquals(diy_list.removeAll(src_list),arr_list.removeAll(src_list));

			diy_list.addAll(0,src_list);
			arr_list.addAll(0,src_list);

			Assertions.assertEquals(diy_list.removeAll(src_list),arr_list.removeAll(src_list));

			diy_list.addAll(diy_list.size(),src_list);
			arr_list.addAll(arr_list.size(),src_list);

			Assertions.assertEquals(diy_list.removeAll(src_list),arr_list.removeAll(src_list));

			List<String> tst_list = new ArrayList<>();

			tst_list.addAll(src_list);
			tst_list.add("test1");
			tst_list.add("test2");

			diy_list.addAll(7,src_list);
			arr_list.addAll(7,src_list);

			Assertions.assertEquals(diy_list.removeAll(tst_list),arr_list.removeAll(tst_list));


			Assertions.assertIterableEquals(diy_list,arr_list,"<Err> diy_list != arr_list");
		}

	}
