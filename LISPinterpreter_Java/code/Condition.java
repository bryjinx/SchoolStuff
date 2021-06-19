//package project2;

public class Condition {
	private String toParse;
	private PrefixEval evaler;
	
	public Condition(PrefixEval ev) {
		this.toParse = "";
		this.evaler = ev;
	}
	
	public String evalCond(String str) {
		this.toParse = str;
		int len = this.toParse.length();
		
		boolean xIsChar = false;
		boolean yIsChar = false;
		
		boolean condNotSet = true;
		boolean ifExpNotSet = true;
		boolean elseExpNotSet = true;
		
		String ifExp = "";
		int startIf = 0;
		int endIf = 0;
		
		String elseExp = "";
		int startElse = 0;
		int endElse = 0;
		
		String condOp = "";
		
		double x = 0;
		double y = 0;
		
		String strX = "";
		String strY = "";
		
		
		this.toParse = this.toParse.substring(1, len);
		String [] parse = this.toParse.split(" ");
		String [] tmp;
		
		// (if (> 10 20) (+ 1 1) (+ 3 3))

		for(int j = 0; j < parse.length; j++) {
			/**
			 *  GET OPERATOR AND FIRST OPERAND
			 */
			if (parse[j].contains("(") && condNotSet) {
				int num1_dex = j + 1;
		      
                //System.out.print("parse[" + j + "]: "+ parse[j] + "\n");
				tmp = parse[j].split("");
				
                /*for (int k = 0; k < tmp.length; k++) {
                    System.out.print("tmp[" + k +"]: " + tmp[k] + "\n");
                }*/
				/*if (tmp.length < 2) {
					return "-ISFAIL";
				}*/
				//System.out.print("tmp0: "+tmp[0] + "\ntmp1: " + tmp[1] + "\n" + tmp[3] + "\n");
				condOp = tmp[tmp.length - 1];
				//System.out.print("condOp " + condOp + "\n");
				//System.out.print("x: "+ Double.parseDouble(parse[num1_dex]) + "\n");
				try {
					x = Double.parseDouble(parse[num1_dex]);
				}
				catch (NumberFormatException e) {
					strX = parse[num1_dex];
					//System.out.print("CharX: "+ strX + "\n");
					xIsChar = true;
				}
			}
			/**
			 *  GET SECOND OPERAND
			 */
			else if (parse[j].contains(")") && condNotSet) {
				int num2_dex = j;
				//System.out.print("y: " + parse[num2_dex].substring(0, parse[num2_dex].length() - 1) + "\n");
				try {
					y = Double.parseDouble(parse[num2_dex].substring(0, parse[num2_dex].length() - 1));
				}
				catch (NumberFormatException e) {
					strY = parse[num2_dex];
					yIsChar = true;
				}
				condNotSet = false;
			}
			/**
			 *  	GET START INDX OF IF{}
			 */
			else if(parse[j].contains("(") && ifExpNotSet && elseExpNotSet && !condNotSet) {
				startIf = j;
				//System.out.print("IfExp start set: " + (j) + "\n");
			}
			/**
			 *  	GET END INDX OF IF{}
			 */
			else if(parse[j].contains(")") && ifExpNotSet && elseExpNotSet && !condNotSet) {
				endIf = j;
				//System.out.print("IfExp end set: " + j + "\n");
				if (endIf == 0 || startIf == 0) {
					System.out.print("Something's wrong with the conditional\n");
					return "-ISFAIL";
				}
				
				for(int k = startIf; k < (endIf + 1); k++) {
					ifExp = ifExp + " " + parse[k];
				}
				ifExp = ifExp.substring(2, ifExp.length() - 1);
				//System.out.print("ifExp: " + ifExp +"\n");
				ifExpNotSet = false;
			}
			/**
			 * 	GET START INDX OF ELSE{}
			 */
			else if(parse[j].contains("(") && elseExpNotSet && !ifExpNotSet && !condNotSet) {
				startElse = j;
				//System.out.print("ElseExp start set: " + j + "\n");
			}
			/**
			 *  GET END INDX OF ELSE{}
			 */
			else if (parse[j].contains(")") && elseExpNotSet  && !ifExpNotSet && !condNotSet) {
				endElse = j;
				//System.out.print("ElseExp end set: " + j + "\n");
				if (endElse == 0 || startElse == 0) {
					System.out.print("Something's wrong with the conditional\n");
					return "-ISFAIL";
				}
				for(int k = startElse; k < (endElse + 1); k++) {
					elseExp = elseExp + " " + parse[k];
				}
				elseExp = elseExp.substring(2, elseExp.length() - 1);
				//System.out.print("elseExp: " + elseExp + "\n");
				elseExpNotSet = false;
			}
			else if (!condNotSet && !ifExpNotSet && ! elseExpNotSet) {
				j = parse.length;
			}
		}
		if (ifExp.equals("") || elseExp.equals("")) {
			System.out.print("Something's wrong with the conditional\n");
			return "-ISFAIL";
		}
		//System.out.print("IfExp: "+ifExp+"\tElseExp: "+elseExp +"\n");
		if (xIsChar != yIsChar) {
			System.out.print("Numbers and characters cannot be compared!\n");
			return "-ISFAIL";
		}
		if (!xIsChar) {
			switch (condOp) {
			case ">":
				if (x > y) {
					evaler.setExp(ifExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
				else {
					evaler.setExp(elseExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
			case "<":
				if (x < y) {
					evaler.setExp(ifExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
				else {
					evaler.setExp(elseExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
			case "=":
				if (x == y) {
					evaler.setExp(ifExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
				else {
					evaler.setExp(elseExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
			default:
				System.out.print(condOp + " is not a conditional operator!\n");
				return "-ISFAIL";
			}
		}
		else {
			switch (condOp) {
			case ">":
				if (strX.compareTo(strY) < 0) {
					evaler.setExp(ifExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
				else {
					evaler.setExp(elseExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
			case "<":
				if (strX.compareTo(strY) > 0) {
					evaler.setExp(ifExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
				else {
					evaler.setExp(elseExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
			case "=":
				if (strX.compareTo(strY) == 0) {
					evaler.setExp(ifExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
				else {
					evaler.setExp(elseExp);
					double ans = evaler.evalExp();
					return ans + "";
				}
			default:
				System.out.print(condOp + " is not a conditional operator!\n");
				return "-ISFAIL";
			}
			
		}

		
	}
	
}

