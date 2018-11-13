package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

 public class AspBooleanLiteral extends AspAtom{
   boolean bool;
    AspBooleanLiteral(int n) {
      super(n);
    }

    public static AspBooleanLiteral parse(Scanner s) {
      Main.log.enterParser("boolean literal");
      AspBooleanLiteral ab = new AspBooleanLiteral(s.curLineNum());

      if(s.curToken().kind == trueToken) {
        ab.bool = true;
        skip(s,trueToken);
      } else if (s.curToken().kind == falseToken) {
        ab.bool = false;
        skip(s,falseToken);
      }

	    Main.log.leaveParser("boolean literal");
      return ab;
  }

  void prettyPrint(){
    if(bool == true) {
      Main.log.prettyWrite("True");
    } else {
      Main.log.prettyWrite("False");
    }
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeBoolValue(bool);
  }
}
