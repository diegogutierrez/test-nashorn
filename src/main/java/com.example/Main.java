package com.example;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
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
      //docs: https://cr.openjdk.org/~sundar/8015969/webrev.00/raw_files/new/docs/JavaScriptingProgrammersGuide.html

      //execute script directly:
      String script = "print('Hello, World!')";
      Object res = engine.eval(script);
      System.out.println("res: " + res);
      System.out.println("res: " + res.getClass());

      System.out.println("========");


      //invoke scripting method
      System.out.println("is compilable:" + (engine instanceof Compilable));
      //compile
      script = "function() { print('Hello, World!'); return 1; }";//the script is a function, nothing gets executed when run the script
      CompiledScript compiledScript = ((Compilable) engine).compile(script);
      Object finalScript = compiledScript.eval();
      res = ((Invocable) engine).invokeMethod(finalScript, "call", finalScript);//this triggers the script execution
      System.out.println("response: " + res);
      System.out.println("response: " + res.getClass());

    } catch (final ScriptException se) {
      se.printStackTrace();
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
}