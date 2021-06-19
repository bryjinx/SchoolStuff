//package project2;

import java.util.ArrayList;

public class IsMathExp {
	
	private ArrayList<String> opList;
	
	public IsMathExp(ArrayList<String> opList) {
		this.opList = opList;
	}
	
	public boolean isMathExp(String parse) {
		for (String op : this.opList) {
			if (parse.contains(op)) {
				return true;
			}
		}
		return false;
	}


	public boolean hasBuiltInOp(String parse) {
		if (parse.contains("sin") || parse.contains("cos") || parse.contains("tan") || parse.contains("sqrt") || parse.contains("pow")) {
			return true;
		}
		return false;
	}
}
