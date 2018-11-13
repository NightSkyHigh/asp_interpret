package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTerm extends AspSyntax{
  ArrayList<AspFactor> factorList = new ArrayList<AspFactor>();
  ArrayList<AspTermOpr> termOprList = new ArrayList<AspTermOpr>();

  AspTerm(int n) {
    super(n);
  }
  public static AspTerm parse(Scanner s) {
    Main.log.enterParser("term");
    AspTerm at = new AspTerm(s.curLineNum());
    while(true) {
      AspFactor tamp = AspFactor.parse(s);
      if(!tamp.ap.isEmpty()) {
        at.factorList.add(tamp);
      } else {
        break;
      }
      if(s.curToken().kind == plusToken || s.curToken().kind == minusToken) {
        at.termOprList.add(AspTermOpr.parse(s));
      } else {
        break;
      }
    }
    Main.log.leaveParser("term");
    return at;
  }

  void prettyPrint(){
    for(int i = 0; i < factorList.size(); i++) {
      factorList.get(i).prettyPrint();
      if(i < termOprList.size()) {
        termOprList.get(i).prettyPrint();
      }
    }
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = factorList.get(0).eval(curScope);
    for(int i = 1; i < factorList.size(); i++) {
      String k = termOprList.get(i-1).op;
      switch(k) {
        case "-":
          v = v.evalSubtract(factorList.get(i).eval(curScope), this); break;
        case "+":
          v = v.evalAdd(factorList.get(i).eval(curScope), this); break;
        default:
          Main.panic("Illegal term operator: " + k + "!");
      }
    }
    return v;
  }
}
