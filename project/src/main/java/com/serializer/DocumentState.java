package com.serializer;

public class DocumentState {

    /**
     * Nothing has been added to the document
     */
    static final int EMPTY_DOCUMENT = 0;

    /**
     * If there is at least one name/value pair then it
     * should be separeted with coma from the other one
     */
    static final int NONEMPTY_DOCUMENT = 1;

    /**
     * Describes the fact that the last entry to the json
     * document was a property name. This means the next
     * entry should be a value
     */
    static final int PROPERTY_NAME = 2;

    /**
     * If a key/value pair was inserted to the document,
     * then another pair inserted to the document should
     * be indented with comma
     */
    static final int NONEMPTY_OBJECT = 3;

    /**
     * If there are no key/value pairs present in object,
     * then document does not require any separators
     */
    static final int EMPTY_OBJECT = 4;

    /**
     * Array does not require any separators if it is empty
     */
    static final int EMPTY_ARRAY = 5;

    /**
     * There should not be any separators after the key/value
     * pair provided array was ended
     */
    static final int NONEMPTY_ARRAY = 6;
}
