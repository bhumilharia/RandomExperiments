import java.util.*;

/**
 * @author Bhumil Haria
 * 
 *         This class is an implementation of a generic binary tree
 * 
 * @param <T>
 *            The type of data that each Node of the tree stores
 */
public class Tree<T> {

	/**
	 * The Node object representing the root node of the tree
	 */
	private Node<T> root;

	/**
	 * Default constructor
	 */
	public Tree() {
		super();
	}

	/**
	 * Returns the root node of the calling object
	 * 
	 * @return The root node
	 */
	public Node<T> getRoot() {
		return root;
	}

	/**
	 * Sets the root node of the calling Tree object
	 * 
	 * @param treeRoot
	 *            the node to be set as the root of tree
	 */
	public void setRoot(Node<T> treeRoot) {
		root = treeRoot;
	}

	/**
	 * Returns True if the tree is empty, False otherwise.
	 * 
	 * @return True if the tree is empty, False otherwise.
	 */
	public boolean isEmpty() {
		return (root == null);
	}
	
	public static<T> Tree<T> buildBinaryTree(ArrayList<T> list) {
		// Creating a BST
		
		ListIterator<T> iter = list.listIterator();		
		Tree<T> tree = new Tree<T>(); 

		try {
			T elem = (T) iter.next();
			Node<T> rt = new Node<T>(elem);
			tree.setRoot(rt); 
		} catch (NoSuchElementException e) {
			return null;
		}

		while (iter.hasNext()) {
			T elem = (T) iter.next();
			tree.addNode(tree.getRoot(), elem);
		}
		return tree;
	}

	private void addNode(Node<T> parentNode, T elem) {

		try {
			// This will not work for any type except "int"
			// Only writing this for quick prototyping for creating BST with
			// ints.
			// TODO: change this build method into something generic
			if (Integer.parseInt(elem.toString()) < Integer.parseInt(parentNode
					.getData().toString())) {
				if (parentNode.getLeftChild() == null)
					parentNode.addLeftChild(new Node<T>(elem));
				else
					addNode(parentNode.getLeftChild(), elem);
			} else {
				if (parentNode.getRightChild() == null)
					parentNode.addRightChild(new Node<T>(elem));
				else
					addNode(parentNode.getRightChild(), elem);
			}
		} catch (Exception e) {
			// Do nothing
		}

	}

	public ArrayList<T> parallelDFSTraverse() {

		ArrayList<T> traversalResult = new ArrayList<T>();
		ParallelDFTraverser<T> traverser = new ParallelDFTraverser<T>(root);
		
		traversalResult = (ArrayList<T>) traverser.call();

		// Shutdown executor
		traverser.destroyTraverser(true);

		// System.out.println("Exit: parallel DFS traverse");
		return traversalResult;	
	}

}