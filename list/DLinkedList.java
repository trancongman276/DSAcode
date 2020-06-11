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
public class DLinkedList<E> implements List<E>{
    private static enum MoveType{
            NEXT, PREV
    };
    private Node<E> head;
    private Node<E> tail;
    private int size;
    /*
    Initialize the double-linked list as shown in Figure 32 in the tutorial.
    The following values should be initialized:
    * head, head.prev, head.next
    * tail, tail.prev, tail.next
    * size
    */
    public DLinkedList(){
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, null, null);
        head.next = tail; tail.prev = head;
        size = 0;
        
    }
    //////////////////////////////////////////////////////////////////////////
    /////////////////// Utility methods (private)         ////////////////////
    ////////////////////////////////////////////////////////////////////////// 
    /*
    checkValidIndex: assert that "index" inside of [min, max]
    */
    private void checkValidIndex(int index, int min, int max){    
        if((index < min) || (index > max)){
            String message = String.format("Invalid index (=%d)", index);
            throw new IndexOutOfBoundsException(message);
        }
    }
   
    /*
    getNode(Object o): get node containing data (not applied for meta-nodes: ie., head and tail)
    * search and return the node containing object "o".
    * return "null" if not found
    */
    private Node<E> getNode(Object o){
        Node<E> curNode = head.next;
        Node<E> foundNode = null;
        while(curNode != tail){
            if(curNode.element.equals(o)){
                foundNode = curNode;
                break;
            }
            curNode = curNode.next;
        }
        return foundNode;
    }
    /*
    getNode(int index, int min, int max): get node containing either data or head/tail.
    
    */
    private Node<E> getNode(int index, int min, int max){
        checkValidIndex(index, min, max);
        
        Node<E> curNode;
        int curIndex;
        if((size - index ) < (size/2)){
            curNode = this.tail;
            curIndex = size;
            while(curIndex > index){
                curIndex -= 1;
                curNode = curNode.prev;
            }
        }
        else{
            curNode = head;
            curIndex = -1;
            while(curIndex < index){
                curIndex += 1;
                curNode = curNode.next;
            }
        }
        return curNode;
    }
    /*
    insertLnewR(Node<E> left, Node<E> newNode, Node<E> right):
    insert newNode to the double-linked list.
    after insertion: left<->newNode <->right
    */
    private void insertLnewR(Node<E> left, Node<E> newNode, Node<E> right){
        /*YOUR CODE HERE*/
    	newNode.prev = left;
    	newNode.next = right;
    	left.next = newNode;
    	right.prev = newNode;
        size += 1;
    }
    /*
    removeNode(Node<E> removedNode):
    remove "removedNode" from the double-linked list.
    */
    private void removeNode(Node<E> removedNode){
        /*YOUR CODE HERE*/
    	if(removedNode == head || removedNode == tail)
    		return;
    	Node<E> left = removedNode.prev;
    	Node<E> right = removedNode.next;
    	left.next = right;
    	right.prev = left;
    	removedNode.element = null;
    	removedNode.next = removedNode.prev = null;
        size -= 1;
    }
    
    //////////////////////////////////////////////////////////////////////////
    /////////////////// API of Doubble-Linked List         ///////////////////
    ////////////////////////////////////////////////////////////////////////// 
    @Override
    public int size() {
        /*YOUR CODE HERE*/
    	return size;
//        return -1; //should remove this line
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
       /*YOUR CODE HERE*/
    	return getNode(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        /*YOUR CODE HERE*/ 
        return new FWDIterator(); //should remove this line
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
    	Node<E> newNode = new Node<E>(tail.prev, tail, e);
    	Node<E> tempNode = tail.prev;
    	tempNode.next = newNode;
    	tail.prev = newNode;
    	size++;
        return true;
    }
    

    @Override
    public boolean remove(Object o) {
    	if(o.equals(null))
    		return false;
        Node<E> temp = getNode(o);
        if(temp == null)
        	return false;
        removeNode(temp);
    	return true;
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
    	while(curNode != tail) {
    		temp = curNode;
    		curNode = curNode.next;
    		temp.element = null;
    		temp.next = temp.prev = null;
    	}
        
        //reset head and tail
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    
    

    @Override
    public E get(int index) {
        /*YOUR CODE HERE*/
    	return getNode(index, 0, size).element;
    }

    @Override
    public E set(int index, E element) {
        /*YOUR CODE HERE*/
    	Node<E> tempNode = getNode(index, 0, size);
    	if(tempNode == null)
    		throw new NullPointerException();
    	E tempE = tempNode.element;
    	tempNode.element = element;
        return tempE; //should remove this line
    }

    @Override
    public void add(int index, E element) {
        /*YOUR CODE HERE*/
        //insertLnewR(curNode.prev, newNode, curNode); //can use insertLnewR here
    	checkValidIndex(index, 0, size);
    	if(size == 0) {
    		add(element);
    	}else {
	    	Node<E> tempNode = getNode(index, 0, size);
	    	insertLnewR(tempNode.prev, new Node<E>(null,null,element), tempNode);
    	}
    }

    @Override
    public E remove(int index) {
        /*YOUR CODE HERE*/
    	Node<E> tempNode = getNode(index, 0, size);
    	E tempE = tempNode.element;
    	removeNode(tempNode);
        return tempE; //should remove this line
    }

    @Override
    public int indexOf(Object o) {
        /*YOUR CODE HERE*/
    	Node<E> curNode = head.next;
    	int i=0;
    	while(curNode!=tail) {
    		if(curNode.element.equals(o))
    			return i;
    		curNode = curNode.next;
    		i++;
    	}
        return -1; //should remove this line
    }

    @Override
    public int lastIndexOf(Object o) {
        /*YOUR CODE HERE*/
    	Node<E> curNode = tail.prev;
    	int i = size-1;
    	while(curNode != head) {
    		if(curNode.element.equals(o))
    			return i;
    		curNode = curNode.prev;
    		i--;
    	}
        return -1; //should remove this line
    }

    @Override
    public ListIterator<E> listIterator() {
        return new FBWDIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        /*YOUR CODE HERE*/
    	return new FBWDIterator(index);
//        return null; //should remove this line
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

    ////////////////////////////////////////////////////////////////////
    //// BEGIN OF INNER CLASSES                                     ////
    ////////////////////////////////////////////////////////////////////
    @SuppressWarnings("hiding")
	private class Node<E>{
        E element;
        Node<E> next;
        Node<E> prev;
        Node(Node<E> prev, Node<E> next, E element){
            this.prev = prev;
            this.next = next;
            this.element = element;
        }
    }
    
    private class FWDIterator implements Iterator<E>{
        Node<E> curNode;
        boolean afterMove;
        FWDIterator(){
            curNode = DLinkedList.this.head.next;
            afterMove = false;
        }
        @Override
        public boolean hasNext() {
            return curNode !=  DLinkedList.this.tail;
        }

        @Override
        public E next() {
            E element = curNode.element;
            curNode = curNode.next;
            afterMove = true;
            return element;
        }

        @Override
        public void remove() {
            if(!afterMove) return;
            Node<E> prevNode = curNode.prev;
            removeNode(prevNode);
            afterMove = false;
        }
        
    }//End of MyIerator
    
    private class FBWDIterator extends FWDIterator implements ListIterator<E>{
        int curIndex;
        MoveType moveType;
        FBWDIterator(){
            super();
            curIndex = 0;
            moveType = MoveType.NEXT; //default
            afterMove = false;
        }
        FBWDIterator(int index){
            moveType = MoveType.NEXT; 
            
            if((index < 0) || (index > DLinkedList.this.size)){
                String message = String.format("Invalid index (=%d)", index);
                throw new IndexOutOfBoundsException(message);
            }
            //Assign values to curIdex and curNode
            if((DLinkedList.this.size - index ) < DLinkedList.this.size/2 ){
                curNode = DLinkedList.this.tail;
                curIndex = DLinkedList.this.size;
                while(curIndex > index){
                    curIndex -= 1;
                    curNode = curNode.prev;
                }
            }
            else{
                curNode = DLinkedList.this.head;
                curIndex = -1;
                while(curIndex < index){
                    curIndex += 1;
                    curNode = curNode.next;
                }
            } 
        }

        @Override
        public E next() {
            E e = super.next();
            curIndex += 1;
            moveType = MoveType.NEXT; 
            return e;
        }
        @Override
        public void remove() {
            if(!afterMove) return;
            Node<E> removedNode;
            if(moveType == MoveType.NEXT){
                removedNode = curNode.prev;
            }
            else{
                removedNode = curNode;
                curNode = curNode.next;
            }
            removeNode(removedNode);
            afterMove = false;
        }
        
        @Override
        public boolean hasPrevious() {
            return curNode.prev != DLinkedList.this.head;
        }

        @Override
        public E previous() {
            curNode = curNode.prev;
            curIndex -= 1;
            moveType = MoveType.PREV; 
            afterMove = true;
            return curNode.element;
        }

        @Override
        public int nextIndex() {
            return this.curIndex;
        }

        @Override
        public int previousIndex() {
            return curIndex -1;
        }

        @Override
        public void set(E e) {
            if(!afterMove) return;
            if(moveType == MoveType.NEXT){
                Node<E> prevNode = curNode.prev;
                prevNode.element = e;
            }
            else{
                curNode.element = e;
            }
        }

        @Override
        public void add(E e) {
            if(!afterMove) return;
            if(moveType == MoveType.NEXT){
                Node<E> prevNode = curNode.prev; //go to prev
                Node<E> newNode = new Node<>(null, null, e);
                insertLnewR(prevNode.prev, newNode, prevNode);
            }
            else{
                Node<E> newNode = new Node<>(null, null, e);
                insertLnewR(curNode.prev, newNode, curNode);
                curNode = curNode.prev; // to new node
            }
        }
    }//End of FBWDIterator   
}//End of DLinkedList
