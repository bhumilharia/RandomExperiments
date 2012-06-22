import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Bhumil Haria
 * 
 *         This class implements the Callable interface (and runs a new thread
 *         whenever an object of this class is invoked and started.) This
 *         implements a parallel Depth-first Traversal by recursively invoking
 *         new threads for each node
 * 
 * @param <T>
 *            The type of data that the traversed Tree stores in each Node
 */

public class ParallelDFTraverser<T> implements Callable<ArrayList<T>> {

	private Node<T> node;
	private ArrayList<T> traversalResult;

	private static Long liveObjectCount = 0L;
	private static ExecutorService executor;

	/**
	 * Increments the count of the number of 'live' objects of this class
	 */
	private static void incrementLiveObjectCount() {
		synchronized (liveObjectCount) {
			liveObjectCount += 1;
		}
	}

	/**
	 * Decrements the count of the number of 'live' objects of this class
	 */
	private static void decrementLiveObjectCount() {
		synchronized (liveObjectCount) {
			liveObjectCount -= 1;
		}
	}

	/**
	 * Initialises the ExecutorService field in the class to create a new
	 * ThreadPool if it already doesn't exist
	 */
	private static void initExecutorService() {

		// If 'executor' has not been initialised at all
		if (ParallelDFTraverser.executor == null)
			ParallelDFTraverser.executor = Executors.newCachedThreadPool();
		else {
			synchronized (executor) {
				// If it is shutdown
				if (ParallelDFTraverser.executor.isShutdown())
					ParallelDFTraverser.executor = Executors
							.newCachedThreadPool();
				// If it is initialised and running, do nothing
			}
		}
	}

	/**
	 * Shuts down the ParallelDFTraverser.executor object (and sets it to null)
	 * if there are no more "live" objects of this class, if Shutdown takes
	 * place within the stipulated timeout period.
	 * 
	 * @return True if executor was shutdown (and set to null), False in all
	 *         other cases
	 */
	private static boolean terminateExecutorService() {
		synchronized (liveObjectCount) {

			if (liveObjectCount == 0) {
				// Safe to shutdown without affecting other running traversals

				executor.shutdown();

				try {
					executor.awaitTermination(5, TimeUnit.SECONDS);
					executor = null;

					return true;
				} catch (InterruptedException ie) {
					ie.printStackTrace();
					return false;
				}
			} else {
				// If liveObjectCount != 0, other objects are living and
				// possibly are/will be competing for threads, and might default
				// if this is shutdown
				return false;
			}
		}
	}

	/**
	 * Parametrised constructor: Creates a new ParallelDFTraverser object and
	 * initialises the Node for which thread is created.
	 * 
	 * @param node
	 *            The Node object which this thread has to work on
	 */
	public ParallelDFTraverser(Node<T> node) {
		this.node = node;
		this.traversalResult = new ArrayList<T>();

		ParallelDFTraverser.initExecutorService();
		ParallelDFTraverser.incrementLiveObjectCount();
	}

	/**
	 * This method should be invoked when an object has completed traversals and
	 * is no longer needed. This causes the count of 'live' objects of this
	 * class to be decremented, and resources freed. The parameter
	 * terminateExecutorService should be set to true if the ThreadPool is no
	 * longer needed and is to be shut down. If set to false, the ThreadPool is
	 * left as is.
	 * 
	 * @param terminateExecutorService
	 *            should be set to true if the ThreadPool is no longer needed
	 *            and is to be shut down. If set to false, the ThreadPool is
	 *            left as is. If unsure, set it to false if there may be other
	 *            parallel tree traversals executing.
	 * 
	 * @return True if the ParallelDFTraverser.executor object is shutdown and
	 *         set to null, false otherwise. If the parameter
	 *         terminateExecutorService is set to false, this always returns
	 *         true.
	 */
	public boolean destroyTraverser(boolean terminateExecutorService) {
		ParallelDFTraverser.decrementLiveObjectCount();

		if (terminateExecutorService)
			return terminateExecutorService();
		return true;
	}

	@Override
	public ArrayList<T> call(){
		traversalResult = auxParallelDFSTraverse();
		return traversalResult;
	}

	/**
	 * Recursive method performing the Depth First Traversal
	 * 
	 * @return an ArrayList object with traversalResult of the subtree which has
	 *         this.node as root.
	 */
	private ArrayList<T> auxParallelDFSTraverse() {

		if (node == null)
			return new ArrayList<T>();

		// System.out.println("Entering thread with node: " + node.getData());

		ArrayList<T> left = new ArrayList<T>();
		ArrayList<T> right = new ArrayList<T>();

		// Left node subtree thread
		ParallelDFTraverser<T> worker1 = new ParallelDFTraverser<T>(
				node.getLeftChild());
		Future<ArrayList<T>> f1 = executor.submit(worker1);

		// Right node subtree thread
		ParallelDFTraverser<T> worker2 = new ParallelDFTraverser<T>(
				node.getRightChild());
		Future<ArrayList<T>> f2 = executor.submit(worker2);

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
