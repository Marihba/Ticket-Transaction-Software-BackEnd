javac -Xlint:unchecked Controller.java
export CLASSPATH=$CLASSPATH:$JUNIT
javac TestRunner.java
java TestRunner
