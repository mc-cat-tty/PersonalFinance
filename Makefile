BINDIR = ./bin
ENTRY = Main

.PHONY: init build run jar
init:
	mkdir -p $(BINDIR)
	cp -r assets $(BINDIR)/assets

build:
	javac -d $(BINDIR) $(ENTRY).java

run:
	java -cp $(BINDIR) Main
