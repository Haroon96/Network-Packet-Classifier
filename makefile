JC = javac
RM = rm

default: build

clean:
	rm -rf build/

build: clean
	mkdir -p build
	javac -cp src:lib/forms_rt.jar:resources:data src/com/haroon/Main.java -d build
	cp -r lib resources data build
