package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    //private int indents[] = new int[100];
    private int numIndents = 0;
    private Stack<Integer> indents = new Stack<Integer>();
    private final int tabDist = 4;


  public Scanner(String fileName) {
	   curFileName = fileName;
	    //indents[0] = 0;
      numIndents = 1;
      indents.push(0);
	     try {
	        sourceFile = new LineNumberReader(
			       new InputStreamReader(
				         new FileInputStream(fileName),
				             "UTF-8"));
	     } catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
}


  private void scannerError(String message) {
	   String m = "Asp scanner error";
	    if (curLineNum() > 0)
	      m += " on line " + curLineNum();
	       m += ": " + message;

	Main.error(m);
    }

  public Token curToken() {
	   while (curLineTokens.isEmpty()) {
	    readNextLine();
	   }
	 return curLineTokens.get(0);
  }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }

public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	}
	return false;
    }

  public void indentHanlder(String line){
    int n = 0;
    indents.push(n);
  }

private void readNextLine() {
	curLineTokens.clear();

	// Read the next line:
	String line = null;
	try {
	    line = sourceFile.readLine();
	    if (line == null) {
		    sourceFile.close();
		    sourceFile = null;
	    } else {
		    Main.log.noteSourceLine(curLineNum(), line);
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}

	//-- Must be changed in part 1:
  if(line == null) {
    while(0 < indents.peek()) {
      indents.pop();
      curLineTokens.add(new Token(dedentToken, curLineNum()));
    }
    curLineTokens.add(new Token(eofToken, curLineNum()));
    for (Token t: curLineTokens) {
        Main.log.noteToken(t);
      }
    return;
  }
  //Converts tabs to spaces
  line = expandLeadingTabs(line);
  //If the line is empty
  if (findIndent(line) == line.length()) {
    return;
  }
  if(line.length() >= (findIndent(line))) {
    if(line.charAt(findIndent(line)) == '#') {
      return;
    }
  }

  /* Adds an indent token if the indentation of the current line is greater
  * than the previous line indentation */
  if(findIndent(line) > indents.peek()) {
    indents.push(findIndent(line));
    curLineTokens.add(new Token(indentToken, curLineNum()));
  }
  /* Adds an dedent token if the indentation of the current line is lesser
  * than the previous line indentation */
  while(findIndent(line) < indents.peek()) {
    indents.pop();
    if (findIndent(line) <= indents.peek()) {
        curLineTokens.add(new Token(dedentToken, curLineNum()));
    /* Throws an error if the current indentation is lesser than the current
    * indentation but greater than the previous level */
    } else if(findIndent(line) > indents.peek()) {
        scannerError("Indentation error!");
    }
  }

  char[] lineChar = line.toCharArray();
  String name;

    //j is the current index in lineChar[]
    for(int j = 0; j < lineChar.length ; j++) {
      name = "";
      //Comments
      if(lineChar[j] == '#') {
        break;
      }
      //stringLit
      /* Both " and ' is a valid way of defining a string literal. The program
      * commits to the symbol used to start the literal. So if a literal is
      * started by " it needs to close by " */

      //In case of ""
      else if(lineChar[j] == '"') {
        j++;
        while(lineChar[j] !=  '"') {
          if (j < lineChar.length - 1) {
            name += lineChar[j];
            lineChar[j] = ' ';
            j++;
          } else {
            //If the line ends before the string literal is terminated
            scannerError("String literal not terminated!");
            return;
          }
        }
        Token tk = new Token(stringToken, curLineNum());
        tk.stringLit = name;
        curLineTokens.add(tk);
      }
      //In case of ''
      else if(lineChar[j] == '\''){
        j++;
        while(lineChar[j] != '\'') {
          if (j < lineChar.length - 1) {
            name += lineChar[j];
            lineChar[j] = ' ';
            j++;
          } else {
            //If the line ends before the string literal is terminated
            scannerError("String literal not terminated!");
            return;
          }
        }
        Token tk = new Token(stringToken, curLineNum());
        tk.stringLit = name;
        curLineTokens.add(tk);
      }
      //checks if line contains a digit
      else if(isDigit(lineChar[j])) {
        name += lineChar[j];
        j++;
          while(j < lineChar.length && (isDigit(lineChar[j]) || lineChar[j] == '.')) {
          name += lineChar[j];
          lineChar[j] = ' ';
          j++;
        }
        j--;
        // cehcks if "name" (a number) contains the char '.', if so it is a float
        if(name.indexOf('.') >= 0){
          Token t = new Token(floatToken, curLineNum());
          t.floatLit = Double.parseDouble(name);
          curLineTokens.add(t);
          //if it does not contain a '.', then it is an integer
        } else {
          Token tk = new Token(integerToken, curLineNum());
          tk.integerLit = Long.parseLong(name);
          curLineTokens.add(tk);
        }
      }
      //Name tokens and keywords
      else if(isLetterAZ(lineChar[j])) {
        boolean keyWord = false;
        while(j < lineChar.length && (isLetterAZ(lineChar[j]) || isDigit(lineChar[j])) && !keyWord) {
          name += lineChar[j];
          //goes through keyword tokens to see if name contains a keyword
          for (TokenKind tk: EnumSet.range(andToken, yieldToken)) {
          if (name.equals(tk.image)) {
                if(j < (lineChar.length - 1)) {
                if(isLetterAZ(lineChar[j + 1]) || isDigit(lineChar[j + 1])) {
                  break;
                }
              }
                curLineTokens.add(new Token(tk, curLineNum()));
                keyWord = true;
                break;
              }
            }
            j++;
          }
          j--;
          if(!keyWord) {
            Token tk = new Token(nameToken, curLineNum());
            tk.name = name;
            curLineTokens.add(tk);
          }
        }
        //if non of the above, it checks for Operators
        else {
          name = "";
          name += lineChar[j];
          nameTokenLoop:
          for (TokenKind tk: EnumSet.range(ampToken, slashEqualToken)) {
              if (name.equals(tk.image) || name.equals("!")) {
                int antFelter = 3;
                //checks how many Chars are left in "lineChar"
                if(lineChar.length - j < 3) {
                  antFelter = lineChar.length - j;
                }
                for(int k = antFelter; k > 0; k--) {
                  name = "";
                  for(int l = 0; l < k; l++) {
                    if(j + l < lineChar.length) {
                      name += lineChar[j + l];
                  }
                }
                  for (TokenKind tok: EnumSet.range(ampToken, slashEqualToken)) {
                    if (name.equals(tok.image)) {
                      j += name.length() - 1;
                      curLineTokens.add(new Token(tok, curLineNum()));
                      break nameTokenLoop;
                  }
                }
              }
            }
          }
        }
      }


	// Terminate line:
	curLineTokens.add(new Token(newLineToken, curLineNum()));

	for (Token t: curLineTokens) {
	    Main.log.noteToken(t);
    }
  }

public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }

    private String expandLeadingTabs(String s) {
	String newS = "";
	for (int i = 0;  i < s.length();  i++) {
	    char c = s.charAt(i);
	    if (c == '\t') {
		do {
		    newS += " ";
		} while (newS.length()%tabDist != 0);
	    } else if (c == ' ') {
		newS += " ";
	    } else {
		newS += s.substring(i);
		break;
	    }
	}
	return newS;
    }


private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }


public boolean isCompOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


public boolean isFactorPrefix() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


public boolean isFactorOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


public boolean isTermOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:

	return false;
    }
}
