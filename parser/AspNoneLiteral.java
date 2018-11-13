package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNoneLiteral extends AspAtom{

  AspNoneLiteral(int n){
    super(n);
  }

  public static AspNoneLiteral parse(Scanner s){
    Main.log.enterParser("None");
    AspNoneLiteral none = new AspNoneLiteral(s.curLineNum());
    skip(s,noneToken);

  Main.log.leaveParser("None");
  return none;
}


  void prettyPrint(){
    Main.log.prettyWrite("None");
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    trace("None");
    return new RuntimeNoneValue();
  }
}
