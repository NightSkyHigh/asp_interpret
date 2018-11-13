package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax {
  String op;
  AspFactorOpr(int n) {
    super(n);
  }
  public static AspFactorOpr parse(Scanner s) {
    Main.log.enterParser("factor opr");
    AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
    switch(s.curToken().kind) {
      case astToken: skip(s, astToken); afo.op = "*"; break;
      case slashToken: skip(s, slashToken); afo.op = "/"; break;
      case percentToken: skip(s, percentToken); afo.op = "%"; break;
      case doubleSlashToken: skip(s, doubleSlashToken); afo.op = "//"; break;
      default: afo.op = "none";
    }
    Main.log.leaveParser("factor opr");
    return afo;
  }

  void prettyPrint() {
    Main.log.prettyWrite(" " + op + " ");
  }
  
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
