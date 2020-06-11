/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacknqueue;
import list.DLinkedList;
import java.util.List;

/**
 *
 * @author LTSACH
 */
public class Queue<T>{
    private List<T> list; 
    public Queue(){
        /*
        Your are required to use: DLinkedList, SLinkedList or MyArrayList
        At begning, you can use java.util.LinkedList for test, but have to change to the above classes
        */
        this.list = new DLinkedList<>();
    }
    public void push(T item){
        /*YOUR CODE HERE*/
    	list.add(item);
    }
    public T pop(){
        /*YOUR CODE HERE*/
    	T temp = list.get(0);
    	list.remove(0);
        return temp; 
    }
    public T peek(){
        /*YOUR CODE HERE*/
    	return list.get(0);
    }
    public boolean contains(T item){
        /*YOUR CODE HERE*/
    	return list.contains(item);
    }
    public boolean empty(){
        return this.list.isEmpty();
    }
    public int size(){
        return this.list.size();
    }
}