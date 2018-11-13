package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspName extends AspAtom{
  String name;

  AspName(int n){
    super(n);
  }

  static AspName parse(Scanner s){
    Main.log.enterParser("name");
    AspName an = new AspName(s.curLineNum());
    an.name = s.curToken().name;
    skip(s,nameToken);

    Main.log.leaveParser("name");
    return an;
  }

  void prettyPrint(){
    Main.log.prettyWrite(name);
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return curScope.find(name, this);
  }

  public String toString() {
    return name;
  }

}
