package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExprStmt extends AspStmt{
  AspExpr test;

  AspExprStmt(int n) {
    super(n);
  }
  public static AspExprStmt parse(Scanner s) {
    Main.log.enterParser("expr stmt");

    AspExprStmt aes = new AspExprStmt(s.curLineNum());
    aes.test = AspExpr.parse(s);
    skip(s, newLineToken);

    Main.log.leaveParser("expr stmt");
    return aes;
  }
  void prettyPrint() {
    test.prettyPrint();
    Main.log.prettyWriteLn();
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return test.eval(curScope);
}
}
