package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspIfStmt extends AspStmt{
  ArrayList<AspExpr> testList = new ArrayList<AspExpr>();
  ArrayList<AspSuite> bodyList = new ArrayList<AspSuite>();

  AspIfStmt(int n) {
    super(n);
  }
  public static AspIfStmt parse(Scanner s) {
    Main.log.enterParser("if stmt");
    AspIfStmt ais = new AspIfStmt(s.curLineNum());
    skip(s, ifToken);
    ais.testList.add(AspExpr.parse(s));
    skip(s, colonToken);
    ais.bodyList.add(AspSuite.parse(s));


    while(s.curToken().kind == elifToken) {
      skip(s, elifToken);
      ais.testList.add(AspExpr.parse(s));
      skip(s, colonToken);
      ais.bodyList.add(AspSuite.parse(s));

    }

    if(s.curToken().kind == elseToken) {
      skip(s, elseToken);
      skip(s, colonToken);
      ais.bodyList.add(AspSuite.parse(s));

    }
    Main.log.leaveParser("if stmt");
    return ais;
  }

  void prettyPrint() {
    for(int i = 0; i < bodyList.size(); i++) {
      if(i == 0) {
        Main.log.prettyWrite("if ");
        Main.log.prettyIndent();
        testList.get(i).prettyPrint();
        Main.log.prettyWriteLn(":");
        bodyList.get(i).prettyPrint();
        Main.log.prettyDedent();
      } else if (i < testList.size()) {
        Main.log.prettyWrite("elif ");
        Main.log.prettyIndent();
        testList.get(i).prettyPrint();
        Main.log.prettyWriteLn(":");
        bodyList.get(i).prettyPrint();
        Main.log.prettyDedent();
      } else {
        Main.log.prettyWriteLn("else:");
        Main.log.prettyIndent();
        bodyList.get(i).prettyPrint();
        Main.log.prettyDedent();
      }
    }
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    int i = 0;
    while(i < testList.size()) {
      if(testList.get(i).eval(curScope).getBoolValue("If else statememnt", this)) {
        trace("if True alt #" + (i + 1) + ": ...");
        return bodyList.get(i).eval(curScope);
      }
      i++;
    }
    if(bodyList.size() > testList.size()){
      trace("else: ...");
      return bodyList.get(i).eval(curScope);
    }
    return new RuntimeNoneValue();
  }
}
