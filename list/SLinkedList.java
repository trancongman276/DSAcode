/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author LTSACH
 */
public class SLinkedList<E> implements java.util.List<E>{
    private static enum MoveType{
            NEXT, PREV
    };
    private Node<E> head, tail;
    private int size;
    
    /*
    Initialize the double-linked list as shown in Figure 20 in the tutorial.
    The following values should be initialized:
    * head, head.next
    * tail, tail.next
    * size
    */
    public SLinkedList(){
       head = new Node<E>(null, null);
       tail = new Node<E>(head, null);
       head.next = tail; size = 0;
    }
    
    //////////////////////////////////////////////////////////////////////////
    /////////////////// Utility methods (private)         ////////////////////
    ////////////////////////////////////////////////////////////////////////// 
    private void checkValidIndex(int index){
        if((index < 0) || (index >= size) ){
            String message = String.format("Invalid index (=%d)", index);
            throw new IndexOutOfBoundsException(message);
        }
    }
    private Node<E> getDataNode(int index) {
        checkValidIndex(index);
        
        Node<E> curNode = head.next;
        int runIndex = 0;
        while(curNode != tail){
            if(index == runIndex) break;
            runIndex += 1;
            curNode = curNode.next;
        }
        return curNode;
    }
    //getNode: can return head
    private Node<E> getNode(int index) {
        if((index < -1) || (index >= size) ){
            String message = String.format("Invalid index (including head) (=%d)", index);
            throw new IndexOutOfBoundsException(message);
        }
        
        Node<E> curNode = head;
        int runIndex = -1;
        while(curNode != tail){
            if(index == runIndex) break;
            runIndex += 1;
            curNode = curNode.next;
        }
        return curNode;
    }
    private void addAfter(Node<E> afterThis, Node<E> newNode){
        newNode.next = afterThis.next;
        afterThis.next = newNode;
        if(newNode.next == tail) tail.next = newNode;
        size += 1;
    }

    
    private Node<E> removeAfter(Node<E> afterThis){
        Node<E> removedNode = afterThis.next;
        afterThis.next = removedNode.next;
        if(removedNode.next == tail) tail.next = afterThis;
        removedNode.next = null;
        size -= 1;
        return removedNode;
    }
    
    //////////////////////////////////////////////////////////////////////////
    /////////////////// API of Single-Linked List         ////////////////////
    ////////////////////////////////////////////////////////////////////////// 
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        if(o==null)
        	throw new NullPointerException();
        Node<E> curNode = head.next;
        while(curNode!=tail) {
        	if(curNode.element.equals(o))
        		return true;
        	curNode = curNode.next;
        }
        return false; 
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(E e) {
        /*YOUR CODE HERE*/
        //addAfter(lastNode, newNode); //can use this line
    	if (e == null)
    		throw new NullPointerException();
    	addAfter(tail.next, new Node<E>(tail, e));
        return true;
    }

    @Override
    public boolean remove(Object o) {
        /*YOUR CODE HERE*/
    	if(o==null)
        	throw new NullPointerException();
        Node<E> curNode = head;
        while(curNode.next!=tail) {
        	if(curNode.next.element.equals(o)) {
        		removeAfter(curNode);
        		return true;
        	}
        	curNode = curNode.next;
        }
        return false; //should remove this line
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        /*YOUR CODE HERE*/
        Node<E> curNode = head.next;
        Node<E> temp;
        while(curNode!=tail) {
        	temp = curNode;
        	curNode = curNode.next;
        	temp.next = null;
        	temp.element = null;
        }
        //to empty condition
        head.next = tail; tail.next = head;       
        size = 0;
    }
    

    @Override
    public E get(int index) {
        /*YOUR CODE HERE*/
    	return getDataNode(index).element;
    }
    
    @Override
    public E set(int index, E element) {
       /*YOUR CODE HERE*/
    	Node<E> tempNode = getDataNode(index);
    	E tempE = tempNode.element;
    	tempNode.element = element;
        return tempE; //should remove this line
    }

    @Override
    public void add(int index, E element) {
        /*YOUR CODE HERE*/
        //addAfter(prevNode, newNode); //can use this line -> uncomment
    	if(element == null)
    		throw new NullPointerException();
    	if(size == 0 && index == 0) {
    		add(element);
    		return;
    	}
    	checkValidIndex(index);
    	Node<E> curNode = head;
    	int i=0;
    	while(curNode.next != tail) {
    		if(i++ == index-1) {
    			addAfter(curNode.next, new Node<E>(null, element));
    			return;
    		}
    		curNode = curNode.next;
    	}
    }

    @Override
    public E remove(int index) {
        /*YOUR CODE HERE*/
        //removeAfter(prevNode); //can use this line-> uncomment
        //return curNode.element; //can use this line-> uncomment
    	Node<E> tempNode = getDataNode(index-1);
    	E tempE = tempNode.next.element;
    	removeAfter(tempNode);
        return tempE; //should remove this line
    }

