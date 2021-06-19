//package project2;

public class BuildExp {
	private ExpNode node;
	
	public BuildExp (ExpNode node) {
		this.node = node;
	}
	
	public String buildExp(Parser parse) {
		String tmp = this.node.getExp();
		for (ExpNode kid : this.node.getChilds()) {
			//System.out.print("BuildExp: in loop: " + kid.getExp() + "\n");
			tmp = tmp + " " + parse.parseMe("("+kid.getExp()+")");
			//System.out.print("BuildExp: in loop:" + tmp + "\n");
		}
		//System.out.print("BuildExp: (" + tmp + "\n");
		return ("(" + tmp + ")");
	}

	
}