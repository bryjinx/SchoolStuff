export PATH := /usr/lib/jvm/java-8-openjdk-amd64/bin:$(PATH)
JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class: 
	 $(JC) $(JFLAGS) $*.java
CLASSES = \
    ParenthCheck.java \
    Parser.java \
    PrefixEval.java \
    KeyVal.java \
    SymbolHelper.java \
    BuildExp.java \
    ExpNode.java \
    IsMathExp.java \
    Condition.java \
    Lambda.java \
    LambdaHelper.java \
    LispInterMain.java 


MAIN = LispInterMain

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class 
	 $(JVM) $(MAIN)

clean:
	$(RM) *.class
