package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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


class StudentListTest {
    private int EXPECTED_SIZE = 0;
    private StudentList students;
    private List<Student> helper;
    private ListIterator<Student> listIterator;
    private Iterator<Student> iterator;
    private static final Student STUDENT0 = new Student("Valeriy", LocalDate.of(2002, 8, 27), "no details");
    private static final Student STUDENT1 = new Student("Ivan", LocalDate.of(2001, 9, 2), "");
    private static final Student STUDENT2 = new Student("Nikita", LocalDate.of(2002, 1, 14), "Bro");
    private static final Student STUDENT3 = new Student("Karina", LocalDate.of(2001, 6, 26), "Lovesick girl");
    private static final Student STUDENT4 = new Student("Malinka", LocalDate.of(2004, 9, 20), "Karina's alter ego");
    private static final Student EMPTYSTUDENT = null;

    @BeforeEach
    public void Init() {
        students = new StudentList();
        iterator = students.iterator();
        helper = new ArrayList<>();
        listIterator = students.listIterator();
        helper.add(STUDENT1);
        helper.add(STUDENT2);
        students.add(STUDENT0);
        students.add(STUDENT1);
        students.add(STUDENT2);
        EXPECTED_SIZE = 3;
    }

    @Test
    void whenCreateEmptyStudentList_ShouldBeEmpty() {
        StudentList students1 = new StudentList();
        assertTrue(students1.isEmpty());
    }

    @Test
    void whenAddedOneElementStudentList_IsNotEmpty() {
        StudentList students1 = new StudentList();
        students1.add(STUDENT0);
        assertFalse(students1.isEmpty());
    }

    @Test
    void whenAddedOneElement_ShouldReturnTrue() {
        assertTrue(students.add(STUDENT0));
    }

    @Test
    void whenAddedOneElement_ShouldNotBeEmpty() {
        StudentList students1 = new StudentList();
        students1.add(STUDENT0);
        assertFalse(students1.isEmpty());
    }

    @Test
    void whenAddedOneElementStudentList_SizeIncreases() {
        final int size = students.size();
        students.add(STUDENT0);
        assertEquals(students.size(), size + 1);
    }

