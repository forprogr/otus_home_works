package ru.forprogr.hw.hw02diylist;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   20.04.2019 20:19
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DIYIterator<T> implements Iterator<T> {
	//список на котором работает итератор
	private DIYarrayList<T> list_for_iterator;

	protected void set_list_for_iterator(DIYarrayList<T> p_list_for_iterator){
		list_for_iterator = p_list_for_iterator;
	}

	protected DIYarrayList<T> get_list_for_iterator(){
		return list_for_iterator;
	}


	//номер индекса текущей записи в итераторе
	private int curr_elem_index = -1;

	public DIYIterator(DIYarrayList<T> p_list){
		set_list_for_iterator(p_list);
	}

	protected int get_curr_index(){return curr_elem_index;}

	protected int get_next_index(){return curr_elem_index+1;}

	protected int get_prev_index(){

		if (curr_elem_index<0){
			return -1;
		}
		return curr_elem_index-1;
	}

	protected int inc_index(){
		curr_elem_index++;
		return curr_elem_index;
	}

	protected int dec_index(){
		curr_elem_index--;
		if (curr_elem_index < -1){
			curr_elem_index = -1;
		}
		return curr_elem_index;
	}

	/**
	 * Returns {@code true} if the iteration has more elements.
	 * (In other words, returns {@code true} if {@link #next} would
	 * return an element rather than throwing an exception.)
	 *
	 * @return {@code true} if the iteration has more elements
	 */
	@Override
	public boolean hasNext() {
		return (!get_list_for_iterator().isEmpty()
				&& (get_next_index()<=get_list_for_iterator().last_elem_index()));
	}

	/**
	 * Returns the next element in the iteration.
	 *
	 * @return the next element in the iteration
	 * @throws NoSuchElementException if the iteration has no more elements
	 */
	@Override
	public T next() {
		if (!hasNext()){
			throw new NoSuchElementException();
		}

		T elem = get_list_for_iterator().get(inc_index());

		return elem;
	}

	/**
	 * Removes from the underlying collection the last element returned
	 * by this iterator (optional operation).  This method can be called
	 * only once per call to {@link #next}.
	 * <p>
	 * The behavior of an iterator is unspecified if the underlying collection
	 * is modified while the iteration is in progress in any way other than by
	 * calling this method, unless an overriding class has specified a
	 * concurrent modification policy.
	 * <p>
	 * The behavior of an iterator is unspecified if this method is called
	 * after a call to the {@link #forEachRemaining forEachRemaining} method.
	 *
	 * @implSpec
	 * The default implementation throws an instance of
	 * {@link UnsupportedOperationException} and performs no other action.
	 *
	 * @throws UnsupportedOperationException if the {@code remove}
	 *         operation is not supported by this iterator
	 *
	 * @throws IllegalStateException if the {@code next} method has not
	 *         yet been called, or the {@code remove} method has already
	 *         been called after the last call to the {@code next}
	 *         method
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove");
	}

}