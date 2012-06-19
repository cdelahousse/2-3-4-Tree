import java.io.*;
import java.util.Comparator;
class Tree234App
   {
   public static <T> void main(String[] args) throws IOException {



     //System.out.println(
		long value;
		TwoFourTree theTree = new TwoFourTree();
		

		
		

		
		tinsert(theTree);
      

		theTree.displayTree();
		System.out.println("");


		return;
		
		//while(true)
			 //{
			 //System.out.print("Enter first letter of ");
			 //System.out.print("show, insert, or find: ");
			 //char choice = getChar();
			 //switch(choice)
					//{
					//case 's':
						 //theTree.displayTree();
						 //break;
					//case 'i':
						 //System.out.print("Enter value to insert: ");
						 //value = getInt();
						 //theTree.insert(value);
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
   
   public static void tinsert2(TwoFourTree t) {
	   
   }
   
   public static void tinsert(TwoFourTree t) {

      t.insert(new ta(50));
      t.insert(new ta(40));
      t.insert(new ta(41));
      t.insert(new ta(72));
      t.insert(new ta(73));
      t.insert(new ta(45));
      t.insert(new ta(42));
      t.insert(new ta(49));
      t.insert(new ta(34));
      t.insert(new ta(60));
      t.insert(new ta(61));
      t.insert(new ta(62));
      t.insert(new ta(31));
      t.insert(new ta(44));
      t.insert(new ta(30));
      t.insert(new ta(66));
      t.insert(new ta(32));
      t.insert(new ta(70));
      t.insert(new ta(71));
      t.insert(new ta(67));
	 }
   //Do not allow doubles
   public static void tinsertDoubles(TwoFourTree t) {

      t.insert(50);
      t.insert(40);
      t.insert(40);
      t.insert(60);
      t.insert(30);
      t.insert(70);
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
