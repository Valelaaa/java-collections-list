package com.endava.internship.collections;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * StudentList class implements List interface
 * @author Valeriy Cosneanu
 * @version 1.0
 * @since 2022-09-12
 */
public class StudentList implements List<Student> {
    /**
     * capacity - the maximum number of elements
     * that can be contained in the list
     */
    private int capacity;
    /**
     * size - number of elements in the list
     */
    private int size = 0;
    /**
     * students - array that stores elements of the list
     */
    private Student[] students;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public StudentList() {
        capacity = 10;
        students = new Student[capacity];
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     * @param initialCapacity - sets primary capacity to the given integer
     * @throws IllegalArgumentException - if given integer (initialCapacity)
     * is less than 0
     */
    public StudentList(int initialCapacity){
        if (initialCapacity >= 0) {
            capacity = initialCapacity;
            students = new Student[capacity];
        }
        else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * Constructs a list containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     * @param c - the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public StudentList(Collection<? extends Student> c){
        if (Objects.isNull(c))
            throw new NullPointerException("Cannot create StudentList" +
                    " because \"c\" is null");
        final Student[] array = c.toArray(new Student[c.size()]);
        if (array.length != 0) {
            size = array.length;
            capacity = (array.length * 3) / 2 +1 ;
            students = Arrays.copyOf(array, capacity);
        }
        else{
            capacity = 10;
            students = new Student[capacity];
        }
    }
    /**
     * Returns size of elements in the list
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }
    /**
     * If size of array equals 0, return true, else return false
     * @return true if StudentList hasn't elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Check if there are an element (o) in the list (StudentList)
     * @param o element whose presence in this list is to be tested
     * @return true if element is in the list else return false
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size;i++)
            if (students[i].equals(o))
                return true;
        return false;
    }
    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<Student> iterator() {
        //TODO
        return new Iterator<Student>() {
            /**
             * Index of current element of the list
             */
            private int current = -1;

            /**
             * If next element of the list exists, returns true
             * @return true if next index less than size else returns false
             */
            @Override
            public boolean hasNext() {
                return current + 1 < size;
            }

            /**
             * return next element of the list, advances the iterator to the next element
             * @return next element of the list
             */
            @Override
            public Student next() {
                if (current >= size)
                    throw new NoSuchElementException();
                return students[++current];
            }
        };
    }
    /**
     * Returns an array containing all the elements
     * in this list in proper sequence (from first to last element).
     * @return an array containing all the elements in this list in proper sequence
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(students,size);
    }
    /**
     * Returns an array containing all the elements in this list in proper
     *          sequence (from first to last element)
     * @param ts the array into which the elements of this list are to
     *          be stored
     * @return an array containing the elements of the list
     * @throws  NullPointerException if array is null
     */
    @Override
    public <T> T[] toArray(T[] ts) {
        if (Objects.isNull(ts))
            throw new NullPointerException("Cannot invoke \"toArray()\" because ts is null");
        if (!(ts instanceof Student[]))
            throw new ClassCastException("Cannot cast class "+ts.getClass()+" to class "+Student[].class);
        if (ts.length < size)
            return (T[]) Arrays.copyOf(students,size);
        System.arraycopy(students,0,ts,0,size);
        if(ts.length > size)
            for (int i = size;i<ts.length;i++)
                ts[i] = null;
        return ts;
    }
    /**
     * Appends the specified element to the end of this list.
     * @param student element whose presence in this collection is to be ensured
     * @return true (as specified by Collection.add(Student))
     */
    @Override
    public boolean add(Student student) {
        if (size == capacity) {
            capacity = (capacity * 3) / 2 + 1;
            students = Arrays.copyOf(students,capacity);
        }
        students[size++] = student;
        return true;
    }
    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     * If the list does not contain the element, it is unchanged.
     * @param o element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        if (o != null)
            if (contains(o)) {
                for (int i = 0; i < size; i++) {
                    if (students[i].equals(o)) {
                        System.arraycopy(students,i+1,students,i,size-i);
                    }
                }
                size--;
                return true;
            }
        return false;
    }
    /**
     * Removes all the elements from this list.
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        for (int i = size - 1; size > 0; size--)
            students[i] = null;
    }
    /**
     * Returns the element at the specified position in the list
     * @param i index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 or index >= size)
     */
    @Override
    public Student get(int i) {
        if (i < 0 || i>=size)
            throw new ArrayIndexOutOfBoundsException("Index " + i
                    + "out of bounds for length " + size);
        return students[i];
    }
    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param i index of the element to replace
     * @param student element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 or index >= size)
     */
    @Override
    public Student set(int i, Student student) {
        if (i < 0 || i >= size)
            throw new ArrayIndexOutOfBoundsException("Index" + i +
                    "out of bounds for length " + size);
        Student tmp = students[i];
        students[i] = student;
        return tmp;
    }
    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * @param i index at which the specified element is to be inserted
     * @param student element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 or index >= size)
     */
    @Override
    public void add(int i, Student student) {
        if (i < 0 || i >= size)
            throw new ArrayIndexOutOfBoundsException("Index" + i +
                    "out of bounds for length " + size);
        size++;
        System.arraycopy(students,i,students,i+1,size-1);
        students[i] = student;
    }
    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * @param i the index of the element to be removed
     * @return the element that was removed from the list
     * @throws ArrayIndexOutOfBoundsException if the index is out
     * of range (index < 0 || index >= size())
     */
    @Override
    public Student remove(int i) {
        if (i < 0 || i >= size)
            throw new ArrayIndexOutOfBoundsException("Index" + i +
                    "out of bounds for length " + size);
        Student temp = students[i];
        System.arraycopy(students,i+1,students,i,size-i);
        return temp;
    }
    /**
     * returns (first) index of given element if it exists
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(Object o) {
        if (Objects.isNull(o))
            return -1;
        for (int i = 0;i < size; i++)
            if (o.equals(students[i]))
                return i;
        return -1;
    }
    /**
     *Returns the index of the last occurrence of the specified element in this list,
     *  or -1 if this list does not contain the element.
     * @param o element to search for
     * @return the index of the last occurrence of the specified
     * element in this list, or -1 if this list does not contain the element
     */
    @Override
    public int lastIndexOf(Object o) {
        if (Objects.isNull(o))
            return -1;
        for (int i = size-1;i>=0;i--)
        {
            if (o.equals(students[i]))
                return i;
        }
        return -1;
    }
    /**
     * Returns a list iterator over the elements in this list;
     * @return a list iterator
     */
    @Override
    public ListIterator<Student> listIterator() {
        return listIterator(-1);
    }
    /**
     * returns list iterator over the element of this list,
     *        starting at the specified position in the list
     * @param i index of the first element to be returned from the
     *        list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator starting of the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public ListIterator<Student> listIterator(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Index "+i +" out of range " + size);
        return new ListIterator<Student>() {
            private int current = i;
            private int wasCalled = -1;

            @Override
            public boolean hasNext() {
                return current + 1 < size;
            }

            @Override
            public Student next() {
                if (!hasNext())
                    throw new NoSuchElementException("Iterator has no next element");
                wasCalled = ++current;
                return students[current];
            }

            @Override
            public boolean hasPrevious() {
                return current >= 0;
            }

            @Override
            public Student previous() {
                if (!hasPrevious())
                    throw new NoSuchElementException("Iterator has no previous element");
                wasCalled = --current;
                return students[current];
            }

            @Override
            public int nextIndex() {
                return current + 1;
            }

            @Override
            public int previousIndex() {
                return current;
            }

            @Override
            public void remove() {
                if (wasCalled < 0)
                    throw new IllegalStateException("next or previous elements" +
                            "wasn't called");
                StudentList.this.remove(wasCalled);
                current--;
            }

            @Override
            public void set(Student student) {
                if (wasCalled < 0)
                    throw new IllegalStateException("next or previous elements" +
                            "wasn't called");
                StudentList.this.set(wasCalled,student);
                current++;
            }

            @Override
            public void add(Student student) {
                if (wasCalled < 0)
                    throw new IllegalStateException("next or previous elements" +
                            "wasn't called");
                StudentList.this.add(wasCalled,student);
                current++;
            }
        };
    }
    /**
     * Returns a view of the portion of this list between
     * the specified fromIndex, inclusive, and toIndex, exclusive.
     * @param i low endpoint (inclusive) of the subList
     * @param i1 high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException if index values out of range
     * @throws IllegalArgumentException if indexes are in wrong order
     */
    @Override
    public List<Student> subList(int i, int i1) {
        if (i < 0 || i1>size)
            throw new IndexOutOfBoundsException("Index " + i + " or " + i1 +
                    " out of bounds");
        if (i>i1)
            throw new IllegalArgumentException("Wrong order fromIndex > toIndex: "
                    +i + " > " + i1);
        return Arrays.asList(Arrays.copyOfRange(students, i, i1));
    }
    /**
     * Appends all the elements in the specified collection to the
     * end of this list, in the order that they are returned
     * by the specified collection's Iterator.
     * @param collection collection containing elements to be added to this collection
     * @return true if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        if (Objects.isNull(collection))
            throw new NullPointerException("Cannot invoke " +
                    "\"\" because \"collection\" is null");

        final Student[] array = collection.toArray(new Student[collection.size()]);
        if(array.length + size > capacity){
            capacity = ((array.length + size) * 3) / 2 + 1;
        }
        final Student[] temp = new Student[capacity];
        System.arraycopy(students,0,temp,0,size);
        System.arraycopy(array,0,temp,size,array.length);
        students = temp;
        size = size + array.length;
        return true;
    }
    /**
     * Returns true if this list contains all the elements
     *          of the specified collection
     * @param collection collection to be checked for containment in this list
     * @return true if this list contains all
     *          the elements of the specified collection
     * @throws NullPointerException if collection is null
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        if(Objects.isNull(collection))
            throw new NullPointerException("Cannot invoke containsAll() because" +
                    " \"collection\" is null");
        for (Object obj:collection)
        {
            if (!contains(obj))
                return false;
        }
        return true;
    }
    /**
     * adds all elements of collection in this list
     * @param i index at which to insert the first element from the
     *              specified collection
     * @param collection collection containing elements to be added to this list
     * @return true if all the elements of collection was added to this list
     * @throws IndexOutOfBoundsException if index i is out of range
     * @throws NullPointerException if collection is null
     */
    @Override
    public boolean addAll(int i, Collection<? extends Student> collection) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Cannot invoke addAll because "+ i
                    +" out of range" + size);
        if (Objects.isNull(collection))
            throw new NullPointerException("Cannot invoke addAll because collection is null");
        int startSize = size;
        for (Student student: collection) {
            add(i++, student);
        }
        return startSize != size;
    }
    /**
     * Removes all elements of collection from this list
     * @param collection collection containing elements to be removed from this list
     * @return true if all elements was removed from the list
     * @throws NullPointerException if collection is null
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        if (Objects.isNull(collection))
            throw new NullPointerException("Cannot invoke addAll because collection is null");
        boolean modified = false;
        for(Object obj:collection)
            if (remove(obj))
                modified = true;
        return modified;
    }
    /**
     * Retains only the elements in this list that are contained in the specified collection.
     * @param collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        if (Objects.isNull(collection))
            throw new NullPointerException("Cannot invoke retainAll because collection is null");
        boolean modified = false;
        int index = 0;
        final Student[] std = new Student[capacity];
        for (Object obj: collection) {
            if (contains(obj)) {
                std[index++] = (Student) obj;
                modified = true;
            }
        }
        size = index;
        students = std;
        return modified;
    }
}
