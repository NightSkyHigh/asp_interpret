package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.*;

 public abstract class AspPrimarySuffix extends AspSyntax {
  AspPrimarySuffix(int n){
    super(n);
  }

  static AspPrimarySuffix parse(Scanner s) {
    Main.log.enterParser("primary suffix");
    AspPrimarySuffix aps = null;
    switch(s.curToken().kind) {
      case leftParToken: aps = AspArguments.parse(s); break;
      case leftBracketToken: aps = AspSubscription.parse(s); break;
    }
    Main.log.leaveParser("primary suffix");
    return aps;
  }
  abstract void prettyPrint();
}
