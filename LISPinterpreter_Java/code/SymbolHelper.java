//package project2;

import java.util.ArrayList;


public class SymbolHelper { 
	
	private ArrayList<KeyVal> symbs;
	
	public SymbolHelper() {
		this.symbs = new ArrayList<KeyVal>();
	}
	
	void addPair(String n, String m) {
		boolean addIt = true;
		for (KeyVal pair : this.symbs) {
			if (n.equals(pair.key)) {
				addIt = false;
				pair.value = m;
			}
		}
		if (addIt) {
			KeyVal pair = new KeyVal(n,m);
			this.symbs.add(pair);
		}
		
	}
	
	public String getVal(String key) {
		for (KeyVal item : this.symbs) {
			//System.out.print("in GetVal: in for loop\n");
			//System.out.print(key + " " + item.key +"\n");
			if (key.equals(item.key)) {
				return item.value;
			}
		}
		return "-NOVAL";
	}
	
	public void printList() {
		System.out.println(this.symbs);
	}

}
