package com.endava.internship.collections;

public class ExceptionMessages {
    public static final String CONSTRUCTOR_NULL_POINTER_EXCEPTION_MESSAGE
            = "Cannot create object %s, because %s is null";
    public static final String CONSTRUCTOR_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
            = "Cannot create object %s, because initial capacity is wrong";
    public static final String NULL_POINTER_EXCEPTION_MESSAGE
            = "Cannot invoke %s, because %s is null";
    public static final String NO_SUCH_ELEMENT_EXCEPTION_MESSAGE
            = "Iterator has no next or previous element";
    public static final String CLASS_CAST_EXCEPTION_MESSAGE
            = "Cannot cast class \"%s\" to class \"%s\"";
    public static final String INDEX_OUT_OF_BOUNDS_EXCEPTION_MESSAGE
            = "Indexes %d and %d are out of range %d";
    public static final String ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION_MESSAGE
            = "Index %d is out of range %d";
    public static final String SUBLIST_ILLEGAL_STATE_EXCEPTION_MESSAGE
            = "Index %d must be less than %d";
    public static final String ITERATOR_ILLEGAL_STATE_EXCEPTION_MESSAGE
            = "next or previous element wasn't called";

    private ExceptionMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
