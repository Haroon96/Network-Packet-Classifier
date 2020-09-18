# Network-Packet-Classifier
A Java application that monitors network traffic and generates charts detailing the percentage of packets targeted towards a specific port number.
The protocol running these packets can be identified using these port numbers but is not entirely accurate due to inherent issues in the methodology.
The applications lets you define your own protocols and also includes several pre-defined protocols.

## Requirements
Requires Java and JavaFX JRE and JDK. Both come bundled in Oracle's JDK. Users on Linux typically use OpenJDK and may need to setup support for JavaFX.

The system must also have `tcpdump` (for Linux users) or its Windows equivalent `windump` configured and running.

## Running
Download the executable jar from the Release page or compile it locally.

## Compilation
The included `makefile` can be used to compile the source code. To run it after compilation, use `java -cp .:lib/forms_rt.jar:resources:data com.haroon.Main` inside the `build` folder.
    
## Screenshots
<div style="display: flex;">
	<img src="https://github.com/Haroon96/Network-Packet-Classifier/raw/gh-pages/img/1.png">
	<img src="https://github.com/Haroon96/Network-Packet-Classifier/raw/gh-pages/img/2.png">
	<img src="https://github.com/Haroon96/Network-Packet-Classifier/raw/gh-pages/img/3.png">
</div>
    
## [Demo](https://www.youtube.com/watch?v=ubxa0Ua8x1M)
