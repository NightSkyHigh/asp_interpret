package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspAssignment extends AspStmt{
  ArrayList<AspSubscription> subList = new ArrayList<AspSubscription>();
  AspExpr test;
  AspName name;
  AspAssignment(int n) {
    super(n);
  }
  public static AspAssignment parse(Scanner s) {
    Main.log.enterParser("assignment");

    AspAssignment aa = new AspAssignment(s.curLineNum());
    aa.name = AspName.parse(s);
    while(s.curToken().kind != equalToken) {
      aa.subList.add(AspSubscription.parse(s));
    }
    skip(s, equalToken);
    aa.test = AspExpr.parse(s);
    skip(s, newLineToken);
    Main.log.leaveParser("assignment");
    return aa;
  }

  void prettyPrint(){
    int nPrinted = 0;
    name.prettyPrint();
    for(AspSubscription t : subList){
      t.prettyPrint();
    }
    Main.log.prettyWrite(" = ");
    test.prettyPrint();
    Main.log.prettyWriteLn();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    if(subList.size() > 0) {
      int count = 0;
      RuntimeValue list = name.eval(curScope);
      while(count < subList.size() - 1) {
        list = list.evalSubscription(subList.get(count).eval(curScope), this);
        count++;
      }
      RuntimeValue r = test.eval(curScope);
      //System.out.println("r typename : " + r.typeName());
      list.evalAssignElem(subList.get(count).eval(curScope), r, this);
      trace(name.toString() + "[" + subList.get(count).eval(curScope) + "] = " + r.showInfo());
    } else {
      RuntimeValue r = test.eval(curScope);
      curScope.assign(name.toString(), r);
      if(r != null) {
        trace(name.toString() + " = " + r.showInfo());
      }
    }
    return null;
  }
}
