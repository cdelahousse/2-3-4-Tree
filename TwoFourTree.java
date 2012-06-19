
import java.util.Comparator;
import java.util.Iterator;

public class TwoFourTree implements SSet<Object> {

	private TwoFourNode myRootNode = new TwoFourNode(); //Tree needs a root
	



	//Find elem in  tree node, return index
	//XXX DOUBLED
	public int find(long key) //XXX LONG COMPARE OBJECT
	{
		TwoFourNode curNode = myRootNode;
		int childNumber;
		while(true)
		{
			if(( childNumber=curNode.findElem(key) ) != -1) //XXX COMPARE KEY
				return childNumber;           
			else if( curNode.leaf() )
				return -1;
			else
				curNode = getNextChild(curNode, key);
		}  // end while
	}



	//XXX CHANGE NAME
	public void insert(long dValue) //XXX long  XXX dvalue
	{
		TwoFourNode curNode = myRootNode;
		Data tempItem = new Data(dValue); //XXX dvalue

		while(true)
		{
			if( curNode.full() )               // if node full,
				{
					split(curNode);                   // split it
					curNode = curNode.parent();    // back up
					// search once
					curNode = getNextChild(curNode, dValue);
			}  // end if(node is full)

			else if( curNode.leaf() )
				break;                            // go insert
			// node is not full, not a leaf; so go to lower level
			else
				curNode = getNextChild(curNode, dValue);
		}  // end while

		curNode.addNewElem(tempItem); 
	}  // end insert()




	// gets appropriate child of node during search for value
	public TwoFourNode getNextChild(TwoFourNode theNode, long theValue) //XXX LONG
	{
		int i;
		// assumes node is not empty, not full, not a leaf
		int numElems = theNode.howManyElems();
		for(i=0; i<numElems; i++)          // for each item in node
		{                               // are we less?
			if( theValue < theNode.getElem(i).getData() )
				return theNode.getChild(i);  // return left child
		}  // end for                   // we're greater, so
		return theNode.getChild(i);        // return right child
	}





	//XXX DELETE ME
	public void displayTree()
	{
		recDisplayTree(myRootNode, 0, 0);
	}
	
	
	//XXX DELETE ME
	private void recDisplayTree(TwoFourNode thisNode, int level, int childNumber)
	{
		System.out.print("level="+level+" child="+childNumber+" ");
		thisNode.displayNode();               // display this node

		// call ourselves for each child of this node
		int numElems= thisNode.howManyElems();
		for(int j=0; j<numElems+1; j++)
		{
			TwoFourNode nextNode = thisNode.getChild(j);
			if(nextNode != null)
				recDisplayTree(nextNode, level+1, j);
			else
				return;
		}
	}  // end recDisplayTree()





	public Iterator<? super Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Comparator<? super Object> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object find(Object x) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findGE(Object x) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findLT(Object x) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(Object x) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean remove(Object x) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public Iterator iterator(Object x) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean unionWith(SSet sset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersectWith(SSet sset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean differenceWith(SSet sset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean subsetOf(SSet sset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean belongsTo(Object x) {
		// TODO Auto-generated method stub
		return false;
	}
	//Helper function
	//As you go down the search path, we split every full node
	//Only called when node is full
	private void split(TwoFourNode node)  {


		//Node has 3 elems and four children 
		Data right = node.remove();
		Data mid = node.remove();  

		//Children 0 and 1 are handled later
		TwoFourNode c2 = node.removeEdge(2); 
		TwoFourNode c3 = node.removeEdge(3); 

		TwoFourNode parent;

		//Case: root
		if(myRootNode == node)   {
				myRootNode = new TwoFourNode();
				parent = myRootNode; 
				myRootNode.createEdge(0, node);   // connect to parent XXX
		}
		//Case Internal Node
		else {
			parent = node.parent(); 
		}

		//Add middle data elem to parent
		int iAdded = parent.addNewElem(mid);
		int elems = parent.howManyElems();

		//Shift edges
		for(int i=elems-1; i>iAdded; i--) {
			TwoFourNode tmp = parent.removeEdge(i); 
			parent.createEdge(i+1, tmp);    
		}

		//This will be new node on right
		TwoFourNode rnew = new TwoFourNode(); 
		parent.createEdge(iAdded+1, rnew); 

		//Repopulate node
		rnew.addNewElem(right);  
		rnew.createEdge(0, c2); 
		rnew.createEdge(1, c3); 
	}
	
	public String toString() {
		// You want to call toString on every node of this tree, starting from root.
		
		return null;
	}
}
