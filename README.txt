Image Filtering


This is the format to run the experiment on the console: java -cp bin <name of the program you wish to run> <image input with extension> <image output with extension> <window size width> 

First: make clean

Complete example: java -cp bin MeanFilterSerial img1.jpg img1.jpg 3

Alternatively, you can use the MAKEFILE to run any program
E.g. If you want to run MeanFilterSerial you can type instead: 
make run-MeanFilterSerial ARGS="<image input with extension> <image output with extension> <window size width>"

Complete example: make run-MeanFilterSerial ARGS="img1.jpg img1.jpg 3"

If you want to run the whole experiment using all sliding- window widths I used and all the images you can run
the Experiment.py file by typing on the console: python3 Experiment.py 

If you want to generate javadocs from the source files you can type on the console: javadoc -d doc src/*.java
Or alternatively use the makefile by typing: make javaDoc


Hint: use Ubuntu or vs code editor to run the experiment. However, whichever way you prefer would suffice.

To check the output filter on the image, look for the image called output.jpg inside the imageOutput folder!

