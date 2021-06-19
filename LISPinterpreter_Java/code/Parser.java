//package project2;

public class Parser {
	
	public Boolean goLoop = true;
	private SymbolHelper symList;
	private IsMathExp 	isExp;
	private PrefixEval  evaler;
	private LambdaHelper lambdas;
	
    
	public Parser() {
		this.symList = new SymbolHelper();
		this.evaler = new PrefixEval();
		this.isExp = new IsMathExp(this.evaler.getOpsList());
		this.lambdas = new LambdaHelper(this.evaler);
	}
	
	public String parseMe(String toParse) {

		int len = toParse.length();
		//tmpparse is with shaved off parenthesis
		evaler.setSymList(this.symList);
		String tmpParse = toParse.substring(1,len - 1);
		String isVal = this.symList.getVal(tmpParse);
		
		/**
		 *  		QUITTING
		 */
		if (toParse.contains("exit") || toParse.contains("quit")) {
			//System.out.println("Goodbye!\n");
			goLoop = false;
			return "Goodbye!";
			
		}
		/**
		 *		 A RECURSIVE CALL FAILED
		 */
		else if (toParse.contains("-ISFAIL")) {
			return "-ISFAIL";
		}
		/**
		 * 		USER NEEDS HELP
		 */
		else if(!toParse.contains("quote") && (toParse.contains("help"))) {
			
			return "   This is the final submission version of this program.\n   To quit type '(quit)' or '(exit)'\n\n   DEFINE a variable: '(define <var> <number>)'\n\n   VAR REFERENCE for any defined variable x: \n      '(x)' will return the value\n\n   SET!: '(set! <var> <exp>)'\n      Evaluates the expression and sets <var> to the answer\n\n   COND: '(if (<cond>) (<Exp1>) (<Exp1>))'\n      returns Exp1 if <cond> is True and Exp2 if <cond> is False\n\n   QUOTE: '(quote <string>)'    like Linux's echo, just returns <string>\n\n   BUILT-IN FUNCTIONS: sin, cos, tan, sqrt\n\n   DEFINE functions: '(lambda <name> (<parameters>) (<expression>))'\n\n   POLISH NOTATION MATH:\n\tNOTE: Nested math expressions should NEVER HAVE a non-nested expression\n       AFTER the nested expression for it to be evaluated correctly\n   GOOD: (+ + 6 (+ 2 2) (+ 2 3))\n   GOOD: (+ + 4 6 (+ 2 3))\n   GOOD: (+ (+ 2 2) 4 (+ 2 2))\n   BAD: (+ + (+ 2 2) (+ 2 3) 6)\n";
		}
		/** 
		 * 		SET!
		 */
		else if (!toParse.contains("quote") && toParse.contains("set!")) {
			toParse = toParse.substring(1, len - 1);
			String [] parse = toParse.split(" ");
			//System.out.print(toParse + "\n");
			String tmpexp = "";
			//int n = 0;
			for (int j = 2; j < parse.length; j++) {
				tmpexp = tmpexp + " " + parse[j];
				//System.out.print("parse[]: " + parse[n] + "\n");
			}
			//System.out.print(tmpexp);
			tmpexp = tmpexp.substring(2, tmpexp.length() - 1);
			
			evaler.setExp(tmpexp);
			parse[2] = evaler.evalExp() + "";
			this.symList.addPair(parse[1], parse[2]);
			return "";
		}
		/**
		 *  	DEFINING USER GIVEN LAMBDA FUNCTIONS 
		 */
		//(lambda ADD2 (x y z) (+ (+ x y) z))
		else if (!toParse.contains("quote") && toParse.contains("lambda") || toParse.contains("LAMBDA")) {
			toParse = toParse.substring(1, len - 1);
			lambdas.addLambda(toParse);
			return "";
		}
		/**
		 *  	EVALUATING USER GIVEN LAMBDA FUNCTIONS
		 */
		else if (!toParse.contains("quote") && lambdas.isDefined(toParse.substring(1, toParse.length() - 1))) {
			String ans = lambdas.evalLambda(toParse.substring(1, toParse.length() - 1), this);
			return ans;
		}
		/**
		 * 		CONDITIONAL - "IF"
		 */
		else if (!toParse.contains("quote") && toParse.contains("if")) {
			toParse = toParse.substring(1, len - 1);
			Condition cond = new Condition(evaler);
			String answer = cond.evalCond(toParse);
			return answer;
		}
		/**
		 * 		RECURSIVE STEP
		 */
		else if (!toParse.contains("quote") && !this.isExp.hasBuiltInOp(tmpParse) && (tmpParse.contains("(") || tmpParse.contains(")"))) {
			//System.out.print("gets to recursive step\n");
			ExpNode tree = new ExpNode(tmpParse);
			BuildExp build = new BuildExp(tree);
			String built = build.buildExp(this);
			//System.out.print("Parser: built: " + built + "\n");
			return parseMe(built);
		}
		/**
		 * 		MATH MATH MATH
		 */
		else if(!toParse.contains("quote") && this.isExp.isMathExp(tmpParse) || this.isExp.hasBuiltInOp(tmpParse)) {
			//System.out.print(tmpParse + "\n");
			evaler.setExp(tmpParse);
			double ans = evaler.evalExp();
			if (!evaler.isFail) {
				//System.out.print(ans + "\n");
				return ans + "";
			}
			else {
				return "-ISFAIL";
			}
			
		}
		/**
		 * 		VALUE IS STORED TO A KEY AND NEEDS TO BE RETURNED
		 */
		else if(isVal != "-NOVAL") {
			//System.out.print(isVal + "\n");
			return isVal;
			
		}
		/**
		 *  OTHER CASES- 
		 *  		QUOTE, DEFINE, LAMBDA
		 */
		else {
			toParse = toParse.substring(1, len - 1);
			//System.out.print(toParse + "\n");
			String [] parse = toParse.split(" ");
			
			switch(parse[0]) {
			case "quote":
				//is "echo" basically
				//System.out.println(parse[1]);
				String quoted = "";
				for(int k = 1; k < parse.length; k++) {
					quoted = quoted + " " + parse[k];
				}
				return quoted;
			case "define":
				//System.out.println("Evaluating variable definition\n");
				if (parse.length < 3) {
					System.out.println("Incomplete expression! Doing nothing\n");
					return "-ISFAIL";
				}
				this.symList.addPair(parse[1], parse[2]);
				//System.out.println("var " + parse[1] + " assigned to value " + parse[2]+"\n");
				return "";
			default:
				System.out.println("No keywords identified! Doing nothing\n");
				return "-ISFAIL";
			}
			
		}
	
	}

}
