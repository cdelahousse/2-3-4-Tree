import java.util.Comparator;


public class TwoFourNode<T> {

	//Global data
	protected final TwoFourNode<T> divorcedAndChildless = null;
	protected TwoFourNode<T> children[] = new TwoFourNode[4];

	protected Comparator<T> c;

	private T[] elems = (T[]) new Object[3];


	protected TwoFourNode<T> 	parent;
	protected int	numberOfElems;

	public TwoFourNode() {
		numberOfElems = 0;
		c = new DefaultComparator<T>();
	}
	//If you want to pass in a caparator
	public TwoFourNode(Comparator<T> ca) {
		numberOfElems = 0;
		c = ca;
	}

	//elems on node
	//Number of elements in node
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
		TwoFourNode<T> test = firstChild();
		if (test == divorcedAndChildless) {
			return true;
		}else {
			return false;
		}
	}


	// create an edge between a child child and this node
	public void createEdge(int i, TwoFourNode<T> node) {
		children[i] = node;
		if(node != divorcedAndChildless) node.parent = this;
	}

	// delete edge from this node, return it
	public TwoFourNode<T> removeEdge(int i) {
		TwoFourNode<T> tmp = getChild(i);
		children[i] = divorcedAndChildless;
		return tmp;
	}


	//Get parent
	public TwoFourNode<T> parent() {
		return parent;
	}

	//Get first child
	public TwoFourNode<T> firstChild() {
		return children[0];
	}
	//Get  last child
	public TwoFourNode<T> lastChild() {
		return children[numberOfElems];
	}

	//Get child from node
	public TwoFourNode<T> getChild(int i) {
		return children[i];
	}

	//get Children
	public TwoFourNode<T>[] getChildren() {
		return children;
	}

	//Get object
	public T getElem(int i )  {
		return elems[i];
	}
	//Get smallest Elem
	public T smallestElem()  {
		return elems[0];
	}
	//Get largest Elem
	public T largestElem()  {
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
	public int findElem(T x) {
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

	//Find T that is greater than X, but less than max
	public int findG(T x, T max) {
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
    //Find T that is less than x, but more than min
	public int findLT(T x, T min) {

		int flag = -1;
		for(int i=2; i>=0; i--)  {
			if(elems[i] == null) {
				continue;
			}
			else if( (c.compare(elems[i], x) < 0) && (c.compare(elems[i], min) > 0)  ) {
				flag = i;
				break;
			}
		}
		return flag;
	}


	//Greater or equal to x, less than max
	public int findGE(T x, T max) {
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
	public T remove() {

		numberOfElems--;
		T elem= elems[numberOfElems];
		elems[numberOfElems] = null;
		return elem;
	}

	//Add a new element to current TwoFourNode
	//These nodes shouldn't be full
	//returns index of where value was added
	public int addNewElem(T obj) {


		//Inc node
		numberOfElems++;

		int indexToReturn = 0;
		//Shift left like arrayList
		for(int i=elems.length-1; i>=0; i--)  {  //3 - 1 = 2
				if(elems[i] == null)
					continue;
				else {

					T k = elems[i];
					if(c.compare(obj, k ) < 0)
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
		//Return index where added
		return indexToReturn;
	}

	//Removes elem at index, shift to fill space
	T removeElem(int index) {
		T toReturn = getElem(index);
		int i;
		for (i = index; i < numberOfElems-1; i++) {
			elems[i] = elems[i+1];
		}
		elems[i] = null;
		numberOfElems--;

		return toReturn;

	}

	//Converts node to string
	public String  toString() {
		String str = "|";
		for (int i = 0; i< howManyElems(); i++) {
			str+= elems[i] + "|";
		}
		return str;
	}
}
