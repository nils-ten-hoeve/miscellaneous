Writen by Nils ten Hoeve on 17-4-2007

This project creates a simple executable that starts a jar with the same name as the executable.
The JavaLauncher will try to start the jar with command: java -jar JavaLauncher.jar

Note that:
- the jar file must have a manifest file with main-class and class-path variables
- all required jar files need to be in the same directory as the JavaLauncher.exe
- you need to rename this executable to the name of the executable jar file. I.e.:
  if the jar file is named MyApp.jar then rename JavaLauncher.exe to MyApp.exe 