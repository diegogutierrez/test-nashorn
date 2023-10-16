# Test nashorn standalone library

The nashorn support on the JDK was dropped in JDK 15(https://www.oracle.com/java/technologies/javase/15-relnote-issues.html)

If, you are using Nashorn in JDK11 and want to upgrade to JDK17, it won't work.

From the documentation: https://github.com/szegedi/nashorn/wiki/Using-Nashorn-with-different-Java-versions. There is an
Open Nashron project that can we included as a dependency in your project.


It's still possible to use the Open Nashorn library(as dependency) in JDK11 to JDK15. The problem: 
there will be two nashorn in the classpath. From the same documentation:
```
standalone Nashorn, if you loaded Nashorn as a module through the --module-path option as it gets precedence there,
```

It's possible to use Open nashorn(standalone) and make the code use that engine instead the built-in one(from the JDK)

The goal of this project is to test what's required to use Open nashorn in JDK11-JDK15.


## Build the executables

- run:
```
./gradlew clean jar
```

- Check the generated jar file:

```
$ tree build/libs/
build/libs/
└── test-nashorn-0.0.1-SNAPSHOT.jar
```

- The project contains a `nashorn` folder in the root. That folder contains the open nashorn jars and its dependencies.
To build that directory, use the gradle task: `copyToLib`

- The code is just creating a ScriptEngine instance of the nashorn engine and printing a simple "hello world" message from
a script. The code is also printing the class of the ScriptEngine.

## Establish Open Nashorn precedence

Since JDK11-JDK15 contains a built-in nashorn, and we want to use Open nashorn, there will be two nashorn libraries on the 
classpath. To use Open nashorn we need to use the --module-path(from the documentation: https://github.com/szegedi/nashorn/wiki/Using-Nashorn-with-different-Java-versions)


- Check the JDK version:
```
$ java -version
openjdk version "11.0.20.1" 2023-08-24
OpenJDK Runtime Environment (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04)
OpenJDK 64-Bit Server VM (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)

```

- Run the program without --module-path parameter:
```
$ java  -jar build/libs/test-nashorn-0.0.1-SNAPSHOT.jar 
Warning: Nashorn engine is planned to be removed from a future JDK release
class jdk.nashorn.api.scripting.NashornScriptEngine
Hello, World!

```
The code is using the built-in nashorn engine(the class package starts with `jdk.`)

- Run the program with --module-path parameter:

```
$ java  --module-path=nashorn -jar build/libs/test-nashorn-0.0.1-SNAPSHOT.jar 
class org.openjdk.nashorn.api.scripting.NashornScriptEngine
Hello, World!
```

The code now is using the Open nashorn engine, the class package starts with `org.nashorn...`
