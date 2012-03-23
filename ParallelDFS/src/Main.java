/**
 * @author Bhumil Haria
 *
 */

import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
		
		Tree tree = new Tree();
		tree.buildBinaryTree(list);
		
		for(int i = 1; i< 11; i++){
			ArrayList<Integer> dfsTraversal = tree.parallelDFSTraverse();
			System.out.println("Traversal " + i + ": " + dfsTraversal.toString());
		}
		
		System.out.println("Exit: main");
	}

}