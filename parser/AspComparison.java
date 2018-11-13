package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspComparison extends AspSyntax{
  ArrayList<AspTerm> aTermList = new ArrayList<AspTerm>();
  ArrayList<AspCompOpr> compOprList = new ArrayList<AspCompOpr>();

  AspComparison(int n){
    super(n);
  }

  static AspComparison parse(Scanner s){
    Main.log.enterParser("comparison");
    AspComparison ac = new AspComparison(s.curLineNum());
    ac.aTermList.add(AspTerm.parse(s));

    while(s.curToken().kind == greaterToken || s.curToken().kind == lessToken || s.curToken().kind == doubleEqualToken || s.curToken().kind == greaterEqualToken || s.curToken().kind == lessEqualToken || s.curToken().kind == notEqualToken){
      ac.compOprList.add(AspCompOpr.parse(s));
      ac.aTermList.add(AspTerm.parse(s));
    }
    Main.log.leaveParser("comparison");
    return ac;
  }

  void prettyPrint() {

    for(int i = 0; i < aTermList.size(); i++) {
      aTermList.get(i).prettyPrint();
      if(i < compOprList.size()) {
        compOprList.get(i).prettyPrint();
      }
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = aTermList.get(0).eval(curScope);
    RuntimeValue b = v;
    for(int i = 1; i < aTermList.size(); i++) {
      String k = compOprList.get(i-1).op;
      switch (k) {
        case ">":
          b = v.evalGreater(aTermList.get(i).eval(curScope), this); break;
        case "<":
          b = v.evalLess(aTermList.get(i).eval(curScope), this);break;
        case "==":
          b = v.evalEqual(aTermList.get(i).eval(curScope), this);break;
        case ">=":
          b = v.evalGreaterEqual(aTermList.get(i).eval(curScope), this);break;
        case "<=":
          b = v.evalLessEqual(aTermList.get(i).eval(curScope), this);break;
        case "!=":
          b = v.evalNotEqual(aTermList.get(i).eval(curScope), this);break;
      }
      v = aTermList.get(i).eval(curScope);
      if(b.getBoolValue(k + "operand", this) == false) {
        break;
      }
    }
    v = b;
    return v;
  }
}
