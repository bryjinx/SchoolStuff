//package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LispInterMain {

	public static void main(String[] args) throws FileNotFoundException {
		Boolean condition = true;
		String toParse;
		
		System.out.println("_______________________________________________\n_______________________________________________\nWelcome to Bry's Fancy Lisp Interpreter!\n_______________________________________________\n_______________________________________________\n This is the submission version of the program.\nType \"(help)\" for more information\n_______________________________________________\n");
		Scanner scan = new Scanner(System. in);
		Parser parse = new Parser();
		
		System.out.println("---Do you want to use File Mode?\n---\t\t<yes: (y)   no: any other input>\n---WARNING: File Mode can't be accessed again until the program is restarted\n >>");
		toParse = scan.nextLine();
		/**
		 *  			FILE MODE
		 */
		//use default file lispfood.txt
		if (toParse.equals("(y)")) {
			System.out.println("Commencing File Mode\nNOTE: input files must have one LISP expression per line, with the last line of the file being \"(quit)\" or \"(exit)\"\n");
			System.out.println("Either enter \"(default)\" to use the default test file or enter the name of your file\n An output file named \"output.txt\" will be created/overwritten \n");
			toParse = scan.nextLine();
			if (toParse.equals("(default)")) {
				//use default file testcases.txt
				File file = new File("testcases.txt");
				if (!file.exists()) {
					System.out.print("You must have managed to delete the default file.\n Goodbye\n");
					scan.close();
					return;
				}
				Scanner in = new Scanner(file);
				PrintWriter writer = new PrintWriter("output.txt");
				
				while (in.hasNextLine()) {
					toParse = in.nextLine();
					//writer.print(toParse + " <<outputs>> ");
					ParenthCheck parenth = new ParenthCheck(toParse);
					
					//first check if parenthesis are balanced
					if (parenth.isBalanced()) { 
						//call Parser
						String ans = parse.parseMe(toParse);
						writer.println(ans + "\n");
						
					}
					else {
						System.out.print("Incorrect expression: mismatched or lack of parenthesis\n");
					}
			
				}
				System.out.print("output.txt should be ready to look at. Goodbye!\n");
				in.close();
				writer.close();
				scan.close();
			}
			//not default file
			else {
				File file = new File(toParse);
				if (!file.exists()) {
					System.out.print("Well that file's not there\n Goodbye\n");
					scan.close();
					return;
				}
				Scanner in = new Scanner(file);
				PrintWriter writer = new PrintWriter("output.txt");
				
				while (in.hasNextLine()) {
					toParse = in.nextLine();
                    //writer.print(toParse + " <<outputs>> ");
					ParenthCheck parenth = new ParenthCheck(toParse);
					
					//first check if parenthesis are balanced
					if (parenth.isBalanced()) { 
						//call Parser
						String ans = parse.parseMe(toParse);
						writer.println(ans + "\n");
						
					}
					else {
						System.out.print("Incorrect expression: mismatched or lack of parenthesis\n");
					}
			
				}
				System.out.print("output.txt should be ready to look at. Goodbye!\n");
				in.close();
				writer.close();
				scan.close();
			}
			
		}
		/**
		 *   		COMMAND LINE MODE
		 */
		else {
			System.out.println("Commencing Command Line Mode\n");
			while (condition) {
				System.out.println(">> ");
				toParse = scan.nextLine();
				
				ParenthCheck parenth = new ParenthCheck(toParse);
				
				//first check if parenthesis are balanced
				if (parenth.isBalanced()) { 
					//call Parser
					String ans = parse.parseMe(toParse);
					if (!parse.goLoop) {
						condition = false;
					}
					if (!ans.equals("-ISFAIL")) {
						System.out.print(ans + "\n");
					}
					else {
						System.out.print("Something failed!!\n");
					}
				}
				else {
					System.out.print("Incorrect expression: mismatched or lack of parenthesis\n");
				}
			}
		}
		scan.close();
	}
}
