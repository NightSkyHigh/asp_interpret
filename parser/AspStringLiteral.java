package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspStringLiteral extends AspAtom{
String string;
  AspStringLiteral(int n){
    super(n);
  }

  static AspStringLiteral parse(Scanner s){
    Main.log.enterParser("string literal");
    AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
    asl.string = s.curToken().stringLit;
    skip(s,stringToken);
     Main.log.leaveParser("string literal");
     return asl;
  }

  void prettyPrint(){
    Main.log.prettyWrite("\"" + string + "\"");
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeStringValue(string);
  }
}
