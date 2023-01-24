EXEC = PersonalFinance.jar
ENTRY = Main
BINDIR = ./bin

.PHONY: init build run jar
init:
	mkdir -p $(BINDIR)
	cp -r assets $(BINDIR)/assets

build:
	javac -d $(BINDIR) $(ENTRY).java

run:
	java -cp $(BINDIR) Main

jar:
	jar cmf META-INF/MANIFEST.MF $(EXEC) -C bin/ .
