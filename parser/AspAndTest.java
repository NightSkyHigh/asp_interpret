package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspAndTest extends AspSyntax{
  ArrayList<AspNotTest> testNot = new ArrayList<AspNotTest>();

  AspAndTest(int n){
    super(n);
  }

  static AspAndTest parse(Scanner s){
    Main.log.enterParser("and test");
    AspAndTest aat = new AspAndTest(s.curLineNum());
    while(true){
      aat.testNot.add(AspNotTest.parse(s));
      if(s.curToken().kind != andToken){ break; }
      skip(s, andToken);
    }
    Main.log.leaveParser("and test");
    return aat;
  }
  void prettyPrint(){
    for(int i = 0; i < testNot.size();i++ ){
      testNot.get(i).prettyPrint();
      if(i < testNot.size()-1){
        Main.log.prettyWrite(" and ");
      }
    }
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = testNot.get(0).eval(curScope);
    for( int i = 1; i < testNot.size(); i++ ) {
      if(!v.getBoolValue("and operand", this)) {
        return v;
      }
      v = testNot.get(i).eval(curScope);
    }
    return v;
  }
}
