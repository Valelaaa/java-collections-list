package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

class StudentListTest {
    private int expectedSize;
    private StudentList students;
    private StudentList emptyList;
    private Student[] studentsArray;
    private List<Student> helper;
    private ListIterator<Student> listIterator;
    private Iterator<Student> iterator;
    private static final Student STUDENT0 = new Student("Valeriy", LocalDate.of(2002, 8, 27), "no details");
    private static final Student STUDENT1 = new Student("Ivan", LocalDate.of(2001, 9, 2), "");
    private static final Student STUDENT2 = new Student("Nikita", LocalDate.of(2002, 1, 14), "Bro");
    private static final Student STUDENT3 = new Student("Karina", LocalDate.of(2001, 6, 26), "Lovesick girl");
    private static final Student STUDENT4 = new Student("Malinka", LocalDate.of(2004, 9, 20), "Karina's alter ego");
    private static final Student EMPTY_STUDENT = null;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MINUS_ONE = -1;
    private static final int THREE = 3;

    @BeforeEach
    public void Init() {
        students = new StudentList();
        iterator = students.iterator();
        helper = new ArrayList<>();
        emptyList = new StudentList();

        listIterator = students.listIterator();
        helper.add(STUDENT1);
        helper.add(STUDENT2);
        students.add(STUDENT0);
        students.add(STUDENT1);
        students.add(STUDENT2);
        expectedSize = THREE;
    }

    @Test
    void shouldBeEmptyWhenCreateStudentList() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void shouldNotBeEmptyWhenAddedElement() {
        assertTrue(emptyList.add(STUDENT0));
        assertFalse(emptyList.isEmpty());
    }