    @Override
    public int indexOf(Object o) {
        Node<E> curNode = head.next;
        int foundIdx = -1;
        int index = 0;
        while(curNode != tail){
            if(curNode.element.equals(o)){
                foundIdx = index;
                break;
            }
            index += 1;
            curNode = curNode.next;
        }
        return foundIdx;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> curNode = head.next;
        int foundIdx = -1;
        int index = 0;
        while(curNode != tail){
            if(curNode.element.equals(o)){
                foundIdx = index; 
                //continue to find, instead of break here
            }
            index += 1;
            curNode = curNode.next;
        }
        return foundIdx;
    }

    @Override
    public ListIterator<E> listIterator() {
        /*YOUR CODE HERE*/
        return new MyListIterator(); //should remove this line
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        /*YOUR CODE HERE*/
        return new MyListIterator(index); //should remove this line
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        /*IMPLEMENTATION: NOT REQUIRED*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String toString(){
        String desc = "[";
        Iterator<E> it = this.iterator();
        while(it.hasNext()){
            E e = it.next();
            desc += String.format("%s,", e);
        }
        if(desc.endsWith(",")) desc = desc.substring(0, desc.length() - 1);
        desc += "]";
        return desc;
    }
    
    //////////////////////////////////////////////////////////////////////////
    /////////////////// Inner classes                     ////////////////////
    //////////////////////////////////////////////////////////////////////////    
    @SuppressWarnings("hiding")
	private class Node<E>{
        E element;
        Node<E> next;
        Node(Node<E> next, E element){
            this.next = next;
            this.element = element;
        }
        void update(Node<E> next, E element){
            this.next = next;
            this.element = element;
        }
    }//End of Node
    
    public class MyIterator implements Iterator<E>{
        Node<E> pprevNode; //use for remove(): prev of prev
        Node<E> prevNode; //pointer to the prev of the current node
        Node<E> curNode; //pointer to the currrent node during the interating
        boolean afterMove; //after next() or previous()
        MoveType moveType; //next or previous
        
        MyIterator(){
            pprevNode = null;
            prevNode = SLinkedList.this.head;
            curNode = SLinkedList.this.head.next;
            moveType = MoveType.NEXT; //default is move is next()
            afterMove = false;
        }
        @Override
        public boolean hasNext() {
            return curNode != SLinkedList.this.tail;
        }

        @Override
        //next() = return the data in the current node and move to next node
        public E next() {
            E element = curNode.element;
            pprevNode = prevNode;
            prevNode = curNode;
            curNode = curNode.next;
            afterMove = true; //allow remove()
            moveType = MoveType.NEXT; 
            return element;
        }
        @Override
        public void remove() {
            if(!afterMove) return;
            removeAfter(pprevNode);
            prevNode = pprevNode;
            afterMove = false; //one remove() call for each call next()
        }
    }//End of MyIterator
    
    public class MyListIterator extends MyIterator implements ListIterator<E>{
        int index;
        public MyListIterator(){
            super();
            index = 0;
        }
        public MyListIterator(int index){            
            super();
            this.index = index; //largest index = size
        }
        @Override
        public E next() {
            index++;
            return super.next();
        }
        
        @Override
        public void remove() {
            if(!afterMove) return;
            if(moveType == MoveType.NEXT){
                super.remove();
            }
            else{
                Node<E> prevNode = getNode(index-1);
                removeAfter(prevNode);
                //no need to change index
            }
            afterMove = false;
        }
        @Override
        public boolean hasPrevious() {
            return index != 0;
        }

        @Override
        //previous(): move curNode to previous, and the return data in new curNode
        public E previous() {
            index -= 1; //move to previous
            moveType = MoveType.PREV; 
            afterMove = true;
            curNode = SLinkedList.this.getDataNode(index);
            return curNode.element;
        }

        @Override
        public int nextIndex() {
            return this.index;
        }

        @Override
        public int previousIndex() {
            return (index -1);
        }

        @Override
        public void set(E e) {
            if(!afterMove) return;
            if(moveType == MoveType.NEXT)
                prevNode.element = e;
            else
                curNode.element = e;
        }

        @Override
        public void add(E e) {
            if(!afterMove) return;
            if(moveType == MoveType.NEXT){
                //add at prevNode => result: newnode->prevNode
                Node<E> newNode = new Node<>(prevNode.next, prevNode.element);
                prevNode.update(newNode, e);
                if(curNode.next == tail) tail.next = newNode; //update tail
            }
            else{
                //add at curNode => result: newnode->curNode
                Node<E> newNode = new Node<>(curNode.next, curNode.element);
                curNode.update(newNode, e);
                if(curNode.next == tail) tail.next = newNode; //update tail
            }
            
            SLinkedList.this.size += 1;
        }
        
    }//End of MyListIterator
    
}//End of SLinkedList
