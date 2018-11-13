package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspArguments extends AspPrimarySuffix {
  ArrayList<AspExpr> argList = new ArrayList<AspExpr>();
  AspArguments(int n){
    super(n);
  }
  public static AspArguments parse(Scanner s) {
    Main.log.enterParser("arguments");

    AspArguments aa = new AspArguments(s.curLineNum());
    skip(s, leftParToken);
    if(s.curToken().kind != rightParToken) {
      aa.argList.add(AspExpr.parse(s));
      while(s.curToken().kind == commaToken) {
        skip(s,commaToken);
        aa.argList.add(AspExpr.parse(s));

      }
    }
    skip(s, rightParToken);

    Main.log.leaveParser("arguments");
    return aa;
  }

  void prettyPrint(){
    Main.log.prettyWrite("(");
    int i = 0;
    for(AspExpr t : argList){
      if(i > 0) {
        Main.log.prettyWrite(", ");
      }
      t.prettyPrint();
      i++;
    }
    Main.log.prettyWrite(")");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> v = new ArrayList<RuntimeValue>();
    for( int i = 0; i < argList.size(); i++ ) {
      v.add(argList.get(i).eval(curScope));
    }
    return new RuntimeListValue(v);
  }
}
