/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

import java.util.Arrays;

/**
 *
 * @author LTSACH
 */
public class XHashMap<K, V> implements IMap<K,V>{
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
    private Entry<K,V>[] table;
    private int size;
    private float loadFactor;
    
    public XHashMap(){
        this(10, 0.75f);
    }
    public XHashMap(int initialCapacity, float loadFactor){
        this.table = new Entry[initialCapacity];
        this.size = 0;
        this.loadFactor = loadFactor;
    }
    //////////////////////////////////////////////////////////////////////////
    /////////////////// Utility methods (private)         ////////////////////
    ////////////////////////////////////////////////////////////////////////// 
    private void ensureLoadFactor(int minCapacity){
        int maxSize = (int)(this.loadFactor*this.table.length);
        if((minCapacity < 0) || (minCapacity > MAX_CAPACITY))
            throw new OutOfMemoryError("Not enough memory to store the array");
        if(minCapacity >= maxSize){
            //grow: oldCapacity >> 1 (= oldCapacity/2)
            int oldCapacity = this.table.length;
            int newCapacity = 2*oldCapacity;
            if(newCapacity < 0) newCapacity = MAX_CAPACITY;
            rehash(newCapacity);
        }        
    }
    private void rehash(int newCapacity){
        Entry<K,V>[] oldTable = this.table;
        this.table = new Entry[newCapacity];
        this.size = 0;
        for(Entry<K, V> entry : oldTable) {
            while(entry != null){
                this.put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    protected int key2index(K key){
    	if(key == null)
    		throw new NullPointerException();
        return key.hashCode()%this.table.length;
    }
    
    //////////////////////////////////////////////////////////////////////////
    /////////////////// API of XHashMap                    ///////////////////
    ////////////////////////////////////////////////////////////////////////// 
    
    @Override
    public V put(K key, V value) {
    	if(value == null)
    		throw new NullPointerException();
        int index = this.key2index(key);
        /*YOUR CODE HERE*/
        if(table[index]!=null) {
        	Entry<K,V> cur = table[index];
        	while(cur!=null) {
        		if(cur.key == key) {
        			V oldV = cur.value;
        			cur.value = value;
        			return oldV;
        		}
        		cur = cur.next;
        	}
        }else {//Create head
        	table[index] = new Entry<>(key, value, null, null);
        	size++;
        	ensureLoadFactor(size);
        	return null;
        }
        
        Entry<K,V> newE = new Entry<>(key, value, null, table[index]);
        table[index].prev = newE;
        table[index] = newE;
        size++;
        ensureLoadFactor(size);
        return null; //should remove this line
    }

    @Override
    public V get(K key) {
        int index = this.key2index(key);
        /*YOUR CODE HERE*/
        if(table[index] != null) {
        	Entry<K,V> cur = table[index];
        	while(cur != null) {
        		if(cur.key == key)
        			return cur.value;
        		cur = cur.next;
        	}
        }
        return null; //should remove this line
    }

    @Override
    public V remove(K key) {
        int index = this.key2index(key);
        Entry<K,V> entry = this.table[index];
        while(entry != null){
            /*YOUR CODE HERE*/
            if(entry.key == key) {
            	size--;
            	if(entry.prev == null) {//head
                	if(entry.next == null) {//only have 1 element in the list
                		entry.next = entry.prev = null;
                		V oldV = entry.value;
                		entry.value = null;
                		return oldV;
                	}
            		entry.next.prev = null;
            		this.table[index] = entry.next;
            		entry.next = entry.prev = null;
            		V oldV = entry.value;
            		entry.value = null;
            		return oldV;
            	}else if(entry.next == null) {//tail
            		entry.prev.next = null;
            		entry.next = entry.prev = null;
            		V oldV = entry.value;
            		entry.value = null;
            		return oldV;
            	}else {
            		entry.next.prev = entry.prev;
            		entry.prev.next = entry.next;
            		entry.next = entry.prev = null;
            		V oldV = entry.value;
            		entry.value = null;
            		return oldV;
            	}
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        int index = this.key2index(key);
        Entry<K,V> entry = this.table[index];
        while(entry != null){
            if(entry.key.equals(key) && entry.value.equals(value)){
                size -= 1;
                /*YOUR CODE HERE*/
                if(entry.prev == null) {//head
                	if(entry.next == null) {
                		entry.next = entry.prev = null;
                		entry.value = null;
                		return true;
                	}
            		entry.next.prev = null;
            		this.table[index] = entry.next;
            		entry.next = entry.prev = null;
            		entry.value = null;
            		return true;
            	}else if(entry.next == null) {//tail
            		entry.prev.next = null;
            		entry.next = entry.prev = null;
            		entry.value = null;
            		return true;
            	}else {
            		entry.next.prev = entry.prev;
            		entry.prev.next = entry.next;
            		entry.next = entry.prev = null;
            		entry.value = null;
            		return true;
            	}
            }
            entry = entry.next;
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        int index = this.key2index(key);
        Entry<K,V> entry = this.table[index];
        while(entry != null){
           /*YOUR CODE HERE*/
        	if(entry.key == key)
        		return true;
            entry = entry.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for(Entry<K, V> entry : this.table) {
            while(entry != null){
                /*YOUR CODE HERE*/
            	if(entry.value == value)
            		return true;
                entry = entry.next;
            }
        }
        return false;
    }

    @Override
    public boolean empty() {
        return this.size == 0;
    }

    @Override
    public int size(){
        return this.size;
    }
    
    public void println(){
        System.out.println(this.toString());
    }
    public String toString(){
        String desc = String.format("%s\n", new String(new char[50]).replace('\0', '-'));
        desc += String.format("Capacity: %d\n", this.capacity());
        desc += String.format("Size: %d\n", this.size());
        for(int idx=0; idx < this.table.length; idx++){
            Entry<K,V> entry = this.table[idx];
            String line = String.format("[%4d]:", idx);
            while(entry != null){
                line += String.format("(%s, %s),", entry.key, entry.value);
                entry = entry.next;
            }
            if(line.endsWith(",")) line = line.substring(0, line.length() - 1);
            desc += line + "\n";
        }
        desc += String.format("%s\n", new String(new char[50]).replace('\0', '-'));
        return desc;
    }
    
    /////////////////////////////////////////////////////////
    ///// The following methods are used for testing only ///
    /////////////////////////////////////////////////////////
    public int capacity(){
        return this.table.length;
    }
    public int numEntryAt(int index){
        Entry<K,V> entry = this.table[index];
        int count = 0;
        while(entry != null){
            count += 1;
            entry = entry.next;
        }
        return count;
    }
}

class Entry<K,V>{
    K key;
    V value;
    Entry<K,V> prev;
    Entry<K,V> next;
    Entry(){
        this(null, null, null, null);
    }
    Entry(K key, V value, Entry<K,V> prev, Entry<K,V> next){
        this.key = key;
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}