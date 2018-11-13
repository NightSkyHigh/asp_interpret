package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeFunc extends RuntimeValue{
  String name;
  AspFuncDef def;
  RuntimeScope scope;

  @Override
  protected String typeName() {
    return "Function";
  }

  public RuntimeFunc(String name) {
    this.name = name;
  }
  public RuntimeFunc(String name, AspFuncDef def, RuntimeScope scope) {
    this.name = name;
    this.def = def;
    this.scope = scope;
    scope.assign(name, this);

  }
  public String getStringValue(String what, AspSyntax where) {
    return name;
  }
  @Override
  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> args, RuntimeScope cope, AspSyntax where) {
    RuntimeScope curScope = new RuntimeScope(cope);
    ArrayList<RuntimeValue> args1 = args.get(0).getListValue("func",def);
    long length = args1.size();

    if(!(length == def.nameList.size())) {
      runtimeError("Wrong number of arguments for function expected " + def.nameList.size() + " but found " + length, where);
    }
    for(int i = 0; i < length; i++) {
      curScope.assign(def.nameList.get(i).toString(), args1.get(i));
    }
    return runFunction(curScope, cope, where);
  }
  public RuntimeValue runFunction(RuntimeScope curScope, RuntimeScope cope, AspSyntax where) {
    try{
      def.body.eval(curScope);
    } catch (RuntimeReturnValue r) {
      return r.value;
    }
    return new RuntimeNoneValue();
  }
}
