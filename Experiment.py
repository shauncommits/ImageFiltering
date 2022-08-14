import os
import subprocess

# list of the images I used from smallest to biggest in size
imageList = ["img1.jpg","img4.jpg","img3.jpg","img5.jpg","img2.jpg"] 

# method to run the programs and take parameters of the program to be ran and the window size to be used in that program
def process(run,windowSize): 
    # loop through the list of images 
    for img in imageList: 
        # for each image I run the program and window size received as arguments 5 times to determine the optimum time the program takes to execute
        for i in range(0,5): 
            os.system("java -cp bin "+run+ " "+img+" "+img+" "+str(windowSize))


# call the the method with the following arguments for each of the 4 programs
process("MedianFilterSerial",3)
process("MedianFilterParallel",3)
process("MeanFilterSerial",3)
process("MeanFilterParallel",3)
process("MedianFilterSerial",5)
process("MedianFilterParallel",5)
process("MeanFilterSerial",5)
process("MeanFilterParallel",5)
process("MedianFilterSerial",11)
process("MedianFilterParallel",11)
process("MeanFilterSerial",11)
process("MeanFilterParallel",11)
process("MedianFilterSerial",15)
process("MedianFilterParallel",15)
process("MeanFilterSerial",15)
process("MeanFilterParallel",15)
