package util;

import java.util.ArrayList;

import java.util.List;

public class CustomHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;

    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;

    private int size;

    @SuppressWarnings("unchecked")

    public CustomHashMap() {

        table = new Entry[DEFAULT_CAPACITY];

        size = 0;

    }

    public void put(K key, V value) {

        if (key == null) {

            throw new IllegalArgumentException("Key cannot be null");

        }

        if (size >= table.length * LOAD_FACTOR) {

            resize();
        }

        int index = getIndex(key);

        Entry<K, V> entry = table[index];

        if (entry == null) {

            table[index] = new Entry<>(key, value);

            size++;

        } else {

            while (entry.next != null) {

                if (entry.key.equals(key)) {

                    entry.value = value;

                    return;

                }

                entry = entry.next;

            }

            if (entry.key.equals(key)) {

                entry.value = value;

            } else {

                entry.next = new Entry<>(key, value);

                size++;

            }

        }

    }

    public V get(K key) {

        if (key == null) return null;

        int index = getIndex(key);

        Entry<K, V> entry = table[index];

        while (entry != null) {

            if (entry.key.equals(key)) {

                return entry.value;

            }

            entry = entry.next;

        }

        return null;

    }

    public boolean containsKey(K key) {

        return get(key) != null;

    }

    public V remove(K key) {

        if (key == null) return null;

        int index = getIndex(key);

        Entry<K, V> entry = table[index];

        Entry<K, V> prev = null;

        while (entry != null) {

            if (entry.key.equals(key)) {

                if (prev == null) {

                    table[index] = entry.next;

                } else {

                    prev.next = entry.next;

                }

                size--;

                return entry.value;

            }

            prev = entry;

            entry = entry.next;

        }

        return null;

    }

    public void clear() {

        for (int i = 0; i < table.length; i++) {

            table[i] = null;

        }

        size = 0;

    }

    public int size() {

        return size;

    }

    public List<K> keySet() {

        List<K> keys = new ArrayList<>();

        for (Entry<K, V> entry : table) {

            while (entry != null) {

                keys.add(entry.key);

                entry = entry.next;

            }

        }

        return keys;

    }

    private int getIndex(K key) {

        return Math.abs(key.hashCode()) % table.length;

    }

    @SuppressWarnings("unchecked")

    private void resize() {

        Entry<K, V>[] oldTable = table;

        table = new Entry[table.length * 2];

        size = 0;

        for (Entry<K, V> entry : oldTable) {

            while (entry != null) {

                put(entry.key, entry.value);

                entry = entry.next;

            }

        }

    }
    
    

    private static class Entry<K, V> {
        K key;

        V value;

        Entry<K, V> next;

        Entry(K key, V value) {

            this.key = key;

            this.value = value;

            this.next = null;

        }

    }
//hhhhhh
}
