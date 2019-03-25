ARCH=OSX

program: outDir Module2
	g++ src/otherthing.cpp -I./include -o $(ARCH)/app ./$(ARCH)/Module2.so

Module2:
	g++ -shared src/Module2.cpp -I./include -o $(ARCH)/Module2.so

outDir:
	mkdir -p $(ARCH)

clean:
	rm -r $(ARCH)