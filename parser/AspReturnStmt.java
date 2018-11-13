package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturnStmt extends AspStmt{
    AspExpr test;
    AspReturnStmt(int n){
      super(n);
    }
    public static AspReturnStmt parse(Scanner s) {
    Main.log.enterParser("return stmt");

    AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
    skip(s, returnToken);
    ars.test = AspExpr.parse(s);
    skip(s, newLineToken);

    Main.log.leaveParser("return stmt");
    return ars;
    }

    void prettyPrint(){
      Main.log.prettyWrite("return ");
      test.prettyPrint();
      Main.log.prettyWriteLn();
    }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = test.eval(curScope);
    trace("return "+v.showInfo());
    throw new RuntimeReturnValue(v);
  }
}
