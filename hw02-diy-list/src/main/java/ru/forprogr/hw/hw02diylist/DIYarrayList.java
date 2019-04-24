package ru.forprogr.hw.hw02diylist;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   12.04.2019 17:18
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.*;

public class DIYarrayList<T> implements List<T>{

	//хранилище элементов списка
	private Object[] elements_arr;

	//начальный размер массива элементов
	private final int BEGIN_LENGTH_ARRAY = 10;

	//множитель увеличения размер массива элементов
	private final int MULTIPLIER_LENGTH_ARRAY = 2;

	//количество элементов в списке
	private int count_elements;

	//индекс последнего элемента в списке
	public int last_elem_index(){
		if (count_elements == 0){
			return 0;
		}

		return count_elements-1;
	}

	//увеличим размер массива чтобы поместились еще p_count_add_elem элементов
	private void expand_arr(int p_count_add_elem){
		int new_count_elem = count_elements+p_count_add_elem;

		if (elements_arr.length<(new_count_elem)) {
			int new_length_arr = new_count_elem * MULTIPLIER_LENGTH_ARRAY;
			Object[] new_elements_arr = new Object[new_length_arr];

			System.arraycopy(elements_arr,0,new_elements_arr,0,elements_arr.length);

			elements_arr = new_elements_arr;
		}
	}

	public DIYarrayList(){
		super();
		clear();
	}

	@Override
	public int size() {
		return count_elements;
	}

	@Override
	public boolean isEmpty() {
		return count_elements == 0;
	}

	@Override
	public boolean contains(Object o) {
		return find_elem(o,false) != -1 ;
	}

	@Override
	public Object[] toArray() {
		Object[] ret_arr = new Object[size()];

		System.arraycopy(elements_arr,0,ret_arr,0,size());

		return ret_arr;
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {

		T1[] ret_arr;
		if (a.length<size()){
			ret_arr = Arrays.copyOf(a,size());
		} else {
			ret_arr = Arrays.copyOf(a,a.length);
		}

		for(int i = 0; i < size(); i++){
			ret_arr[i] = (T1) get(i);
		}

		return ret_arr;
	}

	//добавление элементов в конец
	@Override
	public boolean add(T t) {
		expand_arr(1);

		elements_arr[count_elements] = t;
		count_elements++;

		return true;
	}

	@Override
	public T remove(int index) {
		chk_out_of_bounds(index,false);

		T ret_obj = get(index);

		if (size() == 1){
			clear();
		} else {

			Object[] new_elem_arr = new Object[size() - 1];

			System.arraycopy(elements_arr, 0, new_elem_arr, 0, index);
			System.arraycopy(elements_arr, index+1, new_elem_arr, index,size()-index-1);

			elements_arr = new_elem_arr;
			count_elements = elements_arr.length;
		}

		return ret_obj;
	}

	@Override
	public boolean remove(Object o) {
		int remove_elem_indx = indexOf(o);

		if (remove_elem_indx != -1){
			remove(remove_elem_indx);

			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ret_is_remove = false;
		boolean is_remove_elem;

		for (Object obj : c){
			is_remove_elem = remove(obj);
			if (!ret_is_remove && is_remove_elem){
				ret_is_remove = is_remove_elem;
			}
		}

		return ret_is_remove;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if(c.isEmpty()) {
			return true;
		}

		for (Object obj : c){
			if (!contains(obj)){
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		if (c.isEmpty()) {
			return false;
		}

		Object[] added_elements_arr = c.toArray();
		expand_arr(added_elements_arr.length);

		System.arraycopy(added_elements_arr,0,elements_arr,last_elem_index()+1,added_elements_arr.length);

		count_elements += added_elements_arr.length;

		return true;

	}

	@Override
	public void add(int index, T element) {
		chk_out_of_bounds(index,true);

		expand_arr(1);

		System.arraycopy(elements_arr, index, elements_arr, index + 1, size()-index);

		elements_arr[index] = element;
		count_elements++;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		chk_out_of_bounds(index,true);

		if (c.isEmpty()) {
			return false;
		}

		Object[] added_elements_arr = c.toArray();
		expand_arr(added_elements_arr.length);

		System.arraycopy(elements_arr, index, elements_arr, index + added_elements_arr.length, size()-index);

		System.arraycopy(added_elements_arr, 0, elements_arr, index, added_elements_arr.length);

		count_elements += added_elements_arr.length;

		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if(c.isEmpty()) {
			clear();
			return true;
		}

		DIYarrayList<T> retain_elems = new DIYarrayList<>();

		for (Object obj : elements_arr){
			if(c.contains(obj)){
				retain_elems.add((T) obj);
			}
		}

		elements_arr = retain_elems.toArray();
		count_elements = retain_elems.size();

		return !retain_elems.isEmpty();

	}

	@Override
	public void clear() {
		count_elements = 0;
		elements_arr = new Object[BEGIN_LENGTH_ARRAY];
	}

	//проверка на выход индекса за пределы диапазона
	private void chk_out_of_bounds(int p_index,boolean p_can_equal_size){
		if (p_index < 0
				|| (p_index > last_elem_index() && !p_can_equal_size)
				|| (p_index > size() && p_can_equal_size)
		){
			throw new IndexOutOfBoundsException(p_index);
		}
	}

	private void chk_out_of_bounds(int p_index_from, int p_index_to,boolean p_can_equal_size){
		chk_out_of_bounds(p_index_from,p_can_equal_size);
		chk_out_of_bounds(p_index_to,p_can_equal_size);

		if (p_index_from > p_index_to){
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public T get(int index) {
		chk_out_of_bounds(index,false);

		return (T) elements_arr[index];
	}

	@Override
	public T set(int index, T element) {
		chk_out_of_bounds(index,false);

		T old_element = get(index);

		elements_arr[index] = element;

		return old_element;
	}

	//поиск элемента
	private int find_elem(Object p_obj, boolean p_is_find_last){
		int index_elem = -1;

		if (!isEmpty() && p_obj != null) {
			if (p_is_find_last){
				for(int indx = last_elem_index(); indx >= 0; indx--){
					if (p_obj.equals(get(indx))){
						return indx;
					}
				}
			} else {
				for (int indx = 0; indx < size(); indx++) {
					if (p_obj.equals(get(indx))){
						return indx;
					}
				}
			}
		}
		return index_elem;
	}

	@Override
	public int indexOf(Object o) {
		return find_elem(o,false);
	}

	@Override
	public int lastIndexOf(Object o) {
		return find_elem(o,true);
	}


	@Override
	public Iterator<T> iterator() {
		return new DIYIterator<>(this);
	}


	@Override
	public ListIterator<T> listIterator() {
		return new DIYListIterator<>(this);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		if(true) {
			throw new UnsupportedOperationException();
		}
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		chk_out_of_bounds(fromIndex,toIndex,true);

		DIYarrayList<T> sub_list = new DIYarrayList<>();

		if(fromIndex == toIndex){
			return sub_list;
		}

		for (int indx = fromIndex; indx < toIndex; indx++) {
			sub_list.add(get(indx));
		}

		return sub_list;
	}


	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("[");

		if (!isEmpty()) {

			for (int i = 0; i < size(); i++) {
				str.append(elements_arr[i]);
				if (i != last_elem_index()) {
					str.append(", ");
				} else {
					str.append("]");
				}

			}
		} else {
			str.append("]");
		}

		return str.toString();
	}
}
