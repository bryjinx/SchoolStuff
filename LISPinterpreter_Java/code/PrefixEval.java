//package project2;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.lang.Math;


public class PrefixEval {
	private String 				exp;
	private Stack<String> 		opStk;
	private Stack<Double>		opNumStk;
	private ArrayList<String> 	opsList;
	private SymbolHelper		symList;
	public boolean isFail;
	
	public PrefixEval() {
		this.opNumStk = new Stack<Double>();
		this.opStk = new Stack<String>();
		this.opsList = new ArrayList<String>();
	
		this.opsList.add("+");
		this.opsList.add("-");
		this.opsList.add("*");
		this.opsList.add("/");
		this.opsList.add("*");
		
		this.isFail = false;
		
	}
	
	public PrefixEval(String n) {
		this.exp = n;
		this.opNumStk = new Stack<Double>();
		this.opStk = new Stack<String>();
		this.opsList = new ArrayList<String>();
		
		this.opsList.add("+");
		this.opsList.add("-");
		this.opsList.add("*");
		this.opsList.add("/");
		this.opsList.add("*");
		
		this.isFail = false;
	}
	/*Setter for symList*/
	public void setSymList(SymbolHelper symList) {
		this.symList = symList;
	}
	/*Getter for OpsList for IsMathExp class*/
	public ArrayList<String> getOpsList() {
		return this.opsList;
	}
	/*Expression setter for parser*/
	public void setExp(String exp) {
		this.exp = exp;
	}
	
	private boolean isNumeric(String str) {
		return str.matches("[-+]?\\d*\\.?\\d+");  
	}
	
	public double evalExp() {
		//System.out.print("Evaling Exp: " + this.exp + "\n");
		double ans = 0;
		double x;
		double y;
		double z;
		int i = 0;
		String tmpexp[] = this.exp.split(" ");
		int len = tmpexp.length;
		String isVal;
		
		
		try {
			//System.out.print(exp + "\n");
			while(i < len) {
				//split exp up: get element i of String[] -- should be number or operator
				
				String tmp = tmpexp[i];
				isVal = symList.getVal(tmp);
				
				//System.out.print(tmp + "\n");
				// if tmp is operand
				if (tmp.contains("sin") || tmp.contains("cos") || tmp.contains("tan") || tmp.contains("sqrt") || tmp.contains("pow")) {
					if (tmp.contains("sin")) {
						tmp = tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(")"));
						isVal = symList.getVal(tmp);
						if (!isVal.equals("-NOVAL")) {
							tmp = isVal;
						}
						return Math.sin(Double.parseDouble(tmp));
					}	
					else if (tmp.contains("cos")) {
							tmp = tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(")"));
							isVal = symList.getVal(tmp);
							if (!isVal.equals("-NOVAL")) {
								tmp = isVal;
							}
							return Math.cos(Double.parseDouble(tmp));
						
					}
					else if (tmp.contains("tan")) {
							tmp = tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(")"));
							isVal = symList.getVal(tmp);
							if (!isVal.equals("-NOVAL")) {
								tmp = isVal;
							}
							return Math.tan(Double.parseDouble(tmp));
					}
					else if (tmp.contains("sqrt")) {
						tmp = tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(")"));
						isVal = symList.getVal(tmp);
						if (!isVal.equals("-NOVAL")) {
							tmp = isVal;
						}
						return Math.sqrt(Double.parseDouble(tmp));
					}
					else if (tmp.contains("pow")) {
						tmp = tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(")"));
						String[] tmptmp = tmp.split(",");
						if (tmptmp.length != 2) {
							System.out.print("Not enough arguments for POW\n");
							this.isFail = true;
							return 0;
						}
						else {
							return Math.pow(Double.parseDouble(tmptmp[0]), Double.parseDouble(tmptmp[1]));
						}
					}
				}
				else if ((this.isNumeric(tmp) || (!isVal.equals("-NOVAL")))) {
					if (!isVal.equals("-NOVAL")) {
						tmp = isVal;
					}
					//add to opNumStk
					opNumStk.push(Double.parseDouble(tmp));
					
					//if opNumStk has 2
					if (opNumStk.size() == 2) {
						//pop opStk -- OP
						if (!opStk.empty()) {
							String op = opStk.pop();
							
							//pop (*2) opNumStk -- y, x
							y = opNumStk.pop();
							x = opNumStk.pop();
							
							//DO: z = x OP y
							switch(op) {
							case "+":
								z = x + y;
								break;
							case "-":
								z = x - y;
								break;
							case "*":
								z = x * y;
								break;
							case "/":
								z = x / y;
								break;
							case "%":
								z = x % y;
								break;
							default:
								System.out.println("Not a valid operator!!\n");
								this.isFail = true;
								return 0;
			
								
							}
							//add z to opNumStk
							opNumStk.push(z);
						}
						else {
							System.out.println("The prefix notation for the expression is incorrect!!\n");
							this.isFail = true;
							return 0;
						}
					}
				}
				//if op
				else if (opsList.contains(tmp)){
					
					//add to opStk
					opStk.push(tmp);
				}	
				i = i + 1;
			}
		}
		catch (EmptyStackException e) {
			System.out.print("Something is wrong with the prefix notation expression\n");
		}
		//if opNumStk has > 1 number then the exp was incomplete
		if (opNumStk.size() > 1) {
			//throw error, return to prompt
			System.out.println("The prefix notation for the expression is incorrect!!\n");
			this.isFail = true;
			return 0;
		}
		try {
			ans = opNumStk.pop();
		}
		catch (EmptyStackException e) {
			System.out.print("Something is wrong with the prefix notation expression\n");
			this.isFail = true;
			return 0;
		}
		return ans;

	}
	

}
