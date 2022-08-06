JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= MeanFilterSerial.class MedianFilterSerial.class MeanFilterParallel.class MedianFilterParallel.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run-MedianFilterSerial: $(CLASS_FILES)
	@java -cp bin MedianFilterSerial $(ARGS)

run-MedianFilterParallel: $(CLASS_FILES)
	@java -cp bin MedianFilterParallel $(ARGS)

run-MeanFilterSerial: $(CLASS_FILES)
	@java -cp bin MeanFilterSerial $(ARGS)

run-MeanFilterParallel: $(CLASS_FILES)
	@java -cp bin MeanFilterParallel $(ARGS)

generate-Java-Doc:
	javadoc -d doc src/*.java