    @Test
    void sizeShouldIncreaseWhenAddedElement() {
        final int size = students.size();

        students.add(STUDENT0);

        assertEquals(students.size(), size + 1);
    }


    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, MINUS_ONE,THREE, MAX_VALUE})
    void shouldThrowArrayIndexOutOfBoundExceptionWhenRemove(final int index) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.remove(index));
    }

    @Test
    void listShouldRemoveOneElementFromList() {
        final int size = students.size();

        students.remove(STUDENT0);

        assertEquals(size - 1, students.size());
    }

    @Test
    void shouldNotRemoveUnexistingStudent() {
        assertFalse(emptyList.remove(STUDENT0));
    }

    @Test
    void containsShouldReturnTrueWhenElementInList() {
        students.add(STUDENT0);
        students.add(EMPTY_STUDENT);
        students.remove(EMPTY_STUDENT);

        assertFalse(students.contains(EMPTY_STUDENT));
        assertTrue(students.contains(STUDENT0));
    }

    @Test
    void shouldReturnIndexOfElementInList() {
        students.add(EMPTY_STUDENT);

        assertEquals(ZERO, students.indexOf(STUDENT0));
        assertEquals(THREE, students.indexOf(EMPTY_STUDENT));
        assertEquals(MINUS_ONE, students.indexOf(STUDENT3));

    }

    @Test
    void shouldReturnElementByIndex() {
        assertEquals(STUDENT0, students.get(ZERO));

    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, MINUS_ONE, THREE, MAX_VALUE})
    void shouldThrowArrayIndexOutOfBoundsExceptionWhenGet(final int number) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.get(number));
    }

    @Test
    void removeShouldShiftElementsInList() {
        students.remove(ONE);

        assertEquals(STUDENT2, students.get(ONE));
    }

    @Test
    void hasNextShouldReturnTrueIfNotEndOfList() {
        assertTrue(iterator.hasNext());
    }

    @Test
    void shouldNextShouldReturnCurrentElementOfList() {
        assertEquals(STUDENT0, iterator.next());
    }

    @Test
    void nextShouldThrowNoSuchElementExceptionWhenOutOfArray() {
        while (iterator.hasNext())
            iterator.next();

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void sizeShouldBeEqualToExpectedSize() {
        assertEquals(expectedSize, students.size());
    }

    @Test
    void toArrayShouldReturnArrayThatContainsAllTheElements() {
        studentsArray = new Student[expectedSize];

        studentsArray[0] = STUDENT0;
        studentsArray[1] = STUDENT1;
        studentsArray[2] = STUDENT2;

        assertArrayEquals(studentsArray, students.toArray());
    }

    @ParameterizedTest
    @NullSource
    void toArrayThrowsNullPointerExceptionIfArrayIsNull(final Object[] object) {
        assertThrows(NullPointerException.class, () -> students.toArray(object));
    }

    @Test
    void toArrayThrowsClassCastExceptionWhenCantCast() {
        TestDummy[] testDummies = new TestDummy[expectedSize];
        assertThrows(ClassCastException.class, () -> students.toArray(testDummies));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void toArrayShouldMakeNewArrayWithListSize(final int number) {
        studentsArray = new Student[number];
        assertEquals(students.size(), students.toArray(studentsArray).length);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 8, 10})
    void toArrayLastElementsShouldBeNull(final int number) {
        studentsArray = new Student[number];
        assertNull(students.toArray(studentsArray)[number - 1]);
    }

    @Test
    void shouldClearList() {
        students.clear();
        assertTrue(students.isEmpty());
    }

    @Test
    void setShouldSucceed() {
        students.set(ONE, EMPTY_STUDENT);
        assertEquals(EMPTY_STUDENT, students.get(ONE));
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, MINUS_ONE, MAX_VALUE})
    void setAddRemoveShouldThrowArrayIndexOutOfBoundsException(final int number) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.set(number, STUDENT3));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.add(number, STUDENT3));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.remove(number));
    }

    @Test
    void addWithIndexShouldShiftElements() {
        Student temp = students.get(ONE);

        students.add(ONE, STUDENT3);

        assertEquals(STUDENT3, students.get(ONE));
        assertEquals(temp, students.get(ONE + 1));
    }

    @Test
    void LastIndexOfShouldSucceed() {
        final int ValueLastIndexOf = students.size();
        students.add(STUDENT1);
        assertEquals(ValueLastIndexOf, students.lastIndexOf(STUDENT1));
        students.add(EMPTY_STUDENT);
        assertEquals(ValueLastIndexOf + 1, students.lastIndexOf(EMPTY_STUDENT));
    }

    @Test
    void subListShouldSucceed() {
        students.add(STUDENT3);
        assertEquals(helper, students.subList(students.indexOf(STUDENT1),
                students.indexOf(STUDENT3)));
    }

    @Test
    void subListShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> students.subList(MINUS_ONE, MAX_VALUE));
    }

    @Test
    void subListShouldThrowIllegalArgumentExceptionWhenWrongOrder() {
        assertThrows(IllegalArgumentException.class, () -> students.subList(THREE, ONE));
    }

    @Test
    void addAllShouldSucceed() {
        emptyList.addAll(students);
        for (Student student : students)
            assertTrue(emptyList.contains(student));
    }

    @ParameterizedTest
    @NullSource
    void addAllShouldThrowNullPointerExceptionWhenNull(final Collection<Student> collection) {
        assertThrows(NullPointerException.class, () -> emptyList.addAll(collection));
    }

    @Test
    void containsAllShouldSucceed() {
        assertTrue(students.containsAll(helper));
        for (Student student : helper)
            assertTrue(students.contains(student));
    }

    @ParameterizedTest
    @NullSource
    void containsAllShouldThrowNullPointerExceptionWhenNull(final Collection<Student> collection) {
        assertThrows(NullPointerException.class, () -> students.containsAll(collection));
    }

    @Test
    void removeAllShouldSucceed() {
        assertTrue(students.removeAll(helper));
        for (Student student : helper)
            assertFalse(students.contains(student));
    }

    @ParameterizedTest
    @NullSource
    void removeAllShouldThrowNullPointerExceptionWhenNull(final Collection<Student> collection) {
        assertThrows(NullPointerException.class, () -> students.removeAll(collection));
    }

    @ParameterizedTest
    @NullSource
    void retainAllShouldThrowNullPointerExceptionWhenNull(final Collection<Student> collection) {
        assertThrows(NullPointerException.class, () -> students.retainAll(collection));
    }

    @Test
    void retainAllShouldSucceed() {
        assertTrue(students.retainAll(helper));
        assertArrayEquals(helper.toArray(), students.toArray());
    }

    @ParameterizedTest
    @NullSource
    void addAllShouldThrowsNullPointerExceptionWhenNull(final Collection<Student> collection) {
        assertThrows(NullPointerException.class, () -> students.addAll(ONE, collection));
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, MINUS_ONE, MAX_VALUE})
    void addAllShouldThrowIndexOutOfBoundsException(final int number) {
        assertThrows(IndexOutOfBoundsException.class, () -> students.addAll(number, helper));
    }

    @Test
    void addAllWithIndexShouldSucceed() {
        helper = new ArrayList<>();

        helper.add(STUDENT3);
        helper.add(STUDENT4);

        assertTrue(students.addAll(ONE, helper));
        for (Student student : helper)
            assertTrue(students.contains(student));
        assertArrayEquals(students.subList(ONE, ONE + 2).toArray(), helper.toArray());
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, MAX_VALUE})
    void listIteratorShouldThrowIndexOutOfBoundExceptionWhenWrongParameter(final int number) {
        assertThrows(IndexOutOfBoundsException.class, () -> students.listIterator(number));
    }

    @Test
    void naveNextShouldReturnTrueWhenIteratorHasNextElement() {
        assertTrue(listIterator.hasNext());
    }

    @Test
    void nextShouldReturnCurrentElement() {
        assertEquals(STUDENT0, listIterator.next());
    }

    @Test
    void havePreviousShouldReturnsTrueWhenIteratorHasNextElement() {
        listIterator.next();
        assertTrue(listIterator.hasPrevious());
    }

    @Test
    void previousShouldReturnsCurrentElement() {
        listIterator.next();
        assertEquals(STUDENT0, listIterator.previous());
    }

    @Test
    void nextShouldThrowsNoSuchElementExceptionAtLastElement() {
        while (listIterator.hasNext())
            listIterator.next();
        assertThrows(NoSuchElementException.class, () -> listIterator.next());
    }

    @Test
    void previousShouldThrowsNoSuchElementExceptionAtFirstElement() {
        assertThrows(NoSuchElementException.class, () -> listIterator.previous());
    }

    @Test
    void nextIndexShouldSucceed() {
        assertEquals(ZERO, listIterator.nextIndex());
        listIterator.next();
        assertEquals(ONE, listIterator.nextIndex());
    }

    @Test
    void previousIndexShouldSucceed() {
        listIterator.next();
        assertEquals(ZERO, listIterator.previousIndex());
    }

    @Test
    void removeShouldThrowIllegalStateExceptionWhenNotIterated() {
        assertThrows(IllegalStateException.class, () -> listIterator.remove());
    }

    @Test
    void listIteratorRemoveShouldSucceed() {
        final Student temp = listIterator.next();
        listIterator.remove();
        assertFalse(students.contains(temp));
    }

    @ParameterizedTest
    @NullSource
    void addShouldThrowIllegalStateExceptionWhenNull(final Student student) {
        assertThrows(IllegalStateException.class, () -> listIterator.add(student));
    }

    @Test
    void listIteratorSetShouldChangeElement() {
        final int startSize = students.size();

        listIterator.next();
        listIterator.next();
        listIterator.set(STUDENT4);

        assertEquals(STUDENT4, students.get(ONE));
        assertEquals(startSize, students.size());
    }

    @ParameterizedTest
    @NullSource
    void setShouldThrowIllegalStateExceptionWhenNull(final Student student) {
        assertThrows(IllegalStateException.class, () -> listIterator.set(student));
    }

    @Test
    void SizeShouldIncreaseWhenAddElement() {
        final int startSize = students.size();

        listIterator.next();
        listIterator.next();
        listIterator.add(STUDENT4);

        assertEquals(STUDENT4, students.get(ONE));
        assertEquals(startSize + 1, students.size());
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionWhenNegative() {
        assertThrows(IllegalArgumentException.class, () -> students = new StudentList(MINUS_ONE));
    }

    @ParameterizedTest
    @NullSource
    void constructorShouldThrowNullPointerExceptionWhenNull(final Collection<Student> collection) {
        assertThrows(NullPointerException.class, () -> students = new StudentList(collection));
    }

    @Test
    void createdStudentListShouldContainsCollection() {
        StudentList students = new StudentList(helper);
        assertTrue(students.containsAll(helper));
    }

    static final class TestDummy {
    }
}