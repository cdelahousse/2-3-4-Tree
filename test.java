import java.util.Comparator;
public class test {

	/**
	 * @param <T>
	 * @param args
	 */
	public static <T> void main(String[] args) {
		// TODO Auto-generated method stub
		Comparator<ta> c = new DefaultComparator<ta>();
		ta a = new ta(1);
		
		ta b = new ta(2);
		
		System.out.println(c.compare(a,b));
		System.out.println(c.compare(b,a));
		

	}

}
