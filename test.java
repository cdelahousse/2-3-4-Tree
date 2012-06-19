import java.util.Comparator;
public class test {

	/**
	 * @param <T>
	 * @param args
	 */
	public static <T> void main(String[] args) {
		// TODO Auto-generated method stub

		TwoFourTree tree = new TwoFourTree(new DefaultComparator());
		
		//tComp(tree);
		
		
		
		//tadd(tree );
		testBelongs(tree);
		
		tree .displayTree(); //XXX
		System.out.println("Complete");
 
		
	}
   public static void tadd2(TwoFourTree t) {
	   
   }
   
   public static void tadd(TwoFourTree t) {

      t.add(new ta(50));
      t.add(new ta(40));
      t.add(new ta(41));
      t.add(new ta(72));
      t.add(new ta(73));
      t.add(new ta(45));
      t.add(new ta(42));
      t.add(new ta(49));
      t.add(new ta(34));
      t.add(new ta(60));
      t.add(new ta(61));
      t.add(new ta(62));
      t.add(new ta(31));
      t.add(new ta(44));
      t.add(new ta(30));
      t.add(new ta(66));
      t.add(new ta(32));
      t.add(new ta(70));
      t.add(new ta(71));
      t.add(new ta(67));
	 }
   //Do not allow doubles
   public static void taddDoubles(TwoFourTree t) {

      t.add(50);
      t.add(40);
      t.add(40);
      t.add(60);
      t.add(30);
      t.add(70);
	 }
   
   public static void testBelongs(TwoFourTree t) {
	   //Test root
		tadd(t);
		
		//Test level1
		System.out.println(t.belongsTo(new ta(61)));
		System.out.println(t.belongsTo(new ta(45)));
		
		
		//Leftmost level1
		System.out.println(t.belongsTo(new ta(34)));
		System.out.println(t.belongsTo(new ta(41)));
		
		//Leaf last leve
		System.out.println(t.belongsTo(new ta(72)));
		
		
		System.out.println(t.belongsTo(new ta(0)) == false);
		System.out.println(t.belongsTo(new ta(68)) == false);
		System.out.println(t.belongsTo(new ta(20)) == false);
		System.out.println(t.belongsTo(new ta(11)) == false);
		System.out.println(t.belongsTo(new ta(1000)) == false);
   }
   

   public static void tComp(TwoFourTree tree) {
	   
		System.out.println(tree .comparator());
		System.out.println("");
      
		Comparator<ta> c = new DefaultComparator<ta>();
		ta a = new ta(1);
		
		ta b = new ta(2);
		
		System.out.println(c.compare(a,b));
		System.out.println(c.compare(b,a));
   }
}
