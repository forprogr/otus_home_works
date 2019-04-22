package ru.forprogr.hw.hw02diylist;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   20.04.2019 20:24
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DIYListIterator<T> extends DIYIterator<T> implements ListIterator<T> {

	//показывался ли вызовом next() первый элемент списка
	private boolean is_show_first = false;
	//показывался ли вызовом next() последний элемент списка
	private boolean is_show_last = false;

	protected boolean show_first() {return is_show_first;}
	protected void set_show_first(boolean p_is_show_first){
		is_show_first = p_is_show_first;
	}

	protected boolean show_last() {return is_show_last;}
	protected void set_show_last(boolean p_is_show_last){
		is_show_last = p_is_show_last;
	}

	public DIYListIterator(DIYarrayList<T> p_list){
		super(p_list);
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
		return (!list_for_iterator.isEmpty()
				&& !show_last());
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

		T elem;

		if (get_curr_index() >= list_for_iterator.last_elem_index()){
			elem = list_for_iterator.get(get_curr_index());
		} else {
			elem = list_for_iterator.get(inc_index());
		}

		set_show_last(get_curr_index() >= list_for_iterator.last_elem_index());

		if(!show_first()){
			set_show_first(get_curr_index() >= 0);
		}

		return elem;
	}


	/**
	 * Returns {@code true} if this list iterator has more elements when
	 * traversing the list in the reverse direction.  (In other words,
	 * returns {@code true} if {@link #previous} would return an element
	 * rather than throwing an exception.)
	 *
	 * @return {@code true} if the list iterator has more elements when
	 * traversing the list in the reverse direction
	 */
	@Override
	public boolean hasPrevious() {
		return (!list_for_iterator.isEmpty()
				&& (get_prev_index() >= 0
					|| show_first()));
	}

	/**
	 * Returns the previous element in the list and moves the cursor
	 * position backwards.  This method may be called repeatedly to
	 * iterate through the list backwards, or intermixed with calls to
	 * {@link #next} to go back and forth.  (Note that alternating calls
	 * to {@code next} and {@code previous} will return the same
	 * element repeatedly.)
	 *
	 * @return the previous element in the list
	 * @throws NoSuchElementException if the iteration has no previous
	 *                                element
	 */
	@Override
	public T previous() {
		T elem;
		if (!hasPrevious()){
			throw new NoSuchElementException();
		}

		if (get_curr_index() == 0 && show_first()){
			elem = list_for_iterator.get(get_curr_index());
			dec_index();
			set_show_first(false);
		} else if(get_curr_index() == list_for_iterator.last_elem_index() && show_last()) {
			elem = list_for_iterator.get(get_curr_index());
			set_show_last(false);
		} else {
			elem = list_for_iterator.get(dec_index());
		}

		return elem;
	}

	/**
	 * Returns the index of the element that would be returned by a
	 * subsequent call to {@link #next}. (Returns list size if the list
	 * iterator is at the end of the list.)
	 *
	 * @return the index of the element that would be returned by a
	 * subsequent call to {@code next}, or list size if the list
	 * iterator is at the end of the list
	 */
	@Override
	public int nextIndex() {
		if (!hasNext()) {
			return list_for_iterator.size();
		}
		return get_next_index();
	}

	/**
	 * Returns the index of the element that would be returned by a
	 * subsequent call to {@link #previous}. (Returns -1 if the list
	 * iterator is at the beginning of the list.)
	 *
	 * @return the index of the element that would be returned by a
	 * subsequent call to {@code previous}, or -1 if the list
	 * iterator is at the beginning of the list
	 */
	@Override
	public int previousIndex() {
		if(get_curr_index() == list_for_iterator.last_elem_index() && show_last()){
			return get_curr_index();
		}
		return get_prev_index();
	}

	/**
	 * Replaces the last element returned by {@link #next} or
	 * {@link #previous} with the specified element (optional operation).
	 * This call can be made only if neither {@link #remove} nor {@link
	 * #add} have been called after the last call to {@code next} or
	 * {@code previous}.
	 *
	 * @param t the element with which to replace the last element returned by
	 *          {@code next} or {@code previous}
	 * @throws UnsupportedOperationException if the {@code set} operation
	 *                                       is not supported by this list iterator
	 * @throws ClassCastException            if the class of the specified element
	 *                                       prevents it from being added to this list
	 * @throws IllegalArgumentException      if some aspect of the specified
	 *                                       element prevents it from being added to this list
	 * @throws IllegalStateException         if neither {@code next} nor
	 *                                       {@code previous} have been called, or {@code remove} or
	 *                                       {@code add} have been called after the last call to
	 *                                       {@code next} or {@code previous}
	 */
	@Override
	public void set(T t) {
		if (!show_first() ){
			throw new IllegalStateException();
		}
		list_for_iterator.set(get_curr_index(),t);
	}

	/**
	 * Inserts the specified element into the list (optional operation).
	 * The element is inserted immediately before the element that
	 * would be returned by {@link #next}, if any, and after the element
	 * that would be returned by {@link #previous}, if any.  (If the
	 * list contains no elements, the new element becomes the sole element
	 * on the list.)  The new element is inserted before the implicit
	 * cursor: a subsequent call to {@code next} would be unaffected, and a
	 * subsequent call to {@code previous} would return the new element.
	 * (This call increases by one the value that would be returned by a
	 * call to {@code nextIndex} or {@code previousIndex}.)
	 *
	 * @param t the element to insert
	 * @throws UnsupportedOperationException if the {@code add} method is
	 *                                       not supported by this list iterator
	 * @throws ClassCastException            if the class of the specified element
	 *                                       prevents it from being added to this list
	 * @throws IllegalArgumentException      if some aspect of this element
	 *                                       prevents it from being added to this list
	 */
	//добавить элемент перед элементом который вернется по next()
	@Override
	public void add(T t) {
		throw new  UnsupportedOperationException();
	}
}