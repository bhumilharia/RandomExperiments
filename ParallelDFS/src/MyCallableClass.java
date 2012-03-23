import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("rawtypes")
public class MyCallableClass implements Callable {

	private Node node;
	private ArrayList<Integer> traversalResult;
	public static ExecutorService executor;

	public MyCallableClass(Node node) {
		this.node = node;
		this.traversalResult = new ArrayList<Integer>();
		MyCallableClass.executor = Executors.newCachedThreadPool();
	}

	@Override
	public Object call() throws Exception {
		this.traversalResult = auxParallelDFSTraverse(node);
		return this.traversalResult;
	}

	@SuppressWarnings({ "unchecked" })
	private ArrayList<Integer> auxParallelDFSTraverse(Node node) {

		if (node == null)
			return new ArrayList<Integer>();

		// System.out.println("Entering thread with node: " + node.getData());

		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();

		// Left node subtree thread
		MyCallableClass worker1 = new MyCallableClass(node.getLeftChild());
		Future f1 = executor.submit(worker1);

		// Right node subtree thread
		MyCallableClass worker2 = new MyCallableClass(node.getRightChild());
		Future f2 = executor.submit(worker2);

		// Get Left and right subtree traversals
		try {
			left = (ArrayList<Integer>) f1.get();
			right = (ArrayList<Integer>) f2.get();

		} catch (Exception e) {
			e.printStackTrace();
		}

		traversalResult.addAll(left);
		traversalResult.addAll(right);
		traversalResult.add(node.getData());

		// System.out.println("Exiting thread with node: " + node.getData() +
		// " : " + traversalResult.toString());
		
		return traversalResult;
	}
}
