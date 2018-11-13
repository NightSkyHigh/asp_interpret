package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax{
  String op;
  AspFactorPrefix(int n) {
    super(n);
  }
  public static AspFactorPrefix parse(Scanner s) {
    AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
    Main.log.enterParser("factor prefix");

    switch(s.curToken().kind) {
      case plusToken:  afp.op = "+"; skip(s,plusToken); break;
      case minusToken: afp.op = "-"; skip(s,minusToken); break;
      default: afp.op = "none";
    }
    Main.log.leaveParser("factor prefix");
    return afp;
  }
  void prettyPrint() {
    Main.log.prettyWrite(op + " ");
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
