package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTermOpr extends AspSyntax{
  String op;
  AspTermOpr(int n) {
    super(n);
  }
  public static AspTermOpr parse(Scanner s) {
    Main.log.enterParser("term opr");
    AspTermOpr ato = new AspTermOpr(s.curLineNum());
    switch(s.curToken().kind) {
      case plusToken:  ato.op = "+"; skip(s,plusToken); break;
      case minusToken: ato.op = "-"; skip(s,minusToken); break;
      default: ato.op = "none";
    }
    Main.log.leaveParser("term opr");
    return ato;
  }

  void prettyPrint() {
    Main.log.prettyWrite(" " + op + " ");
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    
  return null;
}
}
