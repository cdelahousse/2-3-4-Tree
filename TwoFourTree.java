
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


	//Get most likely child on left 
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

	protected boolean remove(TwoFourNode<T> node, T x) {


		//BaseCase = Leaf
		if (node.leaf()) {
			int index = node.findElem(x);

		//Test for size? XXX

			if (index > -1) {
				node.removeElem(index);
				return true;

			//Not Found on leaf
			} else {
				return false;
			}

		}
		 //Not leaf
		//Internal node
		else {

			int index = node.findElem(x);

			//Found x
			//THIS NODE IS INTERAL
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
					return remove(predChild,predecessor);

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
						return remove(nextChild,successor);


					}
					//BOth children will underflow
					//Both children of node have t-1 keys
					//BOth children should have 1 keys
					else {

						//Merge
						merge(node,x,index);
						return remove(predChild,x);

					}
				}

			}
			//x is not in this node and is internal node
			//INTERNAL NODE AND NOT FOUND IN THIS NODE
			else {

				//Look for next potential child
				TwoFourNode<T> c = getChildSibling(node, x);
				//Index of child c
				int indexC = getNextSiblingIndex(node, x);

					
				//Check for null XXX
				TwoFourNode<T> leftSibling = null; 
				TwoFourNode<T> rightSibling = null;
				if (indexC-1 >= 0) leftSibling = node.getChild(indexC-1);
				if (indexC+1 <= 3) rightSibling = node.getChild(indexC+1);
				
				//If node c is lean
				//one or less
				if (c.howManyElems() == 1) { //XXX


					//Left not lean
					if (leftSibling != null && leftSibling.howManyElems() > 1) { ///XXX CHEKC FOR NULL && leftSibling != null
						//Rotate right
						
						//Elem in x that precedes c //XXX Dblcheck indexC
						T k1 = node.removeElem(indexC-1); //removes elem before c. If c is child[1], then k1 should be elem[0] 
						int inew = c.addNewElem(k1); //Add k1 to c, should be first, returns index. Need to shift children
						TwoFourNode<T> lastchild = leftSibling.removeEdge(leftSibling.howManyElems()); //Store last child of left sibling //Remove last child. adds null
						T k2 = leftSibling.remove(); //Remove last key, replace with null
						node.addNewElem(k2);
						

						//Shift edges on c to make room for last child
						TwoFourNode<T> e0 = c.removeEdge(0);
						TwoFourNode<T> e1 = c.removeEdge(1);
						
						c.createEdge(1, e0);
						c.createEdge(2, e1);
						//Add last child of left sibling as first child of c
						c.createEdge(0, lastchild);
					}
					//Right not lean
					else if (rightSibling != null && rightSibling.howManyElems() > 1) { //XXX CHECK FOR NULL && rightSibling != null
						
						//Elem in x that precedes c //XXX Dblcheck indexC
						T k1 = node.removeElem(indexC); //removes elem after c. they should share index. Shifts elems 
						int inew = c.addNewElem(k1); //Add k1 to c, should be last, returns index. Need to shift children
						
						//Remove first child of right sibling
						TwoFourNode<T> firstchild = rightSibling.removeEdge(0); //Store last child of left sibling //Remove last child. adds null
						
						//Shift edges left to fill space on right sibling
						int j;
						for (j = 0; j < 4 - 1; j++  ) { //<3 because j+1 = 3 at a certain point
							TwoFourNode<T> tmp = rightSibling.getChild(j + 1); 
							rightSibling.createEdge(j, tmp);
						}
						rightSibling.createEdge(j, null); //Last child index  becomes null;
						
						T k2 = rightSibling.removeElem(0); //Remove first key of right sibling, shifts elems 
						node.addNewElem(k2);
						
						//Add as first child of righSib to be last child of c
						c.createEdge(c.howManyElems(), firstchild);
						
						
						//Rotate left

					}
					//Both siblings AND c have less than 2 elems. They are lean
					else {
						//So merge them
						
						//Check for null XXX
						//Merge c onto left
						if (leftSibling != null) {
							
							T k1 = node.removeElem(indexC-1); //Remove proper key from node, shift elems
							
							//Remove what should be c
							TwoFourNode<T> tmp = node.removeEdge(indexC);
							if ( tmp != c)
								throw new TwoFourNodeException("Issue!");
							
							//Shift edges left to fill space on node
							int j;
							for (j = indexC; j < 4 - 1; j++  ) { //<3 because j+1 = 3 at a certain point
								TwoFourNode<T> tmp2 = node.getChild(j + 1); 
								node.createEdge(j, tmp2);
							}
							node.createEdge(j, null); //Last child index  becomes null;
							
							//Add k1 to leftSibling;
							int inew = leftSibling.addNewElem(k1); //Add k1 to ls, should be last, returns index. Need to shift children
							//C only has one element, add it to left sibling
							inew = leftSibling.addNewElem(c.getElem(0));
							
							leftSibling.createEdge(2,c.getChild(0)); //Add first child of c
							leftSibling.createEdge(3,c.getChild(1)); //Add last child of c as last child of left sib
							
							
							//If parent collapsed
							if (node.howManyElems() <= 0) {
								TwoFourNode<T> parent = node.parent();
								
								if (node == myRootNode) {
									myRootNode = leftSibling;
								} else {
									//Find index
									int l;
									for (l = 0; l < 4-1; l++) {
										if(node == parent.getChild(l)) {
											break;
										}
									}
									if (l == 4) 
										throw new TwoFourNodeException("Issue!");
									
									//left sibling is new root
									parent.createEdge(l, leftSibling);
								}
							
							}
							//We'll be recurrning on c
							c = leftSibling;
						}
						//Merge with right sibling
						//Right sibling
						else {
							
							
							
							
							//Merge right sibling ONTO c
							
							T k1 = node.removeElem(indexC);
							
							c.addNewElem(k1);
							System.out.println(k1);
							
							//Shift edges left to fill space on node
							int j;
							
							//Start from link to rightsibling and clobber it
							for (j = indexC + 1; j < 4 - 1; j++  ) { //<3 because j+1 = 3 at a certain point
								TwoFourNode<T> tmp2 = node.getChild(j + 1); 
								node.createEdge(j, tmp2);
							}
							node.createEdge(j, null); //Last child index  becomes null;
							
							c.createEdge(2,rightSibling.getChild(0));
							c.createEdge(3,rightSibling.getChild(1));
							T rightSibElem = rightSibling.removeElem(0);
							c.addNewElem(rightSibElem);
							
					System.out.println("Pass"); //XXX
					System.out.println("node" + node); //XXX
					System.out.println("nodeparent" + node.parent); //XXX
					System.out.println("myrootnode" + myRootNode); //XXX
					System.out.println("c" + c.getChild(3)); //XXX
					System.out.println("left" + leftSibling); //XXX
					System.out.println("right" + rightSibling); //XXX
//					System.out.println(toString()); //XXX
							
							
							
							
							
							//If parent collapsed
							if (node.howManyElems() <= 0) {
								TwoFourNode<T> parent = node.parent();
								//If root
								if (node == myRootNode) {
									//Replace current root
									myRootNode = c;
									c.parent = null;
								} else {
									//Find index
									int l;
									for (l = 0; l < 4-1; l++) {
										if(node == parent.getChild(l)) {
											break;
										}
									}
									if (l == 4) 
										throw new TwoFourNodeException("Issue!");
								
									//left sibling is new root
									parent.createEdge(l, c);
									
								}
								
							} //end collaspe
							
							
						} //end righ sibling
							
							
							
					} //End siblings lean and c lean

				} //End c lean

				//Recur
			return remove(c,x);
			} //End not found in this internal node 
			
		}//End all interal
	}


	//if you supply a parent and an T, you'll that parent's most likely
	//index of the child that'll house the T
	protected int getNextSiblingIndex(TwoFourNode<T> parent, T x) {

		int numElems = parent.howManyElems();
		//Iterate until right most
		for(int index=0; index<numElems; index++) {

			if( c.compare(x, parent.getElem(index)) < 0 ) {
				//return parent.getChild(index);
				return index; 
			}
		}
		//Right most child ifnot found elsewhere
		return numElems;
	}
	//Assume both child nodes are lean
	//From parent node, split children, previous and following, element k
	//|prev|k|following|
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
		//for(i=index+1; i<node.howManyElems(); i++) { //XXX
		for(i=index+1; i<4-1; i++) { //XXX
			TwoFourNode<T> tmp = node.removeEdge(i+1);
			node.createEdge(i, tmp);
		}
		node.removeEdge(i);

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
		//XXX
		return remove(myRootNode,x);
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

//	public TwoFourNode<T> getRoot() {
//		return myRootNode;
//	}


	//Intersect two sets
	public boolean intersectWith(SSet<T> sset) {

		//Empty set
		if (size() == 0 || sset.size() == 0 ) {

			//Intersection of empty set is empty set
			myRootNode = new TwoFourNode<T>();
			return true;
		}


		//TwoFourNode<T> oldRoot = myRootNode;  //Store for failure

		//TwoFourTree nt = new TwoFourTree();

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
