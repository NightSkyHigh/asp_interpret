package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspStmt extends AspSyntax {
  AspStmt a = null;
  AspStmt(int n){
    super(n);
  }
  static AspStmt parse(Scanner s){
    Main.log.enterParser("stmt");
    AspStmt as = new AspStmt(s.curLineNum());

    switch(s.curToken().kind){
      case defToken: as.a = AspFuncDef.parse(s); break;
      case ifToken: as.a = AspIfStmt.parse(s); break;
      case passToken: as.a = AspPassStmt.parse(s); break;
      case returnToken: as.a = AspReturnStmt.parse(s); break;
      case whileToken: as.a = AspWhileStmt.parse(s); break;
      default: if(s.anyEqualToken() == true) {
                  as.a = AspAssignment.parse(s);
                } else {
                  as.a = AspExprStmt.parse(s);
                }
    }
    Main.log.leaveParser("stmt");
    return as;
  }
  void prettyPrint(){
    a.prettyPrint();
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return a.eval(curScope);
  }
}
