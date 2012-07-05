//Test class implemented for testing. 
//I just keep integers in here
//compareto method implemented for comparator

public class ta implements Comparable<ta> {

	public int data;
	public ta(int d) {
		data = d;
	}
	

	public int compareTo(ta o) {
		int oabd = ((ta) o).data;
		//If one is bigger than the other
		return data - oabd;
	}
	public String toString() {
		// TODO Auto-generated method stub
		return "" + data; 
	}
	
}
