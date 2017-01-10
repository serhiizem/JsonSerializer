package com.serializer;

import com.serializer.exceptions.JsonDocumentIsFinishedException;

import java.io.IOException;
import java.io.Writer;

import static com.serializer.DocumentState.EMPTY_ARRAY;
import static com.serializer.DocumentState.EMPTY_DOCUMENT;
import static com.serializer.DocumentState.EMPTY_OBJECT;
import static com.serializer.DocumentState.NONEMPTY_ARRAY;
import static com.serializer.DocumentState.NONEMPTY_DOCUMENT;
import static com.serializer.DocumentState.NONEMPTY_OBJECT;
import static com.serializer.DocumentState.PROPERTY_NAME;

public class JsonWriter {

    private Writer writer;
    private String indent;
    private String separator = ":";

    private int[] arrayOfStates = new int[32];
    private int arrayOfStatesSize = 0;
    private String deferredName;
    {
        push(EMPTY_DOCUMENT);
    }

    public JsonWriter(Writer writer) {
        this.writer = writer;
    }

    private void push(int state) {
        if(arrayOfStatesSize == arrayOfStates.length) {
            int[] newArray = new int[arrayOfStatesSize * 2];
            System.arraycopy(arrayOfStates, 0, newArray, 0, arrayOfStatesSize);
            arrayOfStates = newArray;
        }
        arrayOfStates[arrayOfStatesSize++] = state;
    }

    public final void setIndent(String indent) {
        if (indent.length() == 0) {
            this.indent = null;
            this.separator = ":";
        } else {
            this.indent = indent;
            this.separator = ": ";
        }
    }

    /**
     * Sets a name for the field to be written. When stack receives a
     * {@link DocumentState#EMPTY_OBJECT} on top of it, the next string
     * which is going to be appended to the {@link JsonWriter} object
     * will be a key for the field-value pair. Name of this field is
     * specified by the following method.
     *
     * @param  name property name
     * @return an instance of the writer-wrapper object that performs
     *         writing operations to the underlying writer
     * @throws IllegalStateException if there is an attempt to add a
     *         property name for the object that has never been created
     */
    public JsonWriter name(String name) throws IllegalStateException {
        if (arrayOfStatesSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        deferredName = name;
        return this;
    }

    public JsonWriter beginObject() throws IOException {
        writePropertyName();
        return openEntity(EMPTY_OBJECT, "{");
    }

    public JsonWriter beginArray() throws IOException {
        return openEntity(EMPTY_ARRAY, "[");
    }

    public void endArray() throws IOException {
        close("]");
    }

    public void endObject() throws IOException {
        close("}");
    }

    private void close(String closeBracket) throws IOException {
        arrayOfStatesSize--;
        writer.write(closeBracket);
    }

    private JsonWriter openEntity(int state, String openBracket) throws IOException {
        beforeValue();
        push(state);
        writer.write(openBracket);
        return this;
    }

    /**
     * As soon as there are different actions to be made depending on the
     * current state of the json document, this method defines transitions between
     * those possible states.
     *
     * @throws IOException if any underlying writing operation fails
     */
    private void beforeValue() throws IOException {
        switch (peek()) {
            case EMPTY_DOCUMENT: {
                putCurrentState(NONEMPTY_DOCUMENT);
                break;
            }
            case PROPERTY_NAME: {
                writer.append(separator);
                putCurrentState(NONEMPTY_OBJECT);
                break;
            }
            case EMPTY_OBJECT: {
                putCurrentState(PROPERTY_NAME);
                break;
            }
            case NONEMPTY_OBJECT: {
                putCurrentState(PROPERTY_NAME);
                break;
            }
            case EMPTY_ARRAY: {
                putCurrentState(NONEMPTY_ARRAY);
                break;
            }
            case NONEMPTY_ARRAY: {
                writer.append(',');
                break;
            }
        }
    }

    private void writeString(String valueToWrite) throws IOException {
        writer.write("\"" + valueToWrite + "\"");
    }

    /**
     * If json object has not been ended and there is another key/value
     * pair to be added to this object, then this subsequent pair should
     * be prepended with comma
     *
     * @throws IOException if an underlying writing operation fails
     */
    private void beforeName() throws IOException {
        int documentState = peek();
        if(documentState == NONEMPTY_OBJECT) {
            writer.write(',');
        }
        putCurrentState(PROPERTY_NAME);
    }

    private void putCurrentState(int state) {
        arrayOfStates[arrayOfStatesSize - 1] = state;
    }

    private int peek() {
        if(arrayOfStatesSize == 0) {
            throw new JsonDocumentIsFinishedException();
        }
        return arrayOfStates[arrayOfStatesSize - 1];
    }

    /**
     * There is a necessity for a separate method for writing a property name,
     * because if it is done via a regular write method, recursion will occur.
     *
     * @throws IOException if an underlying writer has failed to write a
     *         property name string
     */
    private void writePropertyName() throws IOException {
        if (deferredName != null) {
            beforeName();
            writeString(deferredName);
            deferredName = null;
        }
    }

    public void write(Boolean value) throws IOException {
        writePropertyName();
        beforeValue();
        this.writer.write(value ? "true" : "false");
    }

    public void write(Integer value) throws IOException {
        writePropertyName();
        beforeValue();
        this.writer.write(Integer.toString(value));
    }

    public void write(Double value) throws IOException {
        writePropertyName();
        beforeValue();
        this.writer.write(Double.toString(value));
    }

    public void write(Long value) throws IOException {
        writePropertyName();
        beforeValue();
        this.writer.write(Long.toString(value));
    }

    public void write(String value) {
        try {
            writePropertyName();
            beforeValue();
            this.writer.write("\"" + value + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}
