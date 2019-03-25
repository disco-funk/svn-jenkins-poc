ARCH=OSX

program: outDir Module3
	g++ src/thirdthing.cpp -I./include -o $(ARCH)/app ./$(ARCH)/Module3.so

Module3:
	g++ -shared src/Module3.cpp -I./include -o $(ARCH)/Module3.so

outDir:
	mkdir -p $(ARCH)

clean:
	rm -r $(ARCH)