package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {
  ArrayList<RuntimeValue> list = new ArrayList<RuntimeValue>();

  public RuntimeListValue(ArrayList<RuntimeValue> v) {
    list = v;
  }
  @Override
  protected String typeName() {
    return "list";
  }

  @Override
  public String toString() {
    String temp = "[";
    for(int i = 0; i < list.size(); i++) {
      temp += (list.get(i).showInfo());
      if(i < list.size() - 1) {
        temp += ", ";
      }
    }
    temp += "]";
    return temp;
 }
 public RuntimeValue evalLen(AspSyntax where) {
   return new RuntimeIntValue(list.size());
 }

 public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
   return list.get((int)(v.getIntValue("List subscription", where)));
 }
 public void evalAssignElem(RuntimeValue index, RuntimeValue val, AspSyntax where){
   list.set((int)index.getIntValue("List assignment", where), val);
 }

  public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where) {
    return this.list;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(list.isEmpty()){
      return false;
    }
    return true;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for multiplication", where);
      return new RuntimeListValue(new ArrayList<RuntimeValue>());
    } else if (v instanceof RuntimeIntValue) {
        ArrayList<RuntimeValue> temp = new ArrayList<RuntimeValue>(list);
        for (int i = 0; i < v.getIntValue("* operand", where) - 1; i++) {
          list.addAll(temp);
        }
      return new RuntimeListValue(list);
    } else {
      runtimeError("Invalid type for multiplication", where);
      return new RuntimeListValue(new ArrayList<RuntimeValue>());
    }
  }
}
