package ru.forprogr.hw.hw02diylist;
//-----------------------------------------------------------------------------
// Author:    Nemti
// Created:   12.04.2019 17:18
// Copyright: (c) Nemti 2019
// Licence:   GPL 3.0
//-----------------------------------------------------------------------------

import java.util.*;
import java.util.stream.Collectors;

public class DIYarrayList<T> implements List<T>{

	private Object[] elementsStorage;

	private final int BEGIN_LENGTH_ARRAY = 10;

	private final int MULTIPLIER_LENGTH_ARRAY = 2;

	private int listSize;

	public int getLastIndex(){
		if (listSize == 0){
			return 0;
		}

		return listSize-1;
	}

	private void expandArray(int p_addedSize){
		int newListSize = listSize+p_addedSize;

		if (elementsStorage.length < newListSize) {
			int newStorageSize = newListSize * MULTIPLIER_LENGTH_ARRAY;
			Object[] newElementsStorage = new Object[newStorageSize];

			System.arraycopy(elementsStorage,0,newElementsStorage,0,elementsStorage.length);

			elementsStorage = newElementsStorage;
		}
	}

	public DIYarrayList(){
		super();
		clear();
	}

	@Override
	public int size() {
		return listSize;
	}

	@Override
	public boolean isEmpty() {
		return listSize == 0;
	}

	@Override
	public boolean contains(Object o) {
		return findElementIndex(o,false) != -1 ;
	}

	@Override
	public Object[] toArray() {
		Object[] retArray = new Object[size()];

		System.arraycopy(elementsStorage,0,retArray,0,size());

		return retArray;
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {

		T1[] retArray;
		if (a.length<size()){
			retArray = Arrays.copyOf(a,size());
		} else {
			retArray = Arrays.copyOf(a,a.length);
		}

		for(int i = 0; i < size(); i++){
			retArray[i] = (T1) get(i);
		}

		return retArray;
	}

	@Override
	public boolean add(T t) {
		expandArray(1);

		elementsStorage[listSize] = t;
		listSize++;

		return true;
	}

	@Override
	public T remove(int index) {
		chkOutOfBounds(index,false);

		T retRemovedObject = get(index);

		if (size() == 1){
			clear();
		} else {

			Object[] newElementsStorage = new Object[size() - 1];

			System.arraycopy(elementsStorage, 0, newElementsStorage, 0, index);
			System.arraycopy(elementsStorage, index+1, newElementsStorage, index,size()-index-1);

			elementsStorage = newElementsStorage;
			listSize = elementsStorage.length;
		}

		return retRemovedObject;
	}

	@Override
	public boolean remove(Object o) {
		int indexRemovedElement = indexOf(o);

		if (indexRemovedElement != -1){
			remove(indexRemovedElement);

			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean retIsRemovedAll = false;
		boolean isElementRemoved;

		for (Object obj : c){
			isElementRemoved = remove(obj);
			if (!retIsRemovedAll && isElementRemoved){
				retIsRemovedAll = isElementRemoved;
			}
		}

		return retIsRemovedAll;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if(c.isEmpty()) {
			return true;
		}

		for (Object containsObject : c){
			if (!contains(containsObject)){
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

		Object[] addedElements = c.toArray();
		expandArray(addedElements.length);

		System.arraycopy(addedElements,0,elementsStorage,getLastIndex()+1,addedElements.length);

		listSize += addedElements.length;

		return true;

	}

	@Override
	public void add(int index, T element) {
		chkOutOfBounds(index,true);

		expandArray(1);

		System.arraycopy(elementsStorage, index, elementsStorage, index + 1, size()-index);

		elementsStorage[index] = element;
		listSize++;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		chkOutOfBounds(index,true);

		if (c.isEmpty()) {
			return false;
		}

		Object[] addedElements = c.toArray();
		expandArray(addedElements.length);

		System.arraycopy(elementsStorage, index, elementsStorage, index + addedElements.length, size()-index);

		System.arraycopy(addedElements, 0, elementsStorage, index, addedElements.length);

		listSize += addedElements.length;

		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if(c.isEmpty()) {
			clear();
			return true;
		}

		DIYarrayList<T> retainedElements = new DIYarrayList<>();

		for (Object element : elementsStorage){
			if(c.contains(element)){
				retainedElements.add((T) element);
			}
		}

		elementsStorage = retainedElements.toArray();
		listSize = retainedElements.size();

		return !retainedElements.isEmpty();

	}

	@Override
	public void clear() {
		listSize = 0;
		elementsStorage = new Object[BEGIN_LENGTH_ARRAY];
	}

	private void chkOutOfBounds(int p_index,boolean p_canEqualSize){
		if (p_index < 0
				|| (p_index > getLastIndex() && !p_canEqualSize)
				|| (p_index > size() && p_canEqualSize)
		){
			throw new IndexOutOfBoundsException(p_index);
		}
	}

	private void chkOutOfBounds(int p_indexFrom, int p_indexTo,boolean p_canEqualSize){
		chkOutOfBounds(p_indexFrom,p_canEqualSize);
		chkOutOfBounds(p_indexTo,p_canEqualSize);

		if (p_indexFrom > p_indexTo){
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public T get(int index) {
		chkOutOfBounds(index,false);

		return (T) elementsStorage[index];
	}

	@Override
	public T set(int index, T element) {
		chkOutOfBounds(index,false);

		T oldElement = get(index);

		elementsStorage[index] = element;

		return oldElement;
	}

	private int findElementIndex(Object p_findElement, boolean p_isFindLast){
		if (!isEmpty() && p_findElement != null) {
			int indexElement = p_isFindLast ? getLastIndex() : 0;
			boolean isExit = false;

			while(!isExit){
				if (p_findElement.equals(get(indexElement))){
					return indexElement;
				}

				if (p_isFindLast){
					indexElement--;
				} else {
					indexElement++;
				}

				isExit = (p_isFindLast
								&& indexElement <= 0)
						|| (!p_isFindLast
								&& indexElement > getLastIndex());

			}

		}
		return -1;
	}

	@Override
	public int indexOf(Object o) {
		return findElementIndex(o,false);
	}

	@Override
	public int lastIndexOf(Object o) {
		return findElementIndex(o,true);
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
		chkOutOfBounds(fromIndex,toIndex,true);

		DIYarrayList<T> retSubList = new DIYarrayList<>();

		if(fromIndex == toIndex){
			return retSubList;
		}

		for (int indexElement = fromIndex; indexElement < toIndex; indexElement++) {
			retSubList.add(get(indexElement));
		}

		return retSubList;
	}


	@Override
	public String toString(){
		return "[" + Arrays.stream(elementsStorage)
				.limit(size())
				.map(Object::toString)
				.collect(Collectors.joining(", ")) + "]";

	}
}
