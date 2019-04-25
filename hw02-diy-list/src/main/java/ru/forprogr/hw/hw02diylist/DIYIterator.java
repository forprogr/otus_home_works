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
	private DIYarrayList<T> iteratedList;

	private void setIteratedList(DIYarrayList<T> p_iteratedList){
		iteratedList = p_iteratedList;
	}

	protected DIYarrayList<T> getIteratedList(){
		return iteratedList;
	}


	private int currentIndex = -1;

	public DIYIterator(DIYarrayList<T> p_list){
		setIteratedList(p_list);
	}

	protected int getCurrentIndex(){return currentIndex;}

	protected int getNextIndex(){return currentIndex+1;}

	protected int getPrevIndex(){

		if (currentIndex<0){
			return -1;
		}
		return currentIndex-1;
	}

	protected int incIndex(){
		currentIndex++;
		return currentIndex;
	}

	protected int decIndex(){
		currentIndex--;
		if (currentIndex < -1){
			currentIndex = -1;
		}
		return currentIndex;
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
				&& (getNextIndex() <= getIteratedList().getLastIndex()));
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

		T elem = getIteratedList().get(incIndex());

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