    @Test
    void whenIndexLessThanZeroOrMoreThanSize_RemoveShouldThrowArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.remove(-1));
    }

    @Test
    void whenAddedAndRemovedOneElement_ListShouldBeEmpty() {
        StudentList students1 = new StudentList();

        students1.add(STUDENT0);
        students1.remove(STUDENT0);

        assertEquals(0, students1.size());
        assertTrue(students1.isEmpty());
    }

    @Test
    void whenElementDoesNotRemoved_ShouldReturnFalse() {
        StudentList students1 = new StudentList();
        assertFalse(students1.remove(STUDENT0));
    }

    @Test
    void whenElementInList_ContainsShouldReturnTrue() {
        students.add(STUDENT0);
        students.add(EMPTYSTUDENT);
        students.remove(EMPTYSTUDENT);

        assertFalse(students.contains(EMPTYSTUDENT));
        assertTrue(students.contains(STUDENT0));
    }

    @Test
    void whenThereIsNoElementInList_ContainShouldReturnFalse() {
        StudentList students1 = new StudentList();
        assertFalse(students1.contains(STUDENT1));
    }

    @Test
    void whenCallIndexOf_ReturnsIndexOfElementInList() {
        students.add(EMPTYSTUDENT);
        assertEquals(0, students.indexOf(STUDENT0));
        assertEquals(3, students.indexOf(EMPTYSTUDENT));
    }

    @Test
    void indexOfWhenListDoesNotContainsElement() {
        assertEquals(-1, students.indexOf(STUDENT3));
    }

    @Test
    void whenCallGet_ReturnsElementByIndex() {
        assertEquals(STUDENT0, students.get(0));
    }

    @Test
    void whenCallGetToIndexMoreThanSizeOrLessThanZero_ThrowsArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.get(-1));
    }

    @Test
    void whenElementByIndexReturned_NextElementGetPreviousIndex() {
        students.remove(1);
        assertEquals(STUDENT2, students.get(1));
    }

    @Test
    void whenListHaveNextElement_HasNextReturnsTrue() {
        assertTrue(iterator.hasNext());
    }

    @Test
    void whenInvokeIteratorNext_ReturnsNextElementInList() {
        assertEquals(STUDENT0, iterator.next());
    }

    @Test
    void whenInvokeIteratorAfterLastElement_ThrowsNoSuchElementException() {
        while (iterator.hasNext())
            iterator.next();
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void sizeShouldBeEqualToExpectedSize() {
        assertEquals(EXPECTED_SIZE, students.size());
    }

    @Test
    void toArrayShouldReturnArrayThatContainsAllTheElements() {
        Student[] studentsArray = new Student[EXPECTED_SIZE];
        studentsArray[0] = STUDENT0;
        studentsArray[1] = STUDENT1;
        studentsArray[2] = STUDENT2;
        assertArrayEquals(studentsArray, students.toArray());
    }

    @Test
    void toArrayThrowsNullPointerExceptionIfArrayIsNull() {
        assertThrows(NullPointerException.class, () -> students.toArray((Object[]) null));
    }

    @Test
    void toArrayThrowsClassCastExceptionIfCanNotCastClass() {
        TestDummy[] testDummies = new TestDummy[EXPECTED_SIZE];
        assertThrows(ClassCastException.class, () -> students.toArray(testDummies));
    }

    @Test
    void toArrayWhenArrayLengthLessThanSizeOfList_ShouldMakeNewArrayWithSizeOfList() {
        Student[] studentArray = new Student[2];
        assertEquals(students.size(), students.toArray(studentArray).length);
    }

    @Test
    void toArrayWhenArrayLengthMoreThanSizeOfList_LastElementsOfArrayShouldBeNull() {
        final int length = 5;
        Student[] studentArray = new Student[length];
        assertNull(students.toArray(studentArray)[length - 1]);
    }

    @Test
    void whenInvokeCall_ListShouldBeEmpty() {
        students.clear();
        assertTrue(students.isEmpty());
    }

    @Test
    void setShouldSucceed() {
        students.set(1, EMPTYSTUDENT);
        assertEquals(EMPTYSTUDENT, students.get(1));
    }

    @Test
    void whenSetIndexMoreThanSizeOrLessThanZero_ThrowsArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.set(-1, EMPTYSTUDENT));
    }

    @Test
    void whenAddIndexMoreThanSizeOrLessThanZero_ThrowsArrayIndexOutOfBoundException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.add(-2, STUDENT3));
    }

    @Test
    void whenRemoveIndexMoreThanSizeOrLessThanZero_ThrowsArrayIndexOutOfBoundException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> students.add(-2, STUDENT3));
    }

    @Test
    void whenAddWithIndexCalledElementAddsInIndexPreviousElementShiftedToRight() {
        final int addIndex = 1;
        Student temp = students.get(addIndex);

        students.add(addIndex, STUDENT3);

        assertEquals(STUDENT3, students.get(1));
        assertEquals(temp, students.get(addIndex + 1));
    }

    @Test
    void LastIndexOfShouldSucceed() {
        final int ValueLastIndexOf = students.size();
        students.add(STUDENT1);
        assertEquals(ValueLastIndexOf, students.lastIndexOf(STUDENT1));
        students.add(EMPTYSTUDENT);
        assertEquals(ValueLastIndexOf + 1, students.lastIndexOf(EMPTYSTUDENT));
    }

    @Test
    void subListShouldSucceed() {
        students.add(STUDENT3);
        assertEquals(helper, students.subList(students.indexOf(STUDENT1),
                students.indexOf(STUDENT3)));
    }

    @Test
    void whenSubListIndexesAreLessThanZeroOrMoreThanSize_ThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> students.subList(-1, 999));
    }

    @Test
    void whenSubListIndexesAreInWrongOrder_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> students.subList(3, 1));
    }

    @Test
    void addAllShouldSucceed() {
        StudentList list = new StudentList();
        list.addAll(students);
        for (Student student : students)
            assertTrue(list.contains(student));
    }

    @Test
    void addAllShouldThrowsNullPointerException_IfCollectionIsNull() {
        helper = null;
        StudentList students1 = new StudentList();
        assertThrows(NullPointerException.class, () -> students1.addAll(helper));
    }

    @Test
    void containsAllShouldSucceed() {
        assertTrue(students.containsAll(helper));
        for (Student student : helper)
            assertTrue(students.contains(student));
    }

    @Test
    void containsAllShouldThrowsNullPointerException_IfCollectionIsNull() {
        helper = null;
        assertThrows(NullPointerException.class, () -> students.containsAll(helper));
    }

    @Test
    void removeAllShouldSucceed() {
        assertTrue(students.removeAll(helper));
        for (Student student : helper)
            assertFalse(students.contains(student));
    }

    @Test
    void removeAllShouldThrowsNullPointerException_IfCollectionIsNull() {
        helper = null;
        assertThrows(NullPointerException.class, () -> students.removeAll(helper));
    }

    @Test
    void retainAllShouldThrowsNullPointerException_IfCollectionIsNull() {
        helper = null;
        assertThrows(NullPointerException.class, () -> students.retainAll(helper));
    }

    @Test
    void retainAllShouldSucceed() {
        assertTrue(students.retainAll(helper));
        assertArrayEquals(helper.toArray(), students.toArray());
    }

    @Test
    void addAllWithIndexShouldThrowsNullPointerException_IfCollectionIsNull() {
        helper = null;
        assertThrows(NullPointerException.class, () -> students.addAll(1, helper));
    }

    @Test
    void addAllWithIndexShouldThrowsIndexOutOfBoundsException_IfIndexMoreThanSizeOrLessThanZero() {
        final int index = -1;
        assertThrows(IndexOutOfBoundsException.class, () -> students.addAll(index, helper));
    }

    @Test
    void addAllWithIndexShouldSucceed() {
        helper = new ArrayList<>();
        helper.add(STUDENT3);
        helper.add(STUDENT4);
        final int index = 1;
        assertTrue(students.addAll(index, helper));
        for (Student student : helper)
            assertTrue(students.contains(student));
        assertArrayEquals(students.subList(index, index + 2).toArray(), helper.toArray());
    }

    @Test
    void whenListIteratorCreationParameterMoreThanSizeOrLessThanMinusOne_ThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> students.listIterator(-5));
    }

    @Test
    void whenListIteratorHaveNextElement_HasNextReturnsTrue() {
        assertTrue(listIterator.hasNext());
    }

    @Test
    void whenInvokeListIteratorNext_ReturnsNextElementInList() {
        assertEquals(STUDENT0, listIterator.next());
    }

    @Test
    void whenListIteratorHavePreviousElement_HasPreviousReturnsTrue() {
        listIterator.next();
        assertTrue(listIterator.hasPrevious());
    }

    @Test
    void whenInvokeListIteratorPrevious_ReturnsPreviousElementInList() {
        listIterator.next();
        assertEquals(STUDENT0, listIterator.previous());
    }

    @Test
    void whenInvokeListIteratorNextAfterLastElement_ThrowsNoSuchElementException() {
        while (listIterator.hasNext())
            listIterator.next();
        assertThrows(NoSuchElementException.class, () -> listIterator.next());
    }

    @Test
    void whenInvokeListIteratorPreviousAtFirstElement_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> listIterator.previous());
    }

    @Test
    void nextIndexShouldSucceed() {
        assertEquals(0, listIterator.nextIndex());
        listIterator.next();
        assertEquals(1, listIterator.nextIndex());
    }

    @Test
    void previousIndexShouldSucceed() {
        listIterator.next();
        assertEquals(0, listIterator.previousIndex());
    }

    @Test
    void invokeRemoveWhenIteratorDoesNotIterated_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> listIterator.remove());
    }

    @Test
    void listIteratorRemoveShouldSucceed() {
        Student temp = listIterator.next();
        listIterator.remove();
        assertFalse(students.contains(temp));
    }

    @Test
    void invokeAddWhenIteratorDoesNotIterated_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> listIterator.add(null));
    }

    @Test
    void listIteratorSetShouldChangeElement() {
        listIterator.next();
        listIterator.next();
        final int startSize = students.size();
        listIterator.set(STUDENT4);
        assertEquals(STUDENT4, students.get(1));
        assertEquals(startSize, students.size());
    }

    @Test
    void invokeSetWhenIteratorDoesNotIterated_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> listIterator.set(null));
    }

    @Test
    void listIteratorAddShouldAddElement_SizeShouldIncrease() {
        listIterator.next();
        listIterator.next();
        final int startSize = students.size();
        listIterator.add(STUDENT4);
        assertEquals(STUDENT4, students.get(1));
        assertEquals(startSize + 1, students.size());
    }

    @Test
    void createStudentListWithNumberThatLessZero_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> students = new StudentList(-1));
    }

    @Test
    void createStudentListWithNullParameter_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> students = new StudentList(null));
    }

    @Test
    void createdStudentListMustContainsCollection() {
        StudentList students1 = new StudentList(helper);
        assertTrue(students1.containsAll(helper));
    }

    /**
     * class for testing ClassCastException
     */
    static final class TestDummy {
        public TestDummy() {
        }
    }
}