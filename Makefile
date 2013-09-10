# Java compiler
JAVAC = javac

# Creating a .class file
COMPILE = $(JAVAC)

# One of these should be the "main" class listed in Runfile
CLASS_FILES = Sexpression.class Interpreter.class

# The first target is the one that is executed when you invoke
# "make". 

all: $(CLASS_FILES) 

clean :
	`rm *.class`
# The line describing the action starts with <TAB>
%.class : %.java
	$(COMPILE) $<

