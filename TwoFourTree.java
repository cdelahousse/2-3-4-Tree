
import java.util.Comparator;
import java.util.Iterator;

public class TwoFourTree implements SSet<Object> {

	protected TwoFourNode myRootNode = new TwoFourNode(); //Tree needs a root
	

	//For comparting
	Comparator c;
	
	public TwoFourTree() {
		c = new DefaultComparator();
	}
	public TwoFourTree(Comparator ca) {
		c = ca;
	}


	public boolean belongsTo(Object x) {
		// TODO Auto-generated method stub
		
		TwoFourNode current = myRootNode;
		
		
		//Do Forever
		while (true) {
			
			//Case: item in node
			if ( current.findElem(x)   > -1) {
				return true;
			}
			//If does not contain and is leaf, then return false;
			if (current.leaf()) {
				return false;
			}
			current = getChildSibling(current,x);
			
		}
	}
	

	//Find elem in  tree node, return index
	//XXX DOUBLED
	//public int find(long key) //XXX LONG COMPARE OBJECT
	public int findE(Object key) //XXX LONG COMPARE OBJECT
	{
		TwoFourNode curNode = myRootNode;
		int childNumber;
		
		//Go down entire length of tree until broken
		while(true)
		{
			if(( childNumber=curNode.findElem(key) ) != -1) //XXX COMPARE KEY
				return childNumber;           
			else if( curNode.leaf() )
				return -1;
			else
				curNode = getChildSibling(curNode, key);
		}  // end while
	}



	//Add. Return false if already there
	public boolean add(Object x) { 
		
		//Dissallow doubles
		//Needs to have check here here because of splits
		if (belongsTo(x) == true) {
			return false;
		}
		
		TwoFourNode current = myRootNode;
		Object tempItem = x; 

		while(true)
		{
			//Split nodes that are full as we traverse
			if( current.full() )  {
					split(current);
					//Start again from same level, but from left
					current= getChildSibling(current.parent(), x);
					continue;
			} 

			if( current.leaf() ) {
				break;
			}
			
			
			current = getChildSibling(current, x);
		} 

		current.addNewElem(tempItem); 
	
		//Assume successful insertion
		return true; //XXX
	} 




	//if you supply a parent and an object, you'll that parent's most likely 
	//child that'll house the object
	protected TwoFourNode getChildSibling(TwoFourNode parent, Object x) {
		
		int numElems = parent.howManyElems();
		//Iterate until right most
		for(int index=0; index<numElems; index++) {
			
			if( c.compare(x, parent.getElem(index)) < 0 ) {
				return parent.getChild(index); 
			}
		} 
		//Right most child ifnot found elsewhere
		return parent.getChild(numElems); 
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
	
	
	//Returns comparator
	public Comparator<? super Object> comparator() {
		return c;
	}

	public int size() {
		return myRootNode.size();
	}

	//public TwoFourNode findNextNode(TwoFourNode current) {
	//	TwoFourNode tmp = current; 
	//	Object x = 
		
	//	r
	//}
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

	public boolean remove(Object x) {
		// TODO Auto-generated method stub
		return false;
	}

	//clears tree
	public void clear() {
		 myRootNode = new TwoFourNode(); //Tree needs a root
		 //YAY FOR GARBAGE COLLECTION!
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
	//Helper function
	//As you go down the search path, we split every full node
	//Only called when node is full
	protected void split(TwoFourNode node)  {


		//Node has 3 elems and four children 
		Object right = node.remove();
		Object mid = node.remove();  

		//Children 0 and 1 are handled later
		TwoFourNode c2 = node.removeEdge(2); 
		TwoFourNode c3 = node.removeEdge(3); 

		TwoFourNode parent;

		//Case: root
		if(myRootNode == node)   {
				myRootNode = new TwoFourNode();
				parent = myRootNode; 
				myRootNode.createEdge(0, node);
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
