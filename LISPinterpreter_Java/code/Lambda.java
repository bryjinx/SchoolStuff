//package project2;

import java.util.ArrayList;

public class Lambda {
	private String preParse;
	public ArrayList<Character> params;
	public String name;
	public String exp;
	public int numPars;
	
	
	public Lambda(String toParse) {
		this.preParse = toParse;
		params = new ArrayList<Character>();
		this.makeLambda();
	}
	
	public void makeLambda() {
		// lambda ADD2 (x y z) (+ (+ x y) z)
		boolean paramsNotSet = true;
		boolean expNotSet = true;
		boolean startNotSet = true;
		
		String params = "";
		String exp = "";
		
		int start = 0;
		int end = 0;
		
		//System.out.println("Preparse: " + this.preParse + "\n");
		String [] tmp = this.preParse.split(" ");
		
		this.name = tmp[1];
		for(int i = 0; i < tmp.length; i++) {
			//System.out.print("tmp[" + i + "]: " + tmp[i] + "\n");
			if (tmp[i].contains("(") && paramsNotSet && expNotSet) {
				start = i;
			}
			else if (tmp[i].contains(")") && paramsNotSet && expNotSet) {
				end = i;
				if (start == 0 || end == 0) {
					System.out.print("Lamdba function defining input is incorrect\n");
					return;
				}
				for (int j = start; j <= end; j++) {
					params = params + " " + tmp[j];
				}
				for (int j = 0; j < params.length(); j++) {
					//System.out.print("[" + j + "]: " + params.charAt(j) + "\n");
					if(Character.isLetter(params.charAt(j))) {
						this.params.add(params.charAt(j));
					}
				}
				this.numPars = this.params.size();
				//System.out.print("numPars: " + this.numPars + "\n");
				paramsNotSet = false;
			}
			else if (tmp[i].contains("(") && !paramsNotSet && expNotSet && startNotSet) {
				start = i;
				startNotSet = false;
				//System.out.print("exp start: " + i + "\n");
			}
		}
		for (int j = start; j < tmp.length; j++) {
			exp = exp + " " + tmp[j];
		}
		this.exp = exp;
		//System.out.print("Exp: " + this.exp + "\n");
		expNotSet = false;
	}
}
