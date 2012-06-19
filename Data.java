//Encapsulate Data
//


//XXX NOT LONG
class Data {
	private Object dData;       //XXX
	public Data(Object dd)  { //Long
		dData = dd;
	}

	public Object getData() {
		return dData;
	}
	public void displayItem()   // display item, format "/27"
    { System.out.print("/"+dData); }	
}


//public class Data<T> {
	//T data;
	//public void DataItem(T d) {
		//data = d;
	//}

	//public T getData() {
		//return data;
	//}

	////XXX
	 ////public void displayItem()   // display item, format "/27"
     ////{ System.out.print("/"+dData); }	
	
//}
