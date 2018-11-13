package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom{

  AspExpr ex;

  AspInnerExpr(int n){
    super(n);
  }

  static AspInnerExpr parse(Scanner s){
    Main.log.enterParser("inner expr");
    AspInnerExpr ie = new AspInnerExpr(s.curLineNum());
    skip(s,leftParToken);
    ie.ex = AspExpr.parse(s);
    skip(s,rightParToken);
    Main.log.leaveParser("inner expr");
    return ie;
  }

  void prettyPrint(){
    Main.log.prettyWrite("(");
    ex.prettyPrint();
    Main.log.prettyWrite(")");
    //Main.log.prettyWriteLn();
    }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return ex.eval(curScope);
  }
}
