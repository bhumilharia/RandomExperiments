/**
 * @author Bhumil Haria
 * 
 *         This class represents a generic binary tree Node.
 * 
 * @param <T>
 *            The type of data in each Node
 */
public class Node<T> {

	private T data;
	private Node<T> leftChild;
	private Node<T> rightChild;

	/**
	 * Default constructor
	 */
	public Node() {
		super();
	}

	/**
	 * Parametrised constructor: Creates node and sets the data field of the
	 * calling Node object.
	 * 
	 * @param data
	 *            The object to be set in the Node data field
	 */
	public Node(T data) {
		this();
		setData(data);
	}

	/**
	 * Returns True if the calling Node has children, otherwise returns False
	 * 
	 * @return True if the calling Node has children, otherwise returns False
	 */
	public boolean hasChildren() {
		return (leftChild != null || rightChild != null);
	}

	/**
	 * Returns the left child Node of the calling Node
	 * 
	 * @return the left child Node of the calling Node if it exists, otherwise
	 *         null
	 */
	public Node<T> getLeftChild() {
		return leftChild;
	}

	/**
	 * Returns the right child Node of the calling Node
	 * 
	 * @return the right child Node of the calling Node if it exists, otherwise
	 *         null
	 */
	public Node<T> getRightChild() {
		return rightChild;
	}

	/**
	 * Adds a node as the left child of the calling Node object. Throws an
	 * Exception if the left child already exists. If the left child node is to
	 * be replaced, use the {@link #deleteLeftChild()} method to delete the left
	 * child node first, and then use this method to add the new child.
	 * 
	 * @param child
	 *            The Node object to be set as the left child
	 * @throws Exception
	 *             If a left child already exists
	 */
	public void addLeftChild(Node<T> child) throws Exception {
		if (leftChild != null)
			throw new Exception("Child aleady exists");
		leftChild = child;
	}

	/**
	 * Deletes the left child Node of the calling Node object. NOTE: If the
	 * child being deleted has children (is the root of a sub-tree), this will
	 * result in loss of the entire left sub-tree.
	 */
	public void deleteLeftChild() {
		leftChild = null;
	}

	/**
	 * Adds a node as the right child of the calling Node object. Throws an
	 * Exception if a right child already exists. If the right child node is to
	 * be replaced, use the {@link #deleteRightChild()} method to delete the
	 * right child node first, and then use this method to add the new child.
	 * 
	 * @param child
	 *            The Node object to be set as the right child
	 * @throws Exception
	 *             If a right child already exists
	 */
	public void addRightChild(Node<T> child) throws Exception {
		if (rightChild != null)
			throw new Exception("Child aleady exists");
		rightChild = child;
	}

	/**
	 * Deletes the right child Node of the calling Node object. NOTE: If the
	 * child being deleted has children (is the root of a sub-tree), this will
	 * result in loss of the entire right sub-tree.
	 */
	public void deleteRightChild() {
		rightChild = null;
	}

	/**
	 * Sets the data field of the calling Node object
	 * 
	 * @param nodeData
	 *            the data to be set
	 */
	public void setData(T nodeData) {
		data = nodeData;
	}

	/**
	 * Returns the data in this node
	 * 
	 * @return the data in this node
	 */
	public T getData() {
		return data;
	}

}