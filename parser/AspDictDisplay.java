package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AspDictDisplay extends AspAtom{
  ArrayList<AspStringLiteral> sl = new ArrayList<AspStringLiteral>();
  ArrayList<AspExpr> ex = new ArrayList<AspExpr>();

  AspDictDisplay(int n) {
    super(n);
  }

  static AspDictDisplay parse(Scanner s){
    Main.log.enterParser("dict display");
    AspDictDisplay add = new AspDictDisplay(s.curLineNum());
    skip(s, leftBraceToken);
    while(s.curToken().kind != rightBraceToken){
      add.sl.add(AspStringLiteral.parse(s));
      skip(s, colonToken);
      add.ex.add(AspExpr.parse(s));
      if(s.curToken().kind !=  commaToken){break;}
      skip(s,commaToken);
    }
    skip(s,rightBraceToken);
    Main.log.leaveParser("dict display");
    return add;
  }

  void prettyPrint(){
    Main.log.prettyWrite("{");
    for(int i = 0; i < sl.size() || i < ex.size(); i++) {
      if(i < sl.size()) {
        sl.get(i).prettyPrint();
        Main.log.prettyWrite(": ");
      }
      if(i < ex.size()) {
        ex.get(i).prettyPrint();
      }
    }
    Main.log.prettyWrite("}");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
  //-- Must be changed in part 3:
  trace("dict");
  ArrayList<RuntimeValue> v = new ArrayList<RuntimeValue>();
  ArrayList<RuntimeValue> v1 = new ArrayList<RuntimeValue>();
  for( int i = 0; i < sl.size(); i++ ) {
    v.add(sl.get(i).eval(curScope));
    v1.add(ex.get(i).eval(curScope));
  }
  return new RuntimeDictValue(v,v1);
  }
}
