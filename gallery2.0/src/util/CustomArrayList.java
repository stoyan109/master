package util;

import java.util.Arrays;

import java.util.Iterator;

public class CustomArrayList<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    private int size;

    public CustomArrayList() {

        this.elements = new Object[DEFAULT_CAPACITY];

        this.size = 0;

    }

    public void add(T element) {

        ensureCapacity();

        elements[size++] = element;

    }

    @SuppressWarnings("unchecked")

    public T get(int index) {

        checkIndex(index);

        return (T) elements[index];

    }

    public void set(int index, T element) {

        checkIndex(index);

        elements[index] = element;

    }

    public void remove(int index) {

        checkIndex(index);

        int numMoved = size - index - 1;

        if (numMoved > 0) {

            System.arraycopy(elements, index + 1, elements, index, numMoved);

        }

        elements[--size] = null;

    }

    public boolean remove(T element) {

        for (int i = 0; i < size; i++) {

            if (element.equals(elements[i])) {

                remove(i);

                return true;

            }

        }

        return false;

    }

    public int size() {

        return size;

    }

    public boolean isEmpty() {

        return size == 0;

    }

    public boolean contains(T element) {

        return indexOf(element) >= 0;

    }

    public int indexOf(T element) {

        for (int i = 0; i < size; i++) {

            if (element.equals(elements[i])) {

                return i;

            }

        }

        return -1;

    }

    public void clear() {

        for (int i = 0; i < size; i++) {

            elements[i] = null;

        }

        size = 0;

    }

    private void ensureCapacity() {

        if (size == elements.length) {

            int newCapacity = elements.length * 2;

            elements = Arrays.copyOf(elements, newCapacity);

        }

    }

    private void checkIndex(int index) {

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        }

    }

    @Override

    public Iterator<T> iterator() {

        return new Iterator<T>() {

            private int currentIndex = 0;

            @Override

            public boolean hasNext() {

                return currentIndex < size;

            }

            @Override

            @SuppressWarnings("unchecked")

            public T next() {

                return (T) elements[currentIndex++];

            }

        };

    }

    @Override

    public String toString() {

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < size; i++) {

            sb.append(elements[i]);

            if (i < size - 1) {

                sb.append(", ");

            }

        }

        sb.append("]");

        return sb.toString();

    }
//fggggggggggggg
}
 