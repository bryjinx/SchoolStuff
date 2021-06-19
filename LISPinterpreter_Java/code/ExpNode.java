//package project2;

import java.util.ArrayList;

public class ExpNode {
	private String exp;
	private boolean hasChild;
	private ArrayList<ExpNode> childs = new ArrayList<ExpNode>();
	
	public ExpNode(String exp) {
		this.exp = exp;
		
		boolean childExp = false;
		int leftPar = 0;
		int rightPar = 0;
		int start = 0;
		int end = 0;
		
		if (exp.contains("(") || exp.contains(")")) {
			this.hasChild = true;
		}
		if (this.hasChild) {
			String[] tmp = this.exp.split("");
			String tmpexp = "";
			int len = tmp.length;
			
			for (int i = 0; i < len; i++) {
				if (!tmp[i].equals("(") && (!childExp)) {
					tmpexp = tmpexp + tmp[i];
				}
				else if (tmp[i].equals("(")){
					this.exp = tmpexp;
					if (!childExp) {
						start = i + 1;
					}
					childExp = true;
					leftPar++;
				}
				else if (tmp[i].equals(")")) {
					rightPar++;
					if (leftPar == rightPar) {
						end = i;
						//System.out.print("ExpNode: exp: " + exp + "\n");
						//System.out.print("ExpNode: child: " + exp.substring(start, end) + "\n");
						childs.add(new ExpNode(exp.substring(start, end)));
						start = 0;
						end = 0;
						leftPar = 0;
						rightPar = 0;
						childExp = false;
					}
				}
			}
		}
	}
	
	public ArrayList<ExpNode> getChilds() {
		return this.childs;
	}
	public String getExp() {
		return this.exp;
	}
	public void printExpNode() {
		int i = 0;
		System.out.println("this.exp: " + this.exp + "\n");
		for (ExpNode kid : this.childs) {
			System.out.print("child" + i + ": " + kid.getExp() + "\n");
			i++;
		}
	}
}
