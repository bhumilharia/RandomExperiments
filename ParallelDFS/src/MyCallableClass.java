import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("rawtypes")
public class MyCallableClass<T> implements Callable {

	private Node<T> node;
	private ArrayList<T> traversalResult;
	public static ExecutorService executor;

	public MyCallableClass(Node<T> node) {
		this.node = node;
		this.traversalResult = new ArrayList<T>();
		
		// TODO
		MyCallableClass.executor = Executors.newCachedThreadPool();
	}

	@Override
	public Object call() throws Exception {
		traversalResult = auxParallelDFSTraverse(node);
		return traversalResult;
	}

	@SuppressWarnings({ "unchecked" })
	private ArrayList<T> auxParallelDFSTraverse(Node<T> node) {

		if (node == null)
			return new ArrayList<T>();

		// System.out.println("Entering thread with node: " + node.getData());

		ArrayList<T> left = new ArrayList<T>();
		ArrayList<T> right = new ArrayList<T>();

		// Left node subtree thread
		MyCallableClass worker1 = new MyCallableClass(node.getLeftChild());
		Future f1 = executor.submit(worker1);

		// Right node subtree thread
		MyCallableClass worker2 = new MyCallableClass(node.getRightChild());
		Future f2 = executor.submit(worker2);

		// Get Left and right subtree traversals
		try {
			left = (ArrayList<T>) f1.get();
			right = (ArrayList<T>) f2.get();

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
