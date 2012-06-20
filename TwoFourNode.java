import java.util.Comparator;


public class TwoFourNode<T> {
	
	//Global data
	protected final TwoFourNode divorcedAndChildless = null;
	protected TwoFourNode children[] = new TwoFourNode[4];

	protected Comparator c;

	private Object[] elems = new Object[3]; //XXX THIS IS HOW WE WANT TO STORE DATA. SEE TODO
	//protected Data elems[] = new Data[3]; //FRM BOOK XXX


	protected TwoFourNode 	parent;
	protected int	numberOfElems;
	
	public TwoFourNode() {
		numberOfElems = 0;
		c = new DefaultComparator();
	}
	//If you want to pass in a caparator
	public TwoFourNode(Comparator<T> ca) {
		numberOfElems = 0;
		c = ca;
	}

	//elems on node
	public int howManyElems() {
		return numberOfElems;
	}
	
	//Return number of elements on this node and lower 
	public int size() {
		//If this is a leaf return number of elements
		if (this.leaf() == true) {
			return numberOfElems;
		}
		
		int sum = numberOfElems;
		//Add size of all children
		for (int i = 0; i < numberOfElems+1; i++) {
			sum += getChild(i).size();
		}
		
		
		
		return sum;
	}

	//Test if leaf
	public boolean leaf() {
		TwoFourNode test = firstChild();
		if (test == divorcedAndChildless) {
			return true;
		}else {
			return false;
		}
	}


	// create an edge between a child child and this node
	public void createEdge(int i, TwoFourNode node) {
		children[i] = node;
		if(node != divorcedAndChildless) node.parent = this;
	}

	// delete edge from this node, return it
	public TwoFourNode removeEdge(int i) {
		TwoFourNode tmp = getChild(i);
		children[i] = divorcedAndChildless;
		return tmp;
	}


	//Get parent
	public TwoFourNode parent() {
		return parent;
	}

	public TwoFourNode firstChild() {
		return children[0];
	}
	public TwoFourNode lastChild() {
		return children[numberOfElems];
	}

	//Get child from node
	public TwoFourNode getChild(int i) { 
		return children[i]; 
	}
	
	//get Children
	public TwoFourNode[] getChildren() {
		return children;
	}

	//Get object
	public Object getElem(int i )  {
		return elems[i];
	}
	//Get smallest Elem
	public Object smallestElem()  {
		return elems[0];
	}
	//Get largest Elem
	public Object largestElem()  {
		return elems[numberOfElems-1];
	}

	//public boolean empty
	public boolean full() {
		if (numberOfElems >= 3) {
			return true;
		} else {
			return false;
		}
	}

	//searches for elem, returns index
	public int findElem(Object x) { //key->x       //XXX LONG and COMPARE
		int flag = -1;

		for(int i=0; i<3; i++)  { 
				if(elems[i] == null) {
					break;
				}
				else if( c.compare(elems[i], x) == 0 ) { 
					flag = i;
				}
		}
		return flag;
	} 
	
	//Find object that is greater than X, but less than max 
	public int findG(Object x, Object max) {
		int flag = -1;
		for(int i=0; i<3; i++)  { 
			if(elems[i] == null) {
				break;
			}
			else if( (c.compare(elems[i], x) > 0) && (c.compare(elems[i], max) < 0)  ) { 
				flag = i;
				break;
			}
		}
		return flag;
	}
	//Greater or equal to x, less than max
	public int findGE(Object x, Object max) {
		int flag = -1;
		for(int i=0; i<3; i++)  { 
			if(elems[i] == null) {
				break;
			}
			else if( (c.compare(elems[i], x) >= 0) && (c.compare(elems[i], max) < 0)  ) { 
				flag = i;
				break;
			}
		}
		
		return flag;
	}

	//Remove the right most element
	//Queue remove
	public Object remove() {

		numberOfElems--; 
		Object itm = elems[numberOfElems]; 
		elems[numberOfElems] = null; 
		return itm; 
	}

	//Add a new element to current TwoFourNode
	//These nodes shouldn't be full
	//returns index of where value was added
	public int addNewElem(Object obj) {



		//Object newKey = obj.getData();        //XXX LONG AND COMPARABLE

		//Inc node
		numberOfElems++;

		int indexToReturn = 0; 
		//Shift left like arrayList
		for(int i=elems.length-1; i>=0; i--)  {  //3 - 1 = 2
				if(elems[i] == null)    
					continue; 
				else {                             

					Object k = elems[i]; //XXX LONG and COMPARE
					//If bigger than, shift left
					//if(newKey < itsKey) //XXX COMPARABLE
					//if(((ta)newKey).data < ((ta)itsKey).data) //XXX COMPARABLE
					if(c.compare(obj, k ) < 0) //XXX COMPARABLE
						elems[i+1] = elems[i]; 

					//No more shifting
					else {
						elems[i+1] = obj; //insert elem
						indexToReturn = i+1; //store index
						break;
						} 
				}  
		} 
		elems[indexToReturn] = obj;
		//Return index were added
		return indexToReturn;
	} 


	//XXX DELETE ME!
	//XXX Output as one string only
	//XXX conver to .toString
	public void displayNode()           // format "/24/56/74/"
	{
		for(int i=0; i<numberOfElems; i++)
			System.out.print("|" + elems[i].toString() );   // "/56"
		System.out.println("|");         // final "/"
	}



	// Add the rest of the methods you need, including maintaining items. A Node can have one or more
	// items.

	//TO-DO: Create a string which includes the parent of the node, if any, plus, all of it's children.
	//The format should be easy to follow and useful. Think about at as a helper method for debugging/testing
	public String  toString() { //XXX
		return null;
	}
}
