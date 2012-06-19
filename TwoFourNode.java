
public class TwoFourNode {
	
	//Global data
	protected final TwoFourNode divorcedAndChildless = null;
	protected TwoFourNode children[] = new TwoFourNode[4];
	protected Data elems[] = new Data[3]; //FRM BOOK XXX

	//FRM ORIGINAL XXX
	//private Object[] 		elems; //XXX THIS IS HOW WE WANT TO STORE DATA. SEE TODO


	protected TwoFourNode 	parent;
	protected int	numberOfElems;
	public void TwoFourNode() {
		numberOfElems = 0;
	}

	//elems on node
	public int howManyElems() {
		return numberOfElems;
	}

	//Test if leaf
	public boolean leaf() {
		TwoFourNode test = children[0];
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
		TwoFourNode tmp = children[i];
		children[i] = divorcedAndChildless;
		return tmp;
	}

	//Get child from node
	public TwoFourNode getChild(int i) { 
		return children[i]; 
	}

	//Get parent
	public TwoFourNode parent() {
		return parent;
	}


	//get Children
	public TwoFourNode[] getChildren() {
		return children;
	}

	//Get object
	public Data getElem(int i )  {
		return elems[i];
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
	public int findElem(long key) {        //XXX LONG and COMPARE
		int flag = -1;

		for(int i=0; i<3; i++)  { 
				if(elems[i].getData() == key) { //XXX LONG and COMPARE
					flag = i;
				}
				else if(elems[i] == null) {
					break;
				}
		}
		return flag;
	} 

	//Remove the right most element
	public Data remove() {

		numberOfElems--; 
		Data itm = elems[numberOfElems]; 
		elems[numberOfElems] = null; 
		return itm; 
	}

	//Add a new element to current TwoFourNode
	//These nodes shouldn't be full
	//returns index of where value was added
	public int addNewElem(Data obj) {


		long newKey = obj.getData();        //XXX LONG AND COMPARABLE

		//Inc node
		numberOfElems++;

		int indexToReturn = 0; 
		//Shift left like arrayList
		for(int i=elems.length-1; i>=0; i--)  {  //3 - 1 = 2
				if(elems[i] == null)    
					continue; 
				else {                             

					long itsKey = elems[i].getData(); //XXX LONG and COMPARE
					//If bigger than, shift left
					if(newKey < itsKey) //XXX COMPARABLE
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
	public void displayNode()           // format "/24/56/74/"
	{
		for(int i=0; i<numberOfElems; i++)
			elems[i].displayItem();   // "/56"
		System.out.println("/");         // final "/"
	}



	// Add the rest of the methods you need, including maintaining items. A Node can have one or more
	// items.

	//TO-DO: Create a string which includes the parent of the node, if any, plus, all of it's children.
	//The format should be easy to follow and useful. Think about at as a helper method for debugging/testing
	public String  toString() { //XXX
		return null;
	}
}
