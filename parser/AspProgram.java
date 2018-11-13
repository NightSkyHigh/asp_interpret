package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    //-- Must be changed in part 2:
     ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	    super(n);
    }


    public static AspProgram parse(Scanner s) {
	Main.log.enterParser("program");

	AspProgram ap = new AspProgram(s.curLineNum());
	while (s.curToken().kind != eofToken) {
	    //-- Must be changed in part 2:
	     ap.stmts.add(AspStmt.parse(s));
	}

	Main.log.leaveParser("program");
	return ap;
    }



    public void prettyPrint() {
      for(AspStmt s : stmts){
        s.prettyPrint();
      }
    }

  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
  ArrayList<RuntimeValue> temp = new ArrayList<RuntimeValue>();
  try{
  for (AspStmt s:stmts) {
    temp.add(s.eval(curScope));
  }
}catch(RuntimeReturnValue r){
  trace("something went wrong");
  return null;
}
	return new RuntimeListValue(temp);
  }
}
