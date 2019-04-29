package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    private List<Entry<String>> list = new ArrayList<>();

    public CustomTree() {
        this.root = new Entry<>("root");
        list.add(root);
    }

    @Override
    public boolean add(String s) {
        boolean flag = false;
        Entry<String> entry = new Entry<>(s);
        for (Entry<String> e : list) {
            if (e.isAvailableToAddChildren()) {
                if (e.availableToAddLeftChildren) {
                    e.leftChild = entry;
                    e.availableToAddLeftChildren = false;
                }
                else {
                    e.rightChild = entry;
                    e.availableToAddRightChildren = false;
                }
                entry.parent = e;
                flag = list.add(entry);
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean remove(Object object) {
        if (object instanceof String) {
            boolean flag = removeHelper((String) object);
            for (Entry<String> entry : list) {
                if (!list.contains(entry.leftChild))
                    entry.leftChild = null;
                if (!list.contains(entry.rightChild))
                    entry.rightChild = null;
            }
            for (Entry e : list) {
                if (!e.isAvailableToAddChildren()) {
                    if (!e.availableToAddRightChildren && e.rightChild == null)
                        e.availableToAddRightChildren = true;
                    if (!e.availableToAddLeftChildren && e.leftChild == null)
                        e.availableToAddLeftChildren = true;
                }
            }
            return flag;
        }
        else throw new UnsupportedOperationException();
    }

    private boolean removeHelper(String s) {
        boolean flag = false;
        Entry leftEntry = null;
        Entry rightEntry = null;
        Entry ourEntry = null;
        for (Entry<String> entry: list) {
            if (entry.elementName.equals(s)) {
                ourEntry = entry;
                entry.parent = null;
                leftEntry = entry.leftChild;
                rightEntry = entry.rightChild;
            }
        }
        if (ourEntry != null)
            flag = list.remove(ourEntry);
        if (leftEntry != null)
            removeHelper(leftEntry.elementName);
        if (rightEntry != null)
            removeHelper(rightEntry.elementName);
        return flag;
    }

    @Override
    public int size() {
        return list.size() -1;
    }

    public String getParent(String s) {
        String parent = null;
        for (Entry<String> entry : list) {
            if (entry.elementName.equals(s)) {
                parent = entry.parent.elementName;
                break;
            }
        }
        return parent;
    }

    static class Entry<T> implements Serializable{
        String elementName;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;
        Entry<T> parent;
        Entry<T> leftChild;
        Entry<T> rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }


        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "elementName='" + elementName + '\'' +
                    '}';
        }
    }

    @Override
    public String get(int index) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw  new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw  new UnsupportedOperationException();
    }
}
