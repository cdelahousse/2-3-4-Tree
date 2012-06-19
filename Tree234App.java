import java.io.*;
import java.util.Comparator;
class Tree234App
   {
   public static <T> void main(String[] args) throws IOException {



     //System.out.println(
		TwoFourTree theTree = new TwoFourTree(new DefaultComparator());
		

		
		

		
		tadd(theTree);
      

		theTree.displayTree();
		System.out.println(theTree.comparator());
		System.out.println("");


		return;
		
		//while(true)
			 //{
			 //System.out.print("Enter first letter of ");
			 //System.out.print("show, add, or find: ");
			 //char choice = getChar();
			 //switch(choice)
					//{
					//case 's':
						 //theTree.displayTree();
						 //break;
					//case 'i':
						 //System.out.print("Enter value to add: ");
						 //value = getInt();
						 //theTree.add(value);
						 //break;
					//case 'f':
						 //System.out.print("Enter value to find: ");
						 //value = getInt();
						 //int found = theTree.find(value);
						 //if(found != -1)
								//System.out.println("Found "+value);
						 //else
								//System.out.println("Could not find "+value);
						 //break;
					//default:
						 //System.out.print("Invalid entry\n");
					//}  // end switch
			 //}  // end while
         
         
         
         
         
         
      }  // end main()
   
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
   
   
   
   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }

//-------------------------------------------------------------
   public static int getInt() throws IOException
      {
      String s = getString();
      return Integer.parseInt(s);
      }
//-------------------------------------------------------------
   }  // end class Tree234App
