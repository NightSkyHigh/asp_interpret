package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspFactor extends AspSyntax{
  ArrayList<AspFactorOpr> afo = new ArrayList<AspFactorOpr>();
  ArrayList<AspPrimary> ap = new ArrayList<AspPrimary>();
  AspFactorPrefix afp;
  AspFactor(int n){
    super(n);
  }

  static AspFactor parse(Scanner s){
    Main.log.enterParser("factor");
    AspFactor fact = new AspFactor(s.curLineNum());
    if(s.curToken().kind == plusToken || s.curToken().kind == minusToken){
      fact.afp = AspFactorPrefix.parse(s);
    }
    fact.ap.add(AspPrimary.parse(s));
    while(s.curToken().kind == astToken|| s.curToken().kind == slashToken|| s.curToken().kind == percentToken || s.curToken().kind == doubleSlashToken){
      fact.afo.add(AspFactorOpr.parse(s));
      fact.ap.add(AspPrimary.parse(s));
  }
    Main.log.leaveParser("factor");
    return fact;
  }

  void prettyPrint() {
    if(afp != null) {
      afp.prettyPrint();
    }
    for(int i = 0; i < ap.size(); i++) {

      ap.get(i).prettyPrint();
      if(afo.size() > i) {
        afo.get(i).prettyPrint();
      }
    }
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = ap.get(0).eval(curScope);
    if(afp != null) {
      String k = afp.op;
      if(k.equals("+")) {
        v = v.evalPositive(this);
      }
      if(k.equals("-")) {
        v = v.evalNegate(this);
      }
    }
    for(int i = 1; i < ap.size(); i++) {
      String k = afo.get(i-1).op;
      switch (k) {
        case "*":
          v = v.evalMultiply(ap.get(i).eval(curScope), this); break;
        case "/":
          v = v.evalDivide(ap.get(i).eval(curScope), this); break;
        case "%":
          v = v.evalModulo(ap.get(i).eval(curScope), this); break;
        case "//":
          v = v.evalIntDivide(ap.get(i).eval(curScope), this); break;
        default:
          Main.panic("Illegal term operator: " + k + "!");
        }
      }
      return v;
  }
}
