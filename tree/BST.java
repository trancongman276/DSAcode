/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import stacknqueue.Queue;

/**
 *
 * @author LTSACH
 */
public class BST<T> implements IBinarySearchTree<T>, ITreeWalker<T> {
    private int size;
    Node<T> root;
    
    public BST(){
        this.size = 0;
        this.root = null;
    }

    ///
    private int key(T item){
        return item.hashCode();
    }
    private Node<T> add(Node<T> root, T item){
        if(root == null) return new Node<>(item, null, null);
        if(key(item) < key(root.item))
            root.left = this.add(root.left, item);
        else
            root.right = this.add(root.right, item);
        return root;
    }
    ///
    @Override
    public void add(T item) {
    	//TODO
    	if(size == 0){
    		root = new Node<>(item, null, null);
    	}else
    	add(root, item);
    	size++;
        /*YOUR CODE HERE*/
    }

    private Node<T> remove(Node<T> root, Object key) {
        if(root == null) return null;
        if(key.hashCode() < key(root.item)) {
            root.left = remove(root.left, key);
            return root;
        }
        else if(key.hashCode() > key(root.item)){
            root.right = remove(root.right, key);
            return root;
        }
        else{
        	//TODO
        	if(root.left != null && root.right != null) {
        		Node<T> maxLeft = root.left;
        		while(maxLeft.right != null) {
        			maxLeft = maxLeft.right;
        		}
        		root.item = maxLeft.item;
        		root.left = remove(root.left, root.item);
        	}else {
	        	if(root.left == null ) {
	        		root = root.right;
	        	}else if(root.right == null) {
	        		root = root.left;
	        	}
        	}
        	return root;
            /*YOUR CODE HERE*/
            //Remember to decrease size
//            return null;//remove this line
        }
    }
    @Override
    public void remove(Object key) {
//    	System.out.println("Root: " + root.item.hashCode());
//    	this.println();
    	size--;
        Node<T> temp = remove(root, key);
        if(temp!=null) 
        	root = temp;
        if(size == 0)
        	root = null;
    }

    private T search(Node<T> root, Object key) {
        if(root == null) return null;
        //TODO
        while(root!=null) {
        	if(key.hashCode() < key(root.item)) {
        		root = root.left;
        	}else
        		if(key.hashCode() > key(root.item)) {
        			root = root.right;
        		}
        		else {
        			return root.item;
        		}
        }
        /*YOUR CODE HERE*/
        return null;//remove this line
    }
    @Override
    public T search(Object key) {
        return search(this.root, key);
    }

    @Override
    public int size() {
        return this.size;
    }
    
    public String toString(){
    	if(this.root == null)
        	return "(NULL)";
    	else
        return this.root.toString();
    }
    public void println(){
        System.out.println(this.toString());
    }

    private void ascendingList(Node<T> root, List<T> list) {
        if(root == null) return;
        ascendingList(root.left, list);
        list.add(root.item);
        ascendingList(root.right, list);
    }
    @Override
    public List<T> ascendingList() {
    	//TODO
       /*YOUR CODE HERE*/
    	List<T> list = new LinkedList<>();
    	ascendingList(root, list);
    	return list;
    }

    private void descendingList(Node<T> root, List<T> list) {
        /*YOUR CODE HERE*/
    	if(root == null) return;
    	descendingList(root.right, list);
    	list.add(root.item);
    	descendingList(root.left, list);
    }
    @Override
    public List<T> descendingList() {
        List<T> list = new LinkedList<>();
        descendingList(root, list);
        return list;
    }

    @Override
    public List<T> dfs() {
        List<T> list = new LinkedList<>();
      //TODO
        /*YOUR CODE HERE*/
        nlr(root, list);
        return list;
    }

    @Override
    public List<T> bfs() {
        List<T> list = new LinkedList<>();
      //TODO
        Queue<Node<T>> queue = new Queue<>();
        Node<T> node = root;
        while(node!=null) {
        	list.add(node.item);
        	if(node.left!=null) {
        		queue.push(node.left);
        	}
        	if(node.right!=null) {
        		queue.push(node.right);
        	}
        	if(!queue.empty()) {
        		node = queue.pop();
        	}else node = null;
        }
        /*YOUR CODE HERE*/
        return list;
    }

    private void nlr(Node<T> root, List<T> list) {
    	//TODO
        /*YOUR CODE HERE*/
    	if(root == null) return;
    	list.add(root.item);
    	nlr(root.left, list);
    	nlr(root.right, list);
    }
    private void lrn(Node<T> root, List<T> list) {
    	//TODO
        /*YOUR CODE HERE*/
    	if(root == null) return;
    	lrn(root.left, list);
    	lrn(root.right, list);
    	list.add(root.item);
    }
    private void lnr(Node<T> root, List<T> list) {
    	//TODO
        /*YOUR CODE HERE*/
    	if(root == null) return;
    	lnr(root.left, list);
    	list.add(root.item);
    	lnr(root.right, list);
    }
    @Override
    public List<T> nlr() {
    	//TODO
       /*YOUR CODE HERE*/
    	List<T> list = new LinkedList<>();
    	nlr(root, list);
    	return list;
    }

    @Override
    public List<T> lrn() {
    	//TODO
        /*YOUR CODE HERE*/
    	List<T> list = new LinkedList<>();
    	lrn(root, list);
    	return list;
    }

    @Override
    public List<T> lnr() {
    	//TODO
        /*YOUR CODE HERE*/
    	List<T> list = new LinkedList<>();
    	lnr(root, list);
    	return list;
    }
    //
    @SuppressWarnings("hiding")
	public class Node<T>{
        T item;
        Node<T> left, right;
        public Node(T data, Node<T> left, Node<T> right){
            this.item = data;
            this.left = left;
            this.right = right;
        }
       
        public String toString(){
            String desc = "";
            if(this.left == null && this.right == null) 
                desc = String.format("(%s)", item);
            if(this.left == null && this.right != null)
                desc = String.format("(%s () %s)", item, this.right.toString());
            if(this.left != null && this.right == null)
                desc = String.format("(%s %s ())", item, this.left.toString());
            if(this.left != null && this.right != null)
                desc = String.format("(%s %s %s)", item, 
                        this.left.toString(), this.right.toString());
            return desc;
        }
    }
}
