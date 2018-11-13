package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimary extends AspSyntax {
  AspAtom atom;
  ArrayList<AspPrimarySuffix> primarySufixList = new ArrayList<AspPrimarySuffix>();

  AspPrimary(int n) {
    super(n);
  }
  public static AspPrimary parse(Scanner s) {
    Main.log.enterParser("primary");
    AspPrimary ap = new AspPrimary(s.curLineNum());
    ap.atom = AspAtom.parse(s);
    while(s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken) {
      ap.primarySufixList.add(AspPrimarySuffix.parse(s));

    }
    Main.log.leaveParser("primary");
    return ap;
  }
  void prettyPrint() {
    atom.prettyPrint();
    for(AspPrimarySuffix p : primarySufixList) {
        p.prettyPrint();
    }
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = atom.eval(curScope);
    if(v instanceof RuntimeFunc) {
      ArrayList<RuntimeValue> argList = new ArrayList<RuntimeValue>();

      for(AspPrimarySuffix a : primarySufixList) {
        argList.add(a.eval(curScope));
      }

      String param = "";
      for (RuntimeValue q : argList ) { param += q.showInfo(); }
      trace("Call function " + atom.eval(curScope).getStringValue("primary",this) + " with params " + param);
      v = atom.eval(curScope).evalFuncCall(argList, curScope, this);
    } else {
      for(AspPrimarySuffix a : primarySufixList) {
        v = v.evalSubscription(a.eval(curScope), this);
      }
    }
    if(v instanceof RuntimeNoneValue) {
      trace("None");
      return v;
    }
    return v;
  }
  public RuntimeValue evalSubscription(RuntimeValue indeks, RuntimeScope curScope)throws RuntimeReturnValue{
    trace("eval subsciption - index: " + indeks);
    if(indeks instanceof RuntimeIntValue) {
      return atom.eval(curScope).evalSubscription(indeks, this);
    }
    indeks.runtimeError("index not an int",this);
    return null;
  }
  public void evalAssignElem(RuntimeValue indeks, RuntimeValue elem, RuntimeScope curScope)throws RuntimeReturnValue {
    if(indeks instanceof RuntimeValue) {
       atom.eval(curScope).evalAssignElem(indeks, elem, this);
    }
  }
}
