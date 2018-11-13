package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPassStmt extends AspStmt{
  AspPassStmt(int n) {
    super(n);
  }

  public static AspPassStmt parse(Scanner s) {
    Main.log.enterParser("pass stmt");

    AspPassStmt aps = new AspPassStmt(s.curLineNum());
    skip(s, passToken);

    Main.log.leaveParser("pass stmt");
    return aps;
  }

  void prettyPrint() {
    Main.log.prettyWrite("pass");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    trace("pass");
    return null;
  }
}
