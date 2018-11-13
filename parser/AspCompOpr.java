package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspCompOpr extends AspSyntax{
  String op;
  AspCompOpr(int n) {
    super(n);
  }
  public static AspCompOpr parse(Scanner s) {
    Main.log.enterParser("comp opr");
    AspCompOpr aco = new AspCompOpr(s.curLineNum());
    switch(s.curToken().kind) {
      case greaterToken: skip(s, greaterToken); aco.op = ">"; break;
      case lessToken: skip(s,lessToken); aco.op = "<"; break;
      case doubleEqualToken: skip(s,doubleEqualToken); aco.op = "=="; break;
      case greaterEqualToken: skip(s,greaterEqualToken); aco.op = ">="; break;
      case lessEqualToken: skip(s,lessEqualToken); aco.op = "<="; break;
      case notEqualToken: skip(s,notEqualToken); aco.op = "!="; break;
      default: aco.op= "none";
      }
      Main.log.leaveParser("comp opr");
    return aco;
  }

  void prettyPrint(){
    Main.log.prettyWrite(" " + op + " ");
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeStringValue(op);
}
}
