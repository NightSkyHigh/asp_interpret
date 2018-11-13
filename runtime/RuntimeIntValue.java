package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.lang.Math.*;

public class RuntimeIntValue extends RuntimeValue {
  long intValue;

  public RuntimeIntValue(long v) {
    intValue = v;
  }
  @Override
  protected String typeName() {
    return "int";
  }


  @Override
  public String toString() {
    return String.valueOf(intValue);
  }


  @Override
  public long getIntValue(String what, AspSyntax where) {
    return intValue;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if(intValue != 0) {
      return true;
    }
    return false;
  }


  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for addision", where);
      return new RuntimeIntValue(0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(
        intValue + v.getIntValue("+ operand", where));
    } else if (v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(
        intValue + v.getFloatValue("+ operand", where));
      } else {
        runtimeError("Invalid type for addision", where);
        return new RuntimeIntValue(0);
      }
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for substraction", where);
      return new RuntimeIntValue(0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(
        intValue - v.getIntValue("- operand", where));
    } else if (v instanceof RuntimeFloatValue) {
      return new RuntimeFloatValue(
        intValue - v.getFloatValue("- operand", where));
    }
      runtimeError("Invalid type for substraction", where);
      return new RuntimeIntValue(0);
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for multiplication", where);
      return new RuntimeIntValue(0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(
        intValue * v.getIntValue("* operand", where));
    } else if (v instanceof RuntimeFloatValue) {
      return new RuntimeFloatValue(
        intValue * v.getFloatValue("* operand", where));
    } else {
      runtimeError("Invalid type for multiplication", where);
      return new RuntimeIntValue(0);
    }
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for division", where);
      return new RuntimeIntValue(0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(
        intValue / v.getIntValue("/ operand", where));
    } else if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(
          intValue / v.getFloatValue("/ operand", where));
    }

    else {
      runtimeError("Invalid type for division", where);
      return new RuntimeIntValue(0);
    }
  }

  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for int division", where);
      return new RuntimeIntValue(0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(
        Math.floorDiv(intValue, v.getIntValue("/ operand",where)));
    } else if (v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(
        Math.floor(intValue / v.getFloatValue("/ operand",where)));
    } else {
      runtimeError("Invalid type for Int division", where);
      return new RuntimeIntValue(0);
    }
  }

  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      runtimeError("None is invalid factor for modulo", where);
      return new RuntimeIntValue(0);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(
        Math.floorMod(intValue, v.getIntValue("% operand",where)));
    } else if(v instanceof RuntimeFloatValue) {
      return new RuntimeFloatValue(
        intValue - v.getFloatValue("% operand",where) * Math.floor(intValue / v.getFloatValue("% operand",where)));
    }
    runtimeError("Invalid type for Int modulo", where);
    return new RuntimeIntValue(0);
  }
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        intValue == v.getIntValue("= operand",where));
    } else {
      return new RuntimeBoolValue(
        (double)intValue == v.getFloatValue("= operand",where));
    }
  }
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        intValue != v.getIntValue("!= operand",where));
    } else {
      return new RuntimeBoolValue(
        (double)intValue != v.getFloatValue("!= operand",where));
    }
  }
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        intValue < v.getIntValue("< operand",where));
    } else {
      return new RuntimeBoolValue(
        (double)intValue < v.getFloatValue("< operand",where));
    }
  }
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        intValue <= v.getIntValue("<= operand",where));
    } else {
      return new RuntimeBoolValue(
        (double)intValue <= v.getFloatValue("<= operand",where));
    }
  }
public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    } else if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(
        intValue > v.getIntValue("> operand",where));
    } else {
      return new RuntimeBoolValue(
        (double)intValue > v.getFloatValue("> operand",where));
    }
  }
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(true);
      } else if (v instanceof RuntimeIntValue) {
        return new RuntimeBoolValue(
          intValue >= v.getIntValue(">= operand",where));
      } else {
        return new RuntimeBoolValue(
          (double)intValue >= v.getFloatValue(">= operand",where));
      }
    }
    public RuntimeValue evalNegate(AspSyntax where) {
      return new RuntimeIntValue(-intValue);
    }
    public RuntimeValue evalPositive(AspSyntax where) {
      return new RuntimeIntValue(intValue);
    }
}
