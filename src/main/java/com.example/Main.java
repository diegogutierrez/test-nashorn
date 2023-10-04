package com.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main {
  public static void main(String[] args) {
    // create a script engine manager
    ScriptEngineManager factory = new ScriptEngineManager();
    // create a Nashorn script engine
    ScriptEngine engine = factory.getEngineByName("nashorn");
    System.out.println(engine.getClass());
    // evaluate JavaScript statement
    try {
      engine.eval("print('Hello, World!');");
    } catch (final ScriptException se) {
      se.printStackTrace();
    }
  }
}