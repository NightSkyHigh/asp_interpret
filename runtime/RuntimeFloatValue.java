package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
  double floatValue;

  public RuntimeFloatValue(double v) {
    floatValue = v;
  }
  @Override
  protected String typeName() {
    return "float";
  }

  @Override
  public String toString() {
    return String.valueOf(floatValue);
  }


  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return floatValue;
  }

@Override
public boolean getBoolValue(String what, AspSyntax where){
  if(floatValue != 0.0){
    return true;
  }
  return false;
}

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for addision", where);
      return new RuntimeFloatValue(0.0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(
        floatValue + v.getIntValue("+ operand", where));
    } else {
      return new RuntimeFloatValue(
        floatValue + v.getFloatValue("+ operand", where));
      }
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for substraction", where);
      return new RuntimeFloatValue(0.0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(
        floatValue - v.getIntValue("- operand", where));
    } else {
      return new RuntimeFloatValue(
        floatValue - v.getFloatValue("- operand", where));
      }
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for multiplication", where);
      return new RuntimeFloatValue(0.0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(
        floatValue * v.getIntValue("* operand", where));
    } else {
      return new RuntimeFloatValue(
        floatValue * v.getFloatValue("* operand", where));
      }
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for division", where);
      return new RuntimeFloatValue(0.0);
    } else if (v instanceof RuntimeFloatValue) {
      return new RuntimeFloatValue(
        floatValue / v.getFloatValue("/ operand", where));
    } else {
      return new RuntimeFloatValue(
        floatValue / v.getIntValue("/ operand", where));
      }
  }

  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for int division", where);
      return new RuntimeFloatValue(0.0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(
        Math.floor(floatValue / v.getIntValue("// operand", where)));
    } else {
      return new RuntimeFloatValue(
        Math.floor(floatValue / v.getFloatValue("// operand", where)));
    }
  }

  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for modulo", where);
      return new RuntimeFloatValue(0.0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(
        floatValue - ((v.getIntValue("% operand", where)) * Math.floor(floatValue / v.getIntValue("% operand", where))));
    } else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(
        floatValue - ((v.getFloatValue("% operand", where) * Math.floor(floatValue / v.getFloatValue("% operand", where)))));
    }
    runtimeError("Invalid type for modulo", where);
    return new RuntimeIntValue(0);
  }
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        floatValue == (double)v.getIntValue("== operand", where));
    } else {
      return new RuntimeBoolValue(
        floatValue == v.getFloatValue("== operand", where));
    }
  }
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        floatValue !=(double)v.getIntValue("!= operand", where));
    } else {
      return new RuntimeBoolValue(
        floatValue != v.getFloatValue("!= operand", where));
    }
  }
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        floatValue < (double)v.getIntValue("< operand", where));
    } else {
      return new RuntimeBoolValue(
        floatValue < v.getFloatValue("< operand", where));
    }
  }
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        floatValue <= (double)v.getIntValue("<= operand", where));
    } else {
      return new RuntimeBoolValue(
        floatValue <= v.getFloatValue("<= operand", where));
    }
  }
public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        floatValue > (double)v.getIntValue("> operand", where));
    } else {
      return new RuntimeBoolValue(
        floatValue > v.getFloatValue("> operand", where));
    }
  }
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(true);
      } else if (v instanceof RuntimeIntValue) {
        return new RuntimeBoolValue(
          floatValue >= (double)v.getIntValue(">= operand", where));
      } else {
        return new RuntimeBoolValue(
          floatValue >= v.getFloatValue(">= operand", where));
      }
    }
    public RuntimeValue evalNegate(AspSyntax where) {
      return new RuntimeFloatValue(-floatValue);
    }
    public RuntimeValue evalPositive(AspSyntax where) {
      return new RuntimeFloatValue(floatValue);
    }
}
