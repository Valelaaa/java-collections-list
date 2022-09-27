package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

class StudentListTest {
    private static final int DEFAULT_SIZE = 3;
    private StudentList students;
    private StudentList emptyList;
    private Student[] studentsArray;
    private List<Student> helper;
    private ListIterator<Student> listIterator;
    private Iterator<Student> iterator;
    private static final Student STUDENT0 = new Student("Valeriy", LocalDate.of(2002, 8, 27), "no details");
    private static final Student STUDENT1 = new Student("Vlad", LocalDate.of(2002, 5, 2), "");
    private static final Student STUDENT2 = new Student("Nikita", LocalDate.of(2002, 1, 14), "Bro");
    private static final Student STUDENT3 = new Student("Karina", LocalDate.of(2001, 6, 26), "Lovesick girl");
    private static final Student STUDENT4 = new Student("Malinka", LocalDate.of(2004, 9, 20), "Karina's alter ego");
    private static final String EXCEPTION = "Exception should be thrown";
    private static final String SIZES_NOT_EQUALS = "Expected size and ActualSize not equals";
    private static final String ALL_IN = "List should contain all added elements";
    private static final String WRONG_OBJECT = "iterator doesn't return right object";

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
    }

    @Test
    void shouldBeEmptyWhenCreateStudentList() {
        assertTrue(emptyList.isEmpty(),
                "assert False if list isn't empty");
    }

    @Test
    void shouldGetFalseWhenListIsNotEmpty() {
        assertAll(() -> assertTrue(emptyList.add(STUDENT0),
                        "assert False if element doesn't added in list"),
                () -> assertFalse(emptyList.isEmpty(),
                        "assert True if list is empty"));
    }

    @Test
    void sizeShouldIncreaseWhenAddedElement() {
        students.add(STUDENT0);
        assertEquals(DEFAULT_SIZE + 1, students.size(),
                SIZES_NOT_EQUALS);
    }


    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, -1, 3, MAX_VALUE})
    void shouldThrowExceptionWhenRemoveGetWrongIndex(final int index) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.remove(index),
                EXCEPTION);
    }

    @Test
    void shouldRemoveOneElementFromList() {
        students.remove(STUDENT0);

        assertAll(
                () -> assertEquals(DEFAULT_SIZE - 1, students.size(),
                        "After remove, size of list should decrease, but he DON'T"),
                () -> assertFalse(students.contains(STUDENT0),
                        "List contains element, after remove it")
        );
    }

    @Test
    void shouldNotRemoveUnexistingStudent() {
        assertFalse(emptyList.remove(STUDENT0),
                "If element didn't contained in list, remove should return false");
    }

    @Test
    void shouldReturnTrueWhenListContainElement() {
        assertAll(
                () -> assertFalse(students.contains(STUDENT4),
                        "List contains element, but shouldn't"),
                () -> assertTrue(students.contains(STUDENT0),
                        "List doesn't contain element")
        );
    }

    /**
     * Method for run Parameterized Tests with 2 parameters (Student,int)
     *
     * @return Stream of Arguments where arguments are StudentAndInteger objects
     */
    private static Stream<Arguments> studentInteger() {
        return Stream.of(Arguments.of(STUDENT0, 0),
                Arguments.of(STUDENT1, 1),
                Arguments.of(STUDENT2, 2),
                Arguments.of(STUDENT4, -1));
    }

    @ParameterizedTest
    @MethodSource("studentInteger")
    void shouldReturnIndexOfElement(final Student student, final int number) {
        assertEquals(number, students.indexOf(student),
                "IndexOf returns another index");
    }

    @ParameterizedTest
    @MethodSource("studentInteger")
    void shouldReturnElementByIndex(final Student student, final int number) {
        if (number > 0)
            assertEquals(student, students.get(number),
                    "Get returns another element");
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, -1, 3, MAX_VALUE})
    void shouldThrowExceptionWhenGetWrongParameter(final int number) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.get(number),
                EXCEPTION);
    }

    @Test
    void removeShouldShiftElementsInList() {
        students.remove(1);
        assertEquals(STUDENT2, students.get(1),
                "Remove doesn't shift elements");
    }

    @Test
    void iteratorShouldSucceed() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        final String message = "next doesn't return right element";
        iterator = studentList.iterator();

        assertTrue(iterator.hasNext(),
                "List doesn't have next element");
        assertEquals(students.get(0), iterator.next(), message);
        assertEquals(students.get(1), iterator.next(), message);
        assertEquals(students.get(2), iterator.next(), message);

    }

    @Test
    void nextShouldThrowExceptionWhenNoNextElement() {
        while (iterator.hasNext())
            iterator.next();
        assertThrows(NoSuchElementException.class, () -> iterator.next(),
                EXCEPTION);
    }

    @Test
    void sizeShouldBeEqualToActualSize() {
        assertEquals(DEFAULT_SIZE, students.size(),
                SIZES_NOT_EQUALS);
    }

    @Test
    void toArrayShouldReturnArrayThatContainsAllTheElements() {
        studentsArray = new Student[DEFAULT_SIZE];

        studentsArray[0] = STUDENT0;
        studentsArray[1] = STUDENT1;
        studentsArray[2] = STUDENT2;

        assertArrayEquals(studentsArray, students.toArray(),
                "ToArray returns wrong array");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenToArrayGetsNull() {
        final Object[] objects = null;
        assertThrows(NullPointerException.class, () -> students.toArray(objects),
                EXCEPTION);
    }

    @Test
    void shouldThrowClassCastExceptionWhenToArrayCanNotCast() {
        assertThrows(ClassCastException.class, () -> students.toArray(new Integer[DEFAULT_SIZE]), EXCEPTION);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void toArrayShouldMakeNewArrayWithListSize(final int number) {
        studentsArray = new Student[number];
        assertEquals(students.size(), students.toArray(studentsArray).length,
                "StudentList size doesn't equals to array length");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 8, 10})
    void lastElementsOfArrayShouldBeNull(final int number) {
        studentsArray = new Student[number];
        assertNull(students.toArray(studentsArray)[number - 1], "Element must be null");
    }

    @Test
    void shouldClearList() {
        students.clear();
        assertTrue(students.isEmpty(), "StudentList doesn't empty after clear");
    }

    private static Stream<Arguments> Students() {
        return Stream.of(Arguments.of((Student) null),
                Arguments.of(STUDENT0),
                Arguments.of(STUDENT1),
                Arguments.of(STUDENT2),
                Arguments.of(STUDENT3),
                Arguments.of(STUDENT4));
    }

    @ParameterizedTest
    @MethodSource("Students")
    void setShouldSucceed(final Student student) {
        students.set(1, student);
        assertEquals(student, students.get(1), "Set should override element on index position");
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, -1, MAX_VALUE})
    void shouldThrowExceptionWhenSetAddRemoveGetWrongIndex(final int number) {
        assertAll(
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.set(number, STUDENT3), EXCEPTION),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.add(number, STUDENT3), EXCEPTION),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.remove(number), EXCEPTION)
        );
    }

    @ParameterizedTest
    @MethodSource("Students")
    void shouldAddElementOnDefiniteIndex(final Student student) {
        final Student temp = students.get(1);

        students.add(1, student);
        assertAll(
                () -> assertEquals(student, students.get(1), "Element should set at [index] place "),
                () -> assertEquals(temp, students.get(2), "Add should shift element in right")
        );
    }

    @Test
    void shouldGetLastIndexOfElement() {
        emptyList.add(STUDENT1);
        emptyList.add(STUDENT2);
        emptyList.add(STUDENT1);
        emptyList.add(STUDENT3);
        assertAll(
                () -> assertEquals(1, emptyList.lastIndexOf(STUDENT2), "LastIndexOf returns wrong index"),
                () -> assertEquals(2, emptyList.lastIndexOf(STUDENT1), "LastIndexOf returns wrong index"),
                () -> assertNotEquals(0, emptyList.lastIndexOf(STUDENT1), "LastIndexOf should return last Index, but returns first")
        );
    }

    @Test
    void subListShouldSucceed() {
        students.add(STUDENT3);
        assertEquals(helper, students.subList(students.indexOf(STUDENT1),
                students.indexOf(STUDENT3)), "Sublist return wrong list");
    }

    @Test
    void subListShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> students.subList(-1, MAX_VALUE), EXCEPTION);
    }

    @Test
    void subListShouldThrowIllegalArgumentExceptionWhenWrongOrder() {
        assertThrows(IllegalArgumentException.class, () -> students.subList(3, 1), EXCEPTION);
    }

    @Test
    void addAllShouldSucceed() {
        emptyList.addAll(students);
        for (Student student : students)
            assertTrue(emptyList.contains(student), ALL_IN);
    }

    @Test
    void shouldThrowExceptionWhenAddAllGetsNull() {
        assertThrows(NullPointerException.class, () -> emptyList.addAll(null), EXCEPTION);
    }

    @Test
    void containsAllShouldSucceed() {
        assertTrue(students.containsAll(helper), "List Doesn't contain all elements of collection");
        for (Student student : helper)
            assertTrue(students.contains(student), ALL_IN);
    }

    @Test
    void shouldThrowExceptionWhenContainsAllGetsNull() {
        assertThrows(NullPointerException.class, () -> students.containsAll(null), EXCEPTION);
    }

    @Test
    void removeAllShouldSucceed() {
        assertTrue(students.removeAll(helper), "removeAll doesn't remove all elements");
        for (Student student : helper)
            assertFalse(students.contains(student), ALL_IN);
    }

    @Test
    void shouldThrowExceptionWhenRemoveAllGetsNull() {
        assertThrows(NullPointerException.class, () -> students.removeAll(null), EXCEPTION);
    }

    @Test
    void shouldThrowExceptionWhenRetainAllGetsNull() {
        assertThrows(NullPointerException.class, () -> students.retainAll(null), EXCEPTION);
    }

    @Test
    void retainAllShouldSucceed() {
        assertTrue(students.retainAll(helper), "List doesn't have the same elements as collection");
        assertArrayEquals(helper.toArray(), students.toArray(), "Arrays doesn't match");
    }

    @Test
    void addAllShouldThrowsNullPointerExceptionWhenNull() {
        assertThrows(NullPointerException.class, () -> students.addAll(1, null), EXCEPTION);
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, -1, MAX_VALUE})
    void addAllShouldThrowIndexOutOfBoundsException(final int number) {
        assertThrows(IndexOutOfBoundsException.class, () -> students.addAll(number, helper), EXCEPTION);
    }

    @Test
    void IndexedAddAllShouldSucceed() {
        helper = new ArrayList<>();

        helper.add(STUDENT3);
        helper.add(STUDENT4);

        assertTrue(students.addAll(1, helper));
        for (Student student : helper)
            assertTrue(students.contains(student), ALL_IN);
        assertArrayEquals(students.subList(1, 3).toArray(), helper.toArray());
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_VALUE, MAX_VALUE})
    void listIteratorShouldThrowExceptionWhenGetWrongParameter(final int number) {
        assertThrows(IndexOutOfBoundsException.class, () -> students.listIterator(number), EXCEPTION);
    }

    @Test
    void naveNextShouldReturnTrueWhenIteratorHasNextElement() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        assertTrue(listIterator.hasNext(), "List hasn't next element");
    }

    @Test
    void nextShouldReturnCurrentElement() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);

        listIterator = studentList.listIterator();

        assertAll(
                () -> assertEquals(STUDENT0, listIterator.next(), WRONG_OBJECT),
                () -> assertEquals(STUDENT1, listIterator.next(), WRONG_OBJECT),
                () -> assertEquals(STUDENT2, listIterator.next(), WRONG_OBJECT)
        );
    }

    @Test
    void shouldReturnTrueWhenHavePreviousElement() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        listIterator.next();
        assertTrue(listIterator.hasPrevious(), "List doesn't have previous element");
    }

    @Test
    void shouldReturnFirstElementOfList() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        listIterator.next();
        assertEquals(STUDENT0, listIterator.previous(), WRONG_OBJECT);
    }

    @Test
    void nextShouldThrowsNoSuchElementExceptionAtLastElement() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        while (listIterator.hasNext())
            listIterator.next();
        assertThrows(NoSuchElementException.class, () -> listIterator.next(), EXCEPTION);
    }

    @Test
    void previousShouldThrowsNoSuchElementExceptionAtFirstElement() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        assertThrows(NoSuchElementException.class, () -> listIterator.previous(), EXCEPTION);
    }

    @Test
    void nextIndexShouldSucceed() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();

        assertEquals(0, listIterator.nextIndex(), "next index Return wrong value");
        listIterator.next();
        assertEquals(1, listIterator.nextIndex(), "next index Return wrong value");
    }

    @Test
    void previousIndexShouldSucceed() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        listIterator.next();
        assertEquals(0, listIterator.previousIndex(), "previousIndex returns wrong value");
    }

    @Test
    void removeShouldThrowIllegalStateExceptionWhenNotIterated() {
        assertThrows(IllegalStateException.class, () -> listIterator.remove(), EXCEPTION);
    }

    @Test
    void shouldRemoveIteratedElement() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);
        listIterator = studentList.listIterator();
        final Student temp = listIterator.next();

        listIterator.remove();

        assertFalse(studentList.contains(temp), "remove() didn't remove element from list");
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenAddGetNull() {
        assertThrows(IllegalStateException.class, () -> listIterator.add(null), EXCEPTION);
    }

    @Test
    void shouldOverloadElementInList() {
        final StudentList studentList = new StudentList();
        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);

        listIterator = studentList.listIterator();
        listIterator.next();
        listIterator.next();
        listIterator.set(STUDENT4);
        assertAll(
                () -> assertEquals(STUDENT4, studentList.get(1), "Element must overwrite another element"),
                () -> assertEquals(DEFAULT_SIZE, studentList.size(), "Size of list shouldn't change")
        );
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenSetGetNull() {
        assertThrows(IllegalStateException.class, () -> listIterator.set(null), EXCEPTION);
    }

    @Test
    void shouldIncreaseSizeWhenAddElement() {
        final StudentList studentList = new StudentList();

        studentList.add(STUDENT0);
        studentList.add(STUDENT1);
        studentList.add(STUDENT2);

        listIterator = studentList.listIterator();

        listIterator.next();
        listIterator.next();

        listIterator.add(STUDENT4);
        assertAll(
                () -> assertEquals(STUDENT4, studentList.get(1), "Element not added in list at the right place"),
                () -> assertEquals(DEFAULT_SIZE + 1, studentList.size(), "Size should change")
        );
    }

    @Test
    void shouldThrowExceptionWhenConstructorGetsNegativeParameter() {
        assertThrows(IllegalArgumentException.class, () -> students = new StudentList(-1), EXCEPTION);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenConstructorGetNull() {
        assertThrows(NullPointerException.class, () -> students = new StudentList(null), EXCEPTION);
    }

    @Test
    void shouldContainsCollectionWhenCreated() {
        final StudentList students = new StudentList(helper);
        assertTrue(students.containsAll(helper), ALL_IN);
    }

}