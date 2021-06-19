//package project2;

public class ParenthCheck {
	private String 		  parseMe;
	private int left;
	private int right;
	
	public ParenthCheck(String n) {
		this.parseMe = n;
	}
	
	boolean isBalanced() {
		String[] tmpParse = this.parseMe.split("");
		String tmp;
		int i = 0;
		int len = tmpParse.length;
		
		while (i < len) {
			tmp = tmpParse[i];
			
			if (tmp.contains("(")) {
				left++;
			}
			else if (tmp.contains(")")) {
				right++;
			}
			i++;
		}
		
		if (left != right || left == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
