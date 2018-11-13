package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.lang.Math.*;

public class AspFloatLiteral extends AspAtom{
  double number;

  AspFloatLiteral(int n){
    super(n);
  }

  static AspFloatLiteral parse(Scanner s){
    Main.log.enterParser("float literal");
    AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
    afl.number = s.curToken().floatLit;
    skip(s, floatToken);

    Main.log.leaveParser("float literal");
    return afl;
  }

  void prettyPrint(){
    Main.log.prettyWrite("" + number);
  }

  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeFloatValue(number);
}
}
