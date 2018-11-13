package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspSyntax{
  AspAtom(int n){
    super(n);
  }

  static AspAtom parse(Scanner s){
    AspAtom a = null;
    Main.log.enterParser("atom");

    switch(s.curToken().kind){
      case falseToken:
      case trueToken: a = AspBooleanLiteral.parse(s); break;
      case floatToken: a = AspFloatLiteral.parse(s); break;
      case integerToken: a = AspIntegerLiteral.parse(s); break;
      case leftBraceToken: a = AspDictDisplay.parse(s); break;
      case leftBracketToken: a = AspListDisplay.parse(s); break;
      case leftParToken: a = AspInnerExpr.parse(s); break;
      case stringToken: a = AspStringLiteral.parse(s); break;
      case nameToken: a = AspName.parse(s); break;
      case noneToken: a = AspNoneLiteral.parse(s); break;
      default: parserError("Expected an expression atom but found a " + s.curToken().kind + " !", s.curLineNum());
    }
    Main.log.leaveParser("atom");
    return a;
  }
  abstract void prettyPrint();
}
