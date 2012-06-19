//Encapsulate Data
//


//XXX NOT LONG
class Data {
	private long dData;       //XXX
	public Data(long dd)  {
		dData = dd;
	}

	public long getData() {
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
