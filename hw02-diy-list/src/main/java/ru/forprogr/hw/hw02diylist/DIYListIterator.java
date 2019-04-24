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

	private boolean isShowFirst = false;
	private boolean isShowLast = false;

	private boolean getIsShowFirst() {return isShowFirst;}
	private void setIsShowFirst(boolean p_isShowFirst){
		isShowFirst = p_isShowFirst;
	}

	private boolean getIsShowLast() {return isShowLast;}
	private void setIsShowLast(boolean p_isShowLast){
		isShowLast = p_isShowLast;
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
		return (!getIteratedList().isEmpty()
				&& !getIsShowLast());
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

		if (getCurrentIndex() >= getIteratedList().getLastIndex()){
			elem = getIteratedList().get(getCurrentIndex());
		} else {
			elem = getIteratedList().get(incIndex());
		}

		setIsShowLast(getCurrentIndex() >= getIteratedList().getLastIndex());

		if(!getIsShowFirst()){
			setIsShowFirst(getCurrentIndex() >= 0);
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
		return (!getIteratedList().isEmpty()
				&& (getPrevIndex() >= 0
					|| getIsShowFirst()));
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

		if (getCurrentIndex() == 0 && getIsShowFirst()){
			elem = getIteratedList().get(getCurrentIndex());
			decIndex();
			setIsShowFirst(false);
		} else if(getCurrentIndex() == getIteratedList().getLastIndex() && getIsShowLast()) {
			elem = getIteratedList().get(getCurrentIndex());
			setIsShowLast(false);
		} else {
			elem = getIteratedList().get(decIndex());
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
			return getIteratedList().size();
		}
		return getNextIndex();
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
		if(getCurrentIndex() == getIteratedList().getLastIndex() && getIsShowLast()){
			return getCurrentIndex();
		}
		return getPrevIndex();
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
		if (!getIsShowFirst() ){
			throw new IllegalStateException();
		}
		getIteratedList().set(getCurrentIndex(),t);
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