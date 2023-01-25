EXEC = PersonalFinance.jar
ENTRY = Main
BINDIR = ./bin

.PHONY: init build run jar gendoc
init:
	mkdir -p $(BINDIR)
	cp -r assets $(BINDIR)/assets
	cp jOpenDocument.jar $(BINDIR)

build:
	javac -cp ".:jOpenDocument.jar" -d $(BINDIR) $(ENTRY).java

run:
	java -cp "$(BINDIR):jOpenDocument.jar" Main

jar:
	jar cmf META-INF/MANIFEST.MF $(EXEC) -C bin/ .

gendoc:
	doxygen