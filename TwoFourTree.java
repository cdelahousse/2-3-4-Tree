
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;

public class TwoFourTree implements SSet<Object> {

	protected TwoFourNode myRootNode = new TwoFourNode(); //Tree needs a root, no?
	

	//For comparating
	Comparator c;
	
	public TwoFourTree() {
		c = new DefaultComparator();
	}
	public TwoFourTree(Comparator ca) {
		c = ca;
	}


	public boolean belongsTo(Object x) {
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
	//XXX May NOT NEED
	//public int find(long key) //XXX LONG COMPARE OBJECT
	//public int findE(Object key) //XXX LONG COMPARE OBJECT
	//{
	//	TwoFourNode curNode = myRootNode;
	//	int childNumber;
	//	
	//	//Go down entire length of tree until broken
	//	while(true)
	//	{
	//		if(( childNumber=curNode.findElem(key) ) != -1) //XXX COMPARE KEY
	//			return childNumber;           
	//		else if( curNode.leaf() )
	//			return -1;
	//		else
	//			curNode = getChildSibling(curNode, key);
	//	}  // end while
	//}



	//Add. Return false if already there
	public boolean add(Object x) { 
		
		//Dissallow doubles
		//Needs to have check here here because of splits
		if (belongsTo(x) == true) {
			return false;
		}
		
		TwoFourNode current = myRootNode;
		Object tempItem = x; 

		while(true) {
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
		return true; 
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




	
	//Returns comparator
	public Comparator<? super Object> comparator() {
		return c;
	}

	public int size() {
		return myRootNode.size();
	}

	
	//Find smallest node
	protected TwoFourNode findNodeSmallest (TwoFourNode parent) {
		
		TwoFourNode current = parent;
		
		
		//Do Forever
		while (true) {
			
			if (current.leaf()) {
				break;
			}
			current = current.firstChild();
			
		}
		return current;
		
	}
	//Return node with largest elements
	protected TwoFourNode findNodeLargest(TwoFourNode parent) {
		
		TwoFourNode current = parent;
		
		
		//Do Forever
		while (true) {
			
			if (current.leaf()) {
				break;
			}
			current = current.lastChild();
			
		}
		return current;
		
	}
	//Find Largest element on tree
	protected Object findLargest() {
		return findNodeLargest(myRootNode).largestElem();
	}
	//Find Smallest element on tree
	protected Object findSmallest () {
		return findNodeSmallest(myRootNode).smallestElem();
		
	}
	
	//HELPER METHOD
	//Find the next object greater (but not equal to) x
	//ie. find next element on tree
	protected Object findG(Object x) {
		
		if (x == null) {
			throw new TwoFourNodeException("No Object as parameter");
		}
		
		Object max = findLargest();
		
		
		//if x is bigger than biggest. There is no bigger ones to return
		if (c.compare(max, x) <= 0) {
			return null;
		}
		
		//Root
		TwoFourNode current = myRootNode;
		
        while (true) {
        	
			//index greater or eq elem
	        int index = current.findG(x,max);
        
			if (index > -1) {
				max = current.getElem(index); //Bigger than or equals to x
			}
			if (current.leaf()) {
				return max;
			}
			current = getChildSibling(current,x);
        }
     
		
	}
	
	//Find nearest. Greater than and equal to
	public Object find(Object x) {
		if (x == null) {
			throw new TwoFourNodeException("No Object as parameter");
		}
		
		Object max = findLargest();
		
		//if x is bigger than biggest. There is no bigger ones to return
		if (c.compare(max, x) <= -1){
			return null;
		}
		
		
		//Root
		TwoFourNode current = myRootNode;
		
        while (true) {
        	
			//index greater or eq elem
	        int index = current.findGE(x,max);
        
			if (index > -1) {
				max = current.getElem(index); //Bigger than or equals to x
			}
			if (c.compare(max,x) == 0) {
				return max;
			}
			if (current.leaf()) {
				return max;
			}
			current = getChildSibling(current,x);
        }
     
	}

	//Like find, but if x == null, return smallest
	public Object findGE(Object x) {
		//Return Smallest object
		if (x == null) {
			return findSmallest(); 
		}
			
		return find(x); 
	}

		
	protected TwoFourNode getPrevChildSibling(TwoFourNode parent, Object x) {
		int numElems = parent.howManyElems();
		//Iterate until right most
		for(int index=0; index<numElems; index++) {
			
			if( c.compare(x, parent.getElem(index)) <= 0 ) {
				return parent.getChild(index); 
			}
		} 
		//Right most child ifnot found elsewhere
		return parent.getChild(numElems); 
	}
	//XXX
	public Object findLT(Object x) {
		
		if (x == null) {
			return findLargest();
		}
		
		//Root
		TwoFourNode current = myRootNode;
		
		Object min =  findSmallest();
		
		//if x is smaller than smallest. There is no bigger ones to return
		if (c.compare( x,min) <= -1 ) {
			return null;
		}
		//int level = 0;//
		
        while (true) {
        	//System.out.println("level: " + level);//XXX
        	
			//index  smallerthan (NOT EQ TO) x from node
	        int index = current.findLT(x,min);
	        
	        //System.out.println("Index: " + index); // XXX
        
	        //If found, assign
			if (index > -1) {
				min = current.getElem(index); 
		        //System.out.println("	found, MIN: " + current.getElem(index)); // XXX
			}
			
			//If the node was a leaf and not found in it, return min element  b/c can't traverse anymore
			if ( current.leaf()) {
		        //System.out.print(" isleaf: " ); // XXX
		        current.displayNode();
		        
				return min;
			}
			
			//level++;
			//Continue traversing
			current = getPrevChildSibling(current,x);
       }
	}
	
	//XXX
	public void testMerge() {
		//merge(myRootNode);
	   add(new ta(5));
	   add(new ta(9));
	   add(new ta(12));
		
	   TwoFourNode l1 = new TwoFourNode();
	   l1.addNewElem(new ta(2));
	   //TwoFourNode r1 = new TwoFourNode();
	   //r1.addNewElem(new ta(7));
	   myRootNode.createEdge(0, l1);
	   //myRootNode.createEdge(1, r);
	   TwoFourNode l = new TwoFourNode();
	   l.addNewElem(new ta(7));
	   TwoFourNode r = new TwoFourNode();
	   r.addNewElem(new ta(10));
	   
	   myRootNode.createEdge(1, l);
	   myRootNode.createEdge(2, r);
	   TwoFourNode r3 = new TwoFourNode();
	   r3.addNewElem(new ta(14));
	   myRootNode.createEdge(3, r3);
	   
	   displayTree();
	   System.out.println("merg");
	   merge(myRootNode,new ta(12), 2);
	}

	protected void remove(TwoFourNode node, Object x) {
		
		
		//BaseCase = Leaf
		if (node.leaf()) {
			int index = node.findElem(x);
			
		//Test for size? XXX
			
			if (index > -1) {
				remove(index);
				
			//Not Found on leaf
			} else {
				return;
			}
			
		}
		 //Not leaf
		//Internal node
		else {
			
			int index = node.findElem(x);
			
			//Found x
			if (index > -1) {
				
				//Get child of that precedes x. NOTE:  Preceding child has same index;
				TwoFourNode predChild = node.getChild(index);
				
				//Not Full
				//At least 2 keys
				if (predChild.howManyElems()> 1) { //XXX Check if right (at least t, which is two or more, meaning greater than 1)
					Object predecessor = findNodeLargest(predChild).largestElem();
					
					//Overwrite key with new element
					node.removeElem(index);
					node.addNewElem(predecessor);
					
					//Recur
					remove(predChild,predecessor);
					
				} 
				//Node lean/potential underflow/has only 1 element
				//UNDERFLOW
				else {
					
					//ZZ
					//Following child
					//TwoFourNode nextChild = getChildSibling(node,x); //Is this really the best way to find next child?  XXX
					
					TwoFourNode nextChild = node.getChild(index + 1) ;
					
					//If following child has two or more elements
					if (nextChild.howManyElems() > 1) { //XXX
						
						Object successor = findNodeSmallest(nextChild).smallestElem();
						
						//Overwrite key with new element
						node.removeElem(index);
						node.addNewElem(successor);
						
						//Recur
						remove(nextChild,successor);
						
						
					}
					//BOth children will underflow
					//Both children of node have t-1 keys
					//BOth children should have 1 keys
					else {
						
						//Merge
						merge(node,x,index);
						remove(predChild,x);
						
					}
				}
				
			}
			//x is not in this node and is internal node
			else {
				
				//Look for next potential child
				TwoFourNode c = getChildSibling(node, x);
				
				//If node is lean
				//one or less
				if (c.howManyElems() == 1) { //XXX
					
					TwoFourNode predChild = c.getChild(0);
					TwoFourNode nextChild = c.getChild(1) ;
					
					if (predChild.howManyElems() > 1) {
						
						
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//------------
						//HERE WE MUST
						//ROTATE LEFT
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//------------
					}
					else if (nextChild.howManyElems() > 1) {
						//------------
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//HERE WE MUST
						//ROTATE RIGHT 
						//------------
						//-------------------------//
						//-------------------------//
						//-------------------------//
						//-------------------------//
						
					}
					//Both children have less than 2 elems
					else {
						merge(c, x, 0); //XXX
						
					}
					
					
				//There's an issue	
				} else {
					throw new TwoFourNodeException("Issue!");
				}
				
				//Recur
				remove(c,x);
				
				
				
				
				
				
			}
			
		}
		
	}
	
	//Assume both child nodes are lean
	//XXX Change to protected
	//From parent node, split children, previous and following, element k
	public void merge(TwoFourNode node, Object k,int index) {
		
		TwoFourNode predChild = node.getChild(index);
		TwoFourNode nextChild = node.getChild(index + 1) ;
		
		Object temp = node.removeElem(index);
		if (c.compare(k, temp) != 0)
			throw new TwoFourNodeException("HE HAVE A MERGE ISSUE!");
		
		//Shift edges on parent
		//Maintain link to previous
		//Clobber the link to nextChild
		//Index + 1 is link to predChild
		//Clobber it with node.removeEdge(i+1);
		int i;
		for(i=index+1; i<node.howManyElems(); i++) { //XXX
			TwoFourNode tmp = node.removeEdge(i+1); 
			node.createEdge(i, tmp);    
		}
		//node.removeEdge(i);
		
		//Store first and second children of of next node after this node (follwoing child)
		//Node should only have two children
		TwoFourNode c0 = nextChild.firstChild();
		TwoFourNode c1= nextChild.getChild(1);
		
		//Add children to pred child
		predChild.createEdge(2, c0);
		predChild.createEdge(3, c0);
		
		int addIndex;
		addIndex = predChild.addNewElem(k); //Add Key at middle
		
		
		//Next child should only have 1 elem
		addIndex = predChild.addNewElem(nextChild.getElem(0));
		
		if (predChild.howManyElems() != 3) {
			
			throw new TwoFourNodeException("Another merge Issue!");
		}
		
		
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
	
	
	public boolean remove(Object x) {
		
		return removeHack(x);
	}
	
	//I couldn't get the recursive remove to work in every case, so to get the interface working, I implemented this kludge
	public boolean removeHack(Object x) {
		
		
		//Can't remove from an empty set
		if (size() == 0)  {
			
			return false;
		}
		
		
		TwoFourNode oldRoot = myRootNode;  //Store for failure
		
		TwoFourTree nt = new TwoFourTree();
		
		Object obj = findSmallest(); 
		
		Boolean flag = false;
		//Iterate over every element
		while ( obj != null ) {
			
			if (c.compare(x, obj) == 0) {
				flag = true;
				obj = findG(obj) ;
				continue;
			}
			nt.add(obj);
			obj = findG(obj) ;
		}
		
		myRootNode = nt.getRoot(); //XXX
		
		//New tree is resulting tree
		return flag;
		
	}

	//clears tree
	public void clear() {
		 myRootNode = new TwoFourNode(); //Tree needs a root
		 //YAY FOR GARBAGE COLLECTION!
	}

	//Returns iterator. If null is inputed, the iterator will be index at smallest element
	public Iterator iterator(Object x) {
		return new TreeIter(x);
	}
	//Same as above, but with no args. Starts from smallest
	public Iterator<? super Object> iterator() {
		return new TreeIter(findSmallest());
	}
	
	class TreeIter implements Iterator {
		Object next;
		Boolean last = false;
		
		public TreeIter(Object x) {
			if (c.compare(x, findLargest()) > 0 )
				throw new TwoFourNodeException("Can not start to iterate after last element of the set") ;
			if (c.compare(x, findLargest()) == 0 )
				last = true;
			//Returns smallest if x == null
			next = findGE(x);
		}

		public boolean hasNext() {
			if (next == null) {
				return false;
			} else {
				return true;
			}
		}

		public Object next() {
			Object toReturn = next;
			if ( next == null) {
				throw new NoSuchElementException("Has no next element");
			}
			if (last == true) {
				next = null;
			} else {
				next = findG(next);
			}
			return toReturn;
		}

		//XXX
		public void remove() {
			removeHack(next);
		}
	
	}
	

	//Unions with sset
	public boolean unionWith(SSet sset) {
		//Nothing to union
		if (sset.size() == 0) {
			return true;
		}
		Iterator iter = sset.iterator();
		Object obj;
		while (iter.hasNext()) {
			obj = iter.next();
			//add() does doubles checking
			add(obj);
		}
		if (sset.size() == 0) {
			//Something went wrong
			return false;
		}
		
		return true;
	}

	public TwoFourNode getRoot() {
		return myRootNode;
	}
	
	
	//Intersect two sets
	public boolean intersectWith(SSet sset) {
		
		//Empty set
		if (size() == 0 || sset.size() == 0 ) {
			
			myRootNode = new TwoFourNode();
			return true;
		}
		
		
		TwoFourNode oldRoot = myRootNode;  //Store for failure
		
		TwoFourTree nt = new TwoFourTree();
		
		Object obj = findSmallest(); 
		
		//Iterate over every element
		while ( obj != null) {
			
			if (sset.belongsTo(obj) == true) {
				nt.add(obj);
			}
			obj = findG(obj) ;
		}
		
		myRootNode = nt.getRoot(); //XXX
		
		//If empty set is result
		if (size() == 0) {
			//Reset old root
			myRootNode = oldRoot;
			//Fail
			return false;
		}
		
		//New tree is resulting tree
		return true;
	}

	public boolean differenceWith(SSet sset) {
		
		
		
		//Empty set
		if (size() == 0 || sset.size() == 0 ) {
			return true;
		}
		
		TwoFourNode oldRoot = myRootNode;  //Store for failure
		
		TwoFourTree nt = new TwoFourTree();
		
		Iterator iter = iterator();
		
		//Iterate over every element
		while ( iter.hasNext()) {
			
			Object obj = iter.next();
			if (sset.belongsTo(obj) == true) {
				continue;
			}
			nt.add(obj);
		}
		
		myRootNode = nt.getRoot(); //XXX
		
		//If empty set is result
		if (size() == 0) {
			//Reset old root
			myRootNode = oldRoot;
			//Fail
			return false;
		}
		
		//New tree is resulting tree
		return true;
		
	}

	//Is this set a subset of sset?
	public boolean subsetOf(SSet sset) {
		
		//Empty set is always a subset of any set
		if (size() == 0) {
			return true; 
		}
		
		Object obj = findSmallest(); 
		//Iterate over every element
		while ( obj != null) {
			
			if (sset.belongsTo(obj) == false) {
				return false;
			}
			obj = findG(obj) ;
		}
		return true;
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




	
	public String toString() {
		
		TwoFourNode current = myRootNode;
		String str = "";
		
		
		
		
		return null;
	}
}
