package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.HashMap;
import java.util.ArrayList;

public class RuntimeDictValue extends RuntimeValue {
  //HashMap<RuntimeValue,RuntimeValue> dict = new HashMap<RuntimeValue,RuntimeValue>();
  ArrayList<RuntimeValue> v = new ArrayList<RuntimeValue>();
  ArrayList<RuntimeValue> v1 = new ArrayList<RuntimeValue>();

  public RuntimeDictValue(ArrayList<RuntimeValue> v,ArrayList<RuntimeValue> v1){
    //for ( int i = 0;i < v.size();i++) {
    //  dict.put(v.get(i),v1.get(i));
    //}
    this.v = v;
    this.v1 = v1;
  }

  @Override
  protected String typeName() {
    return "Dictionary";
  }

  @Override
  public String toString() {
    String temp = "{";
  /*  int count = 0;
    for (RuntimeValue keys : dict.keySet()){
      temp += keys.showInfo();
      temp += ":";
      temp += dict.get(keys).showInfo();
      if(count < dict.size() - 1){
        temp += ", ";
      }
      count++;
    }*/
    for(int i = 0; i < v.size(); i++) {
      temp += (v.get(i).showInfo());
      temp += v1.get(i).showInfo();
      if(i < v.size() - 1) {
        temp += ", ";
      }
    }

    temp += "}";
    return temp;
 }

 public RuntimeValue evalSubscription(RuntimeValue val, AspSyntax where) {
   int index = -1;
   for (int i = 0;i < v.size();i++) {
     if(v.get(i).showInfo().equals(val.showInfo())) {
       index = i;
     }
   }
   return v1.get(index);
 }

}
