//package project2;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LambdaHelper {
	private ArrayList<Lambda> funcs;
	PrefixEval evaler;
	
	public LambdaHelper(PrefixEval ev) {
		this.funcs = new ArrayList<Lambda>();
		this.evaler = ev;
	}
	
	public void addLambda(String rawinput) {
		this.funcs.add(new Lambda(rawinput));
	}
	
	public boolean isDefined(String rawinput) {
		//System.out.print("in isDefined\n");
		String tmp[] = rawinput.split("[ ()]");
		for (Lambda func : this.funcs) {
			if (func.name.equals(tmp[0])) {
				return true;
			}
		}
		return false;
	}
	public String evalLambda(String rawinput, Parser parse) {
		Lambda tmpLamb = null;
		//System.out.print("in evalLambda\n");
		if (this.isDefined(rawinput)) {
            // (add2(2 2 2))
			// ADD2 2 2 2
			String tmp[] = rawinput.split("[ ()]");
			for (Lambda func : this.funcs) {
				if (func.name.equals(tmp[0])) {
					tmpLamb = func;
					//System.out.print(tmpLamb.name + "\n");
					break;
				}
			}
			/*for (int i = 0; i < tmp.length; i++) {
				System.out.print("tmp[" + i + "]: " + tmp[i] + "\n");
			}*/
			
			// (lambda add2 (x y z) (+ z (+ x y)))
			
			//System.out.print("Lambda: "+tmpLamb.numPars + "\nInp: " + (tmp.length - 1) + "\n");
			String tmpExp = "";
			//System.out.print("Before replace: " + tmpLamb.exp + "\n");
			tmpExp = tmpLamb.exp;
			for (int i = 0; i < tmpLamb.numPars; i++) {
				//System.out.print("Replace: " + tmpLamb.params.get(i).toString() +" with : " + tmp[i + 1] + "\n");
				tmpExp = tmpExp.replaceAll(tmpLamb.params.get(i).toString(), tmp[i + 1]);
				//System.out.print(">>: " + tmpExp + "\n");
			}
			tmpExp = tmpExp.substring(2, tmpExp.length() - 1);
			//System.out.print("After shave/replace: " + tmpExp + "\n");
			ExpNode tree = new ExpNode(tmpExp);
			BuildExp build = new BuildExp(tree);
			String built = build.buildExp(parse);
			built = built.substring(1, built.length() - 1);
			//System.out.print("Built: " + built + "\n");
			evaler.setExp(built);
			return evaler.evalExp() + "";
		}
		else {
			System.out.print("Function is not defined!\n");
			return "-ISFAIL";
		}
	}
}
