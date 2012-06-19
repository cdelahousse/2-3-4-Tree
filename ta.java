

public class ta implements Comparable<ta> {

	public int data;
	public ta(int d) {
		data = d;
	}
	

	@Override
	public int compareTo(ta o) {
		// TODO Auto-generated method stub
		int oabd = ((ta) o).data;
		return data - oabd;
	}
	public String toString() {
		// TODO Auto-generated method stub
		return "" + data; 
	}
	
}
