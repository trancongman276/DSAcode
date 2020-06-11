/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacknqueue;

import java.util.List;
import list.DLinkedList;

/**
 *
 * @author LTSACH
 */
public class Stack<T>{
    private List<T> list; 
    public Stack(){
        /*
        Your are required to use: DLinkedList, SLinkedList or MyArrayList
        At begning, you can use java.util.LinkedList for test, but have to change to the above classes
        */
        this.list = new DLinkedList<>();
    }
    public void push(T item){
        /*YOUR CODE HERE*/
    	if(item == null)
    		throw new NullPointerException();
    	list.add(item);
    }
    public T pop(){
        /*YOUR CODE HERE*/
    	T item = list.get(list.size()-1);
    	list.remove(list.size()-1);
    	return item;
//        return null; //should remove this line
    }
    public T peek(){
        /*YOUR CODE HERE*/
    	return list.get(list.size()-1);
//        return null; //should remove this line
    }
    public boolean remove(T item){
        /*YOUR CODE HERE*/
    	if(item == null)
    		throw new NullPointerException();
    	list.remove(item);
    	return true;
//        return false; //should remove this line
    }
    public boolean contains(T item){
        /*YOUR CODE HERE*/
    	if(item == null)
    		throw new NullPointerException();
    	return list.contains(item);
//        return false; //should remove this line
    }
    public boolean empty(){
        return this.list.isEmpty();
    }
    public int size(){
        return this.list.size();
    }
}