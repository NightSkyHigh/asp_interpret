package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.AspNoneLiteral;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
      assign("len", new RuntimeFunc("len") {
      @Override
      public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, RuntimeScope scope,AspSyntax where) {
        checkNumParams(actualParams, 1, "len", where);
        return actualParams.get(0).evalLen(where);
      }});

    assign("print", new RuntimeFunc("print"){
      @Override
      public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> v, RuntimeScope scope, AspSyntax where){
        ArrayList<RuntimeValue> values = v.get(0).getListValue("print lib",where);
        for (RuntimeValue a : values){
          System.out.print(a.showInfo());
        }
        System.out.println();
        return new RuntimeNoneValue();
      }});

    assign("float", new RuntimeFunc("float"){
      @Override
      public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> v,RuntimeScope scope, AspSyntax where){
        checkNumParams(v,1,"float",where);
        RuntimeValue v1 = v.get(0).evalSubscription(new RuntimeIntValue(0),where);
        if(v1 instanceof RuntimeIntValue){
          Long a = v1.getIntValue("RuntimeLibrary",where);
          return new RuntimeFloatValue(Double.valueOf(a));
        } else if(v1 instanceof RuntimeStringValue){
          try{
            return new RuntimeFloatValue(Double.parseDouble(v1.getStringValue("RuntimeLibrary",where)));
          } catch(NumberFormatException e){
            RuntimeValue.runtimeError("String" + v1.showInfo() + "cannot be converted to float",where);
          }
        } else {
          return new RuntimeFloatValue(v1.getFloatValue("RuntimeLibrary",where));
        }
        return new RuntimeNoneValue();
      }
    });

    assign("int", new RuntimeFunc("int"){
      @Override
      public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> v, RuntimeScope scope, AspSyntax where){
        RuntimeValue v1 = v.get(0);
        checkNumParams(v,1,"int",where);
        RuntimeValue value = v1.evalSubscription(new RuntimeIntValue(0),where);
        if(value instanceof RuntimeFloatValue){
          return new RuntimeIntValue((long)(value.getFloatValue("RuntimeLibrary",where)));
        } else if(value instanceof RuntimeStringValue){
          try{
            return new RuntimeIntValue(Long.parseLong(value.getStringValue("RuntimeLibrary",where)));
          } catch(NumberFormatException e){
            RuntimeValue.runtimeError("String" + value.showInfo() + "cannot be converted to float",where);
          }
        } else  {
          return new RuntimeIntValue(value.getIntValue("int value", where));
        }
        return new RuntimeNoneValue();
      }
    });

    assign("str", new RuntimeFunc("str"){
      @Override
      public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> v, RuntimeScope scope,AspSyntax where){
        checkNumParams(v, 1, "str", where);
        RuntimeValue value = v.get(0).evalSubscription(new RuntimeIntValue(0),where);
        return new RuntimeStringValue(value.toString());
      }
    });


    assign("input", new RuntimeFunc("input"){
      @Override
      public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> v,RuntimeScope scope, AspSyntax where){
        System.out.println(v.get(0).evalSubscription(new RuntimeIntValue(0),where).showInfo());
        return new RuntimeStringValue(keyboard.nextLine());
      }
    });
  }

  private void checkNumParams(ArrayList<RuntimeValue> actArgs,
    int nCorrect, String id, AspSyntax where) {
	  if (actArgs.size() != nCorrect)
	     RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
