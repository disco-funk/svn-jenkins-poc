ARCH=OSX

program: outDir Module1
	g++ src/something.cpp -I./include -o $(ARCH)/app ./$(ARCH)/Module1.so

Module1:
	g++ -shared src/Module1.cpp -I./include -o $(ARCH)/Module1.so

outDir:
	mkdir -p $(ARCH)

clean:
	rm -r $(ARCH)