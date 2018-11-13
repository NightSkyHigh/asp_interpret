package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {
  ArrayList<AspStmt> stmtList = new ArrayList<AspStmt>();
  AspSuite(int n) {
    super(n);
  }
  public static AspSuite parse(Scanner s) {
        Main.log.enterParser("suite");
        AspSuite as = new AspSuite(s.curLineNum());
        skip(s, newLineToken);
        skip(s, indentToken);
        while(s.curToken().kind != dedentToken) {
          as.stmtList.add(AspStmt.parse(s));
        }
        skip(s, dedentToken);
        Main.log.leaveParser("suite");
        return as;
  }

  void prettyPrint() {
    for(AspStmt s : stmtList) {
      s.prettyPrint();
    }
  }

  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> temp = new ArrayList<RuntimeValue>();
    for(AspStmt s : stmtList) {
      temp.add(s.eval(curScope));
    }
    return new RuntimeListValue(temp);
  }
}
