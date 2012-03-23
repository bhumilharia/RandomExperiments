import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {

    private int data;
    private Node left;
    private Node right;

    public Node() {
        super();
    }

    public Node(int data) {
        this();
        setData(data);
    }

    public boolean hasChildren() {
        return (! (this.left==null && this.right==null) );
    }

    public Node getLeftChild(){
    	return this.left;
    }
    public Node getRightChild(){
    	return this.right;
    }
    
    public void addLeftChild(Node child) throws Exception{
        if(left != null)
        	throw new Exception();

        left = child;
    }

    public void addRightChild(Node child) throws Exception{
        if(right != null)
        	throw new Exception();

        right = child;
    }
    
    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

}

