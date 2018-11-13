package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom{
  ArrayList<AspExpr> expr = new ArrayList<AspExpr>();

    AspListDisplay(int n){
      super(n);
    }

    static AspListDisplay parse(Scanner s){
      Main.log.enterParser("list display");
      AspListDisplay ld = new AspListDisplay(s.curLineNum());
      skip(s, leftBracketToken);
      while(s.curToken().kind != rightBracketToken){
        ld.expr.add(AspExpr.parse(s));
        if(s.curToken().kind != commaToken){break;}
        skip(s,commaToken);
      }
      skip(s,rightBracketToken);
      Main.log.leaveParser("list display");
      return ld;
    }

    void prettyPrint(){
      Main.log.prettyWrite("[");
      for(int i = 0; i < expr.size();i++ ){
        expr.get(i).prettyPrint();
        if(i < expr.size()-1){
          Main.log.prettyWrite(", ");
        }
      }
      Main.log.prettyWrite("]");
    }
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      ArrayList<RuntimeValue> v = new ArrayList<RuntimeValue>();
      for( int i = 0; i < expr.size(); i++ ) {
        v.add(expr.get(i).eval(curScope));
      }
      return new RuntimeListValue(v);
  }
}
