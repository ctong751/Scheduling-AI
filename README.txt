To compile and run my code, these steps need to be followed.
First, compile the three java files in the assignment7 package:
javac assignment7/*.java

Then create the jar file to be able to run:
jar cfe Scheduler.jar assignment7/Scheduler assignment7/Scheduler.class assignment7/Node.class

Finally, you can run the scheduling program:
java -jar Scheduler.jar <InputFile>