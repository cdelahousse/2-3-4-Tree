
import java.util.*;


public class TwoFourTree<T> implements SSet<T> {

	protected TwoFourNode<T> myRootNode = new TwoFourNode<T>(); //Tree needs a root, no?
	

	//For comparating
	Comparator<? super T> c;
	
	public TwoFourTree() {
		c = new DefaultComparator<T>();
	}
	public TwoFourTree(Comparator<? super T> ca) {
		c = ca;
	}


	public boolean belongsTo(T x) {
		TwoFourNode<T> current = myRootNode;
		
		
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
	

	//Add. Return false if already there
	public boolean add(T x) { 
		
		//Dissallow doubles
		//Needs to have check here here because of splits
		if (belongsTo(x) == true) {
			return false;
		}
		
		TwoFourNode<T> current = myRootNode;
		T tmp = x; 

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

		current.addNewElem(tmp); 
	
		//Assume successful insertion
		return true; 
	} 




	//if you supply a parent and an T, you'll that parent's most likely 
	//child that'll house the T
	protected TwoFourNode<T> getChildSibling(TwoFourNode<T> parent, T x) {
		
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
	public Comparator<? super T> comparator() {
		return c;
	}

	public int size() {
		return myRootNode.size();
	}

	
	//Find smallest node from parent
	protected TwoFourNode<T> findNodeSmallest (TwoFourNode<T> parent) {
		
		TwoFourNode<T> current = parent;
		
		
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
	protected TwoFourNode<T> findNodeLargest(TwoFourNode<T> parent) {
		
		TwoFourNode<T> current = parent;
		
		
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
	protected T findLargest() {
		return findNodeLargest(myRootNode).largestElem();
	}
	//Find Smallest element on tree
	protected T findSmallest () {
		return findNodeSmallest(myRootNode).smallestElem();
		
	}
	
	//HELPER METHOD
	//Find the next T greater (but not equal to) x
	//ie. find next element on tree
	protected T findG(T x) {
		
		if (x == null) {
			throw new TwoFourNodeException("No T as parameter");
		}
		
		T max = findLargest();
		
		
		//if x is bigger than biggest. There is no bigger ones to return
		if (c.compare(max, x) <= 0) {
			return null;
		}
		
		//Root
		TwoFourNode<T> current = myRootNode;
		
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
	
	//Find nearest element Greater than and equal to
	public T find(T x) {
		if (x == null) {
			throw new TwoFourNodeException("No T as parameter");
		}
		
		T max = findLargest();
		
		//if x is bigger than biggest. There is no bigger ones to return
		if (c.compare(max, x) <= -1){
			return null;
		}
		
		
		//Root
		TwoFourNode<T> current = myRootNode;
		
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
	public T findGE(T x) {
		//Return Smallest T
		if (x == null) {
			return findSmallest(); 
		}
			
		return find(x); 
	}

		
	protected TwoFourNode<T> getPrevChildSibling(TwoFourNode<T> parent, T x) {
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
	
	
	//Find find closest element less than x
	public T findLT(T x) {
		
		//If input is null
		if (x == null) {
			return findLargest();
		}
		
		//Root
		TwoFourNode<T> current = myRootNode;
		
		T min =  findSmallest();
		
		//if x is smaller than smallest. There is no bigger ones to return
		if (c.compare( x,min) <= -1 ) {
			return null;
		}
		
        while (true) {
        	
			//index  smallerthan (NOT EQ TO) x from node
	        int index = current.findLT(x,min);
	        
        
	        //If found, assign
			if (index > -1) {
				min = current.getElem(index); 
			}
			
			//If the node was a leaf and not found in it, return min element  b/c can't traverse anymore
			if ( current.leaf()) {
		        
				return min;
			}
			
			//Continue traversing
			current = getPrevChildSibling(current,x);
       }
	}
	
	//For testing remove() and merge()
	//I left this here just to have an idea of how to test it
//	public void testMerge() {
//		//merge(myRootNode);
//	   add(new ta(5));
//	   add(new ta(9));
//	   add(new ta(12));
//		
//	   TwoFourNode<T> l1 = new TwoFourNode<T>();
//	   l1.addNewElem(new ta(2));
//	   //TwoFourNode r1 = new TwoFourNode();
//	   //r1.addNewElem(new ta(7));
//	   myRootNode.createEdge(0, l1);
//	   //myRootNode.createEdge(1, r);
//	   TwoFourNode<T> l = new TwoFourNode<T>();
//	   l.addNewElem(new ta(7));
//	   TwoFourNode<T> r = new TwoFourNode<T>();
//	   r.addNewElem(new ta(10));
//	   
//	   myRootNode.createEdge(1, l);
//	   myRootNode.createEdge(2, r);
//	   TwoFourNode<T> r3 = new TwoFourNode<T>();
//	   r3.addNewElem(new ta(14));
//	   myRootNode.createEdge(3, r3);
//	   
//	   System.out.println("merg");
//	   merge(myRootNode,new ta(12), 2);
//	}

	protected void remove(TwoFourNode<T> node, T x) {
		
		
		//BaseCase = Leaf
		if (node.leaf()) {
			int index = node.findElem(x);
			
		//Test for size? XXX
			
			if (index > -1) {
				node.removeElem(index);
				
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
				TwoFourNode<T> predChild = node.getChild(index);
				
				//Not Full
				//At least 2 keys
				if (predChild.howManyElems()> 1) { //XXX Check if right (at least t, which is two or more, meaning greater than 1)
					T predecessor = findNodeLargest(predChild).largestElem();
					
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
					
					TwoFourNode<T> nextChild = node.getChild(index + 1) ;
					
					//If following child has two or more elements
					if (nextChild.howManyElems() > 1) { //XXX
						
						T successor = findNodeSmallest(nextChild).smallestElem();
						
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
				TwoFourNode<T> c = getChildSibling(node, x);
				
				//If node is lean
				//one or less
				if (c.howManyElems() == 1) { //XXX
					
					TwoFourNode<T> predChild = c.getChild(0);
					TwoFourNode<T> nextChild = c.getChild(1) ;
					
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
						//So merge them
						merge(c, x, 0);
						
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
	//From parent node, split children, previous and following, element k
	protected void merge(TwoFourNode<T> node, T k,int index) {
		
		TwoFourNode<T> predChild = node.getChild(index);
		TwoFourNode<T> nextChild = node.getChild(index + 1) ;
		
		T temp = node.removeElem(index);
		if (c.compare(k, temp) != 0)
			throw new TwoFourNodeException("HE HAVE A MERGE ISSUE!");
		
		//Shift edges on parent
		//Maintain link to previous
		//Clobber the link to nextChild
		//Index + 1 is link to predChild
		//Clobber it with node.removeEdge(i+1);
		int i;
		for(i=index+1; i<node.howManyElems(); i++) { //XXX
			TwoFourNode<T> tmp = node.removeEdge(i+1); 
			node.createEdge(i, tmp);    
		}
		//node.removeEdge(i);
		
		//Store first and second children of of next node after this node (follwoing child)
		//Node should only have two children
		TwoFourNode<T> c0 = nextChild.firstChild();
		TwoFourNode<T> c1= nextChild.getChild(1);
		
		//Add children to pred child
		predChild.createEdge(2, c0);
		predChild.createEdge(3, c1);
		
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
	protected void split(TwoFourNode<T> node)  {


		//Node has 3 elems and four children 
		T right = node.remove();
		T mid = node.remove();  

		//Children 0 and 1 are handled later
		TwoFourNode<T> c2 = node.removeEdge(2); 
		TwoFourNode<T> c3 = node.removeEdge(3); 

		TwoFourNode<T> parent;

		//Case: root
		if(myRootNode == node)   {
				myRootNode = new TwoFourNode<T>();
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
			TwoFourNode<T> tmp = parent.removeEdge(i); 
			parent.createEdge(i+1, tmp);    
		}

		//This will be new node on right
		TwoFourNode<T> rnew = new TwoFourNode<T>(); 
		parent.createEdge(iAdded+1, rnew); 

		//Repopulate node
		rnew.addNewElem(right);  
		rnew.createEdge(0, c2); 
		rnew.createEdge(1, c3); 
	}
	
	
	public boolean remove(T x) {
		
		return removeHack(x);
	}
	
	//I couldn't get the recursive remove to work in every case, so to get the interface working, I implemented this kludge
	public boolean removeHack(T x) {
		
		
		//Can't remove from an empty set
		if (size() == 0)  {
			
			return false;
		}
		
		
		
		TwoFourTree<T> nt = new TwoFourTree<T>();
		
		T obj = findSmallest(); 
		
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
		 myRootNode = new TwoFourNode<T>(); //Tree needs a root
		 //YAY FOR GARBAGE COLLECTION!
	}

	//Returns iterator. If null is inputed, the iterator will be index at smallest element
	public Iterator<T> iterator(T x) {
		return new TreeIter(x);
	}
	//Same as above, but with no args. Starts from smallest
	public Iterator<T> iterator() {
		return new TreeIter(findSmallest());
	}
	
	class TreeIter implements Iterator<T> {
		T next;
		Boolean last = false;
		
		public TreeIter(T x) {
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

		public T next() {
			T toReturn = next;
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

		public void remove() {
			removeHack(next);
		}
	
	}
	

	//Unions with sset
	public boolean unionWith(SSet<T> sset) {
		//Nothing to union
		if (sset.size() == 0) {
			return true;
		}
		Iterator<T> iter = sset.iterator();
		T obj;
		while (iter.hasNext()) {
			obj = iter.next();
			//add() does checking for doubles
			add(obj);
		}
		if (sset.size() == 0) {
			//Something went wrong
			return false;
		}
		
		return true;
	}

	public TwoFourNode<T> getRoot() {
		return myRootNode;
	}
	
	
	//Intersect two sets
	public boolean intersectWith(SSet<T> sset) {
		
		//Empty set
		if (size() == 0 || sset.size() == 0 ) {
			
			myRootNode = new TwoFourNode<T>();
			return true;
		}
		
		
		TwoFourNode<T> oldRoot = myRootNode;  //Store for failure
		
		TwoFourTree nt = new TwoFourTree();
		
		T obj = findSmallest(); 
		
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

	public boolean differenceWith(SSet<T> sset) {
		
		
		
		//Empty set
		if (size() == 0 || sset.size() == 0 ) {
			return true;
		}
		
		TwoFourNode<T> oldRoot = myRootNode;  //Store for failure
		
		TwoFourTree<T> nt = new TwoFourTree<T>();
		
		Iterator<T> iter = iterator();
		
		//Iterate over every element
		while ( iter.hasNext()) {
			
			T obj = iter.next();
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
	public boolean subsetOf(SSet<T> sset) {
		
		//Empty set is always a subset of any set
		if (size() == 0) {
			return true; 
		}
		
		T obj = findSmallest(); 
		//Iterate over every element
		while ( obj != null) {
			
			if (sset.belongsTo(obj) == false) {
				return false;
			}
			obj = findG(obj) ;
		}
		return true;
	}
	

	//Recursively traverse node, print it out
	//Print the child and the leve
	protected String nodeToString(TwoFourNode<T> node, int lvl, int child) {
		
		String str = "l:" + lvl + " c:" + child + " " + node.toString() + "\n";
		
		int n = node.howManyElems() + 1;
		TwoFourNode<T> aChild;
		for (int i=0; i < n; i++) {
			aChild = node.getChild(i);
			if (aChild != null) {
				str += nodeToString(aChild, lvl + 1, i);
			}
		}
		return str;
	}

	
	public String toString() {
		
		return nodeToString(myRootNode, 0, 0);
		
	}
}
