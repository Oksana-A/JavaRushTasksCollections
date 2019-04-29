package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int size = 0;
        Collection<List<V>> listOfValues = map.values();
        if (!listOfValues.isEmpty()) {
            for (List<V> x : listOfValues) {
                size += x.size();
            }
        }
        return size;
    }

    @Override
    public V put(K key, V value) {
        V v = null;
        if (containsKey(key)) {
            List<V> list = map.get(key);
            if (list.size() < repeatCount) {
                v = list.get(list.size()-1);
                list.add(value);
            } else {
                if (list.size() == repeatCount) {
                    v = list.get(list.size() - 1);
                    list.remove(0);
                    list.add(value);
                }
            }
        }
        if (!containsKey(key)){
            ArrayList<V> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        }
        return v;
    }

    @Override
    public V remove(Object key) {
        V v = null;
        if (containsKey(key)) {
             v = map.get(key).remove(0);
             if (map.get(key).isEmpty())
                 map.remove(key);
        }
        return v;
    }

    @Override
    public Set<K> keySet() {
        //напишите тут ваш код
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        return map.values().stream().flatMap(List :: stream).collect(Collectors.toList());
    }

    @Override
    public boolean containsKey(Object key) {
        //напишите тут ваш код
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return ( (ArrayList<V>) values()).contains(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}