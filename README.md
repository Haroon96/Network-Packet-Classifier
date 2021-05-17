# Network-Packet-Classifier
A Java application that monitors network traffic and generates charts detailing the percentage of packets targeted towards a specific port number.

The protocol running these packets can be identified using these port numbers but is not entirely accurate due to inherent issues in the methodology.
The application lets you define your own protocols and also includes several pre-defined protocols.

## Requirements
Requires Java and JavaFX. Both come bundled in Oracle's JDK 8 which is the recommended version. Users on Linux typically have OpenJDK installed and may need to setup support for JavaFX.

### Windows
1. Download and install [`npcap`](https://nmap.org/download.html).
2. Download [`WinDump for npcap`](https://github.com/hsluoyz/WinDump/releases) and add it to your PATH.
   - Either paste it in C:\Windows\System32 or the compiled/JAR directory.

### Ubuntu
1. Install `tcpdump` using `sudo apt install tcpdump`

## Running
Download the executable jar from the [Release](https://github.com/Haroon96/Network-Packet-Classifier/releases) page or compile it locally.

## Compilation
### Windows
The included `makefile.bat` runs the required commands for compiling on Windows. It generates `start.bat` in the `build` directory which can be used to run the program after compilation.

### Ubuntu
The included `makefile` can be used to compile the source code. After compilation, use `java -cp .:lib/forms_rt.jar:resources:data com.haroon.Main` inside the `build` folder to run the program.
    
## Screenshots
<div style="display: flex;">
	<img src="https://github.com/Haroon96/Network-Packet-Classifier/raw/gh-pages/img/1.png">
	<img src="https://github.com/Haroon96/Network-Packet-Classifier/raw/gh-pages/img/2.png">
	<img src="https://github.com/Haroon96/Network-Packet-Classifier/raw/gh-pages/img/3.png">
</div>
    
## [Demo](https://www.youtube.com/watch?v=ubxa0Ua8x1M)
