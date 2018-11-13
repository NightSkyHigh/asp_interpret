package no.uio.ifi.asp.runtime;

// For part 4:

public class RuntimeReturnValue extends Exception {
    RuntimeValue value;

    public RuntimeReturnValue(RuntimeValue v) {
	     value = v;
    }
}
