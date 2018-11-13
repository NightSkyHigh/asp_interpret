package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIntegerLiteral extends AspAtom{
  long integer;

  AspIntegerLiteral(int n){
    super(n);
  }

  static AspIntegerLiteral parse(Scanner s){
    Main.log.enterParser("integer literal");
    AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
    long zero = Long.parseLong(Long.toString(s.curToken().integerLit).substring(0,1));
    if(zero == 0){
      Main.log.leaveParser("integer literal");
      skip(s,integerToken);
      return ail;
    }
    ail.integer = s.curToken().integerLit;
    skip(s,integerToken);

    Main.log.leaveParser("integer literal");
    return ail;
  }
  void prettyPrint(){
    Main.log.prettyWrite("" + integer);
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeIntValue(integer);
  }
}
