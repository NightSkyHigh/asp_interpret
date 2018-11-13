package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNotTest extends AspSyntax{
  AspComparison ac;
  boolean not = false;
  AspNotTest(int n){
    super(n);
  }

  static AspNotTest parse(Scanner s){
    Main.log.enterParser("not test");
    AspNotTest ant = new AspNotTest(s.curLineNum());
    if(s.curToken().kind == notToken) {
      skip(s, notToken);
      ant.not = true;
    }
    ant.ac = AspComparison.parse(s);
    Main.log.leaveParser("not test");
    return ant;
  }
  void prettyPrint() {
    if(not){
      Main.log.prettyWrite(" not ");
    }
    ac.prettyPrint();
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = ac.eval(curScope);
    if(not) {
      v = v.evalNot(this);
    }
    return v;
  }
}
