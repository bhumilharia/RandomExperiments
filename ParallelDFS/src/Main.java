/**
 * @author Bhumil Haria
 *  
 */
import java.util.ArrayList;

/** 
 * @author Bhumil Haria
 * 
 * This Main class is a wrapper for running, testing the parallel DFS Traversal algorithm
 */
public class Main {

	/**
	 * @param args Command-line arguments: None 
	 */
	public static void main(String[] args) {
		
		// Creating a binary tree
		// Test data
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(5);
		list.add(7);
		list.add(12);
		list.add(13);
		list.add(18);
		list.add(1);
		list.add(11);
		list.add(6);
		list.add(9);
		
		Tree<Integer> tree = new Tree<Integer>();
		tree = Tree.buildBinaryTree(list);
		
		// Running multiple times for verification (to check if parallelism affects order of the output) 
		for(int i = 1; i< 11; i++){
			ArrayList<Integer> dfsTraversal = tree.parallelDFSTraverse();
			System.out.println("Traversal " + i + ": " + dfsTraversal.toString());
		}
		
		System.out.println("Exit: main");
	}
}
