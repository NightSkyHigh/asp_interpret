package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSubscription extends AspPrimarySuffix{
  AspExpr test;
  AspSubscription(int n) {
    super(n);
  }
  public static AspSubscription parse(Scanner s) {
    Main.log.enterParser("subscription");
    AspSubscription as = new AspSubscription(s.curLineNum());
    skip(s, leftBracketToken);
    as.test = AspExpr.parse(s);
    skip(s, rightBracketToken);
    Main.log.leaveParser("subscription");
    return as;
  }
  void prettyPrint() {
    Main.log.prettyWrite("[");
    test.prettyPrint();
    Main.log.prettyWrite("]");
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return test.eval(curScope);
  }
}
