package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
public class RuntimeStringValue extends RuntimeValue{
  String strVal;

  public RuntimeStringValue(String v){
    strVal = v;
  }
  @Override
  public String showInfo(){
    if(strVal.indexOf('\'') >= 0){ return "\"" + strVal + "\"";}
    else{return "'" + strVal + "'";}
  }
  @Override
  public String typeName(){
    return "String";
  }

  @Override
  public String getStringValue(String what, AspSyntax where){
    return strVal;
  }

   public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
     return new RuntimeStringValue(String.valueOf(strVal.charAt((int)v.getIntValue("String subscription", where))));
   }
   public void evalAssignElem(RuntimeValue index, RuntimeValue val, AspSyntax where){
     strVal = strVal.substring(0, ((int)index.getIntValue("List assignment", where))) + val.getStringValue("List assignment", where) + strVal.substring(((int)index.getIntValue("List assignment", where)), strVal.length()); 
   }


@Override
public boolean getBoolValue(String what,AspSyntax where){
  return (!strVal.isEmpty());
}
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeNoneValue){
      runtimeError("cannot concatenate 'str' and 'NoneType' objects", where);
      return new RuntimeStringValue("");
    }
    if(v instanceof RuntimeStringValue){
      return new RuntimeStringValue(
      strVal += v.getStringValue("+ operand",where));
    }
    runtimeError("Invalid type",where);
    return new RuntimeStringValue("");
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeNoneValue){
      runtimeError("can't multiply sequence by non-int of type NoneType",where);
      return new RuntimeStringValue("");
    }
    if(v instanceof RuntimeIntValue){
      long num = v.getIntValue("* operand",where);
      String temp = "";
      for (int i = 0;i < num;i++ ) {
        temp +=strVal;
      }
      return new RuntimeStringValue(temp);
    }
    runtimeError("Invalid type",where);
    return new RuntimeStringValue("");
  }

  //NB:Spørr om == eller .equals skal brukes
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(false);
    }
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(strVal.equals(v.getStringValue("== operand",where)));
    }
    runtimeError("Invalid type",where);
    return new RuntimeStringValue("");
  }

  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(true);
    }
    if(v instanceof RuntimeStringValue){
      return new RuntimeBoolValue(!(strVal.equals(v.getStringValue("!= operand",where))));
    }
    runtimeError("Invalid type",where);
    return new RuntimeStringValue("");
  }

    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(false);
      }
      if(v instanceof RuntimeStringValue){
      int comp = strVal.compareTo(v.getStringValue("< operand",where));
        return new RuntimeBoolValue(comp < 0);
    }
    runtimeError("Invalid type",where);
    return new RuntimeStringValue("");
  }

    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(true);
      }
      if(v instanceof RuntimeStringValue){
        int comp = strVal.compareTo(v.getStringValue("> operand",where));
        return new RuntimeBoolValue(comp > 0);
      }
      runtimeError("Invalid type",where);
      return new RuntimeStringValue("");
  }

  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(true);
    }
    if(v instanceof RuntimeStringValue){
      int comp = strVal.compareTo(v.getStringValue(">= operand",where));
      return new RuntimeBoolValue(comp >= 0);
    }
    runtimeError("Invalid type",where);
    return new RuntimeStringValue("");
  }

  public RuntimeValue evalNot(AspSyntax where){
      return new RuntimeBoolValue(!(strVal.isEmpty()));
  }
}
