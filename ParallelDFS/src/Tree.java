import java.util.*;
import java.util.concurrent.*;

public class Tree {

	private Node root;	
	
	public Tree() {
		super();
	}

	public Node getRoot() {
		return this.root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return (root == null);
	}

	public Tree buildBinaryTree(ArrayList<Integer> list) {
		// Creating a BST

		ListIterator<Integer> iter = list.listIterator();

		try {
			int elem = (Integer) iter.next();
			Node root = new Node(elem);
			this.setRoot(root);
		} catch (Exception e) {
			return null;
		}

		Node parentNode = root;

		while (iter.hasNext()) {
			int elem = (Integer) iter.next();
			this.addNode(parentNode, elem);
		}
		return this;
	}

	private void addNode(Node parentNode, int elem) {

		try {

			if (elem < parentNode.getData()) {
				if (parentNode.getLeftChild() == null)
					parentNode.addLeftChild(new Node(elem));
				else
					addNode(parentNode.getLeftChild(), elem);
			} 
			else {
				if (parentNode.getRightChild() == null)
					parentNode.addRightChild(new Node(elem));
				else
					addNode(parentNode.getRightChild(), elem);
			}

		} catch (Exception e) {
			// Do nothing
		}

	}

	public ArrayList<Integer> parallelDFSTraverse(){
		
		ArrayList<Integer> traversalResult = new ArrayList<Integer>();

		MyCallableClass runner = new MyCallableClass(this.root);
		
		try{
			traversalResult = (ArrayList<Integer>) runner.call();
		}
		catch(Exception e){
		}
		
		MyCallableClass.executor.shutdown();
		
		//System.out.println("Exit: parallel DFS traverse");		
		return traversalResult;
	}

}