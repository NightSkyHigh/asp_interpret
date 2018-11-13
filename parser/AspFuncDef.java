package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspFuncDef extends AspStmt {
  public AspName name;
  public AspSuite body;
  public ArrayList<AspName> nameList = new ArrayList<AspName>();

  AspFuncDef(int n) {
    super(n);
  }
  public static AspFuncDef parse(Scanner s) {
    Main.log.enterParser("func def");

    AspFuncDef afd = new AspFuncDef(s.curLineNum());
    skip(s, defToken);
    afd.name = AspName.parse(s);
    skip(s, leftParToken);
    int i = 0;
    while(s.curToken().kind != rightParToken) {
      if(i != 0){
      skip(s, commaToken);
      }
      afd.nameList.add(AspName.parse(s));
      i++;
    }
    skip(s, rightParToken);
    skip(s, colonToken);
    afd.body = AspSuite.parse(s);
    Main.log.leaveParser("func def");
    return afd;
  }

  void prettyPrint(){
    Main.log.prettyWrite("def ");
    Main.log.prettyIndent();
    name.prettyPrint();
    Main.log.prettyWrite(" (");
    for(int i = 0; i < nameList.size(); i++) {
      nameList.get(i).prettyPrint();
      if(i < nameList.size() - 1) {
        Main.log.prettyWrite(", ");
      }
    }
    Main.log.prettyWriteLn("):");
    body.prettyPrint();
    Main.log.prettyDedent();
    Main.log.prettyWriteLn();
  }
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
    trace("def " + name.toString());
    return new RuntimeFunc(name.toString(), this, curScope);
  }
}
