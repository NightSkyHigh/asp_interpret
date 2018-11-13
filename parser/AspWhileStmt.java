package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspWhileStmt extends AspStmt{
  AspExpr test;
  AspSuite body;

  AspWhileStmt(int n) {
    super(n);
  }
  public static AspWhileStmt parse(Scanner s) {
    Main.log.enterParser("while stmt");

    AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
    skip(s, whileToken);
    aws.test = AspExpr.parse(s);
    skip(s, colonToken);
    aws.body = AspSuite.parse(s);
    Main.log.leaveParser("while stmt");
    return aws;
  }
  void prettyPrint(){
    Main.log.prettyWrite("while ");
    Main.log.prettyIndent();
    test.prettyPrint();
    Main.log.prettyWrite(":");
    Main.log.prettyWriteLn();
    body.prettyPrint();
    Main.log.prettyDedent();
    }
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      while(test.eval(curScope).getBoolValue("While statement", this)) {
        trace("while " + test.eval(curScope).toString() + ": ...");
        body.eval(curScope);
      }
      trace("while " + test.eval(curScope).toString() + ":");
      return null;
    }
  }
