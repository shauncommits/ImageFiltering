import os
import subprocess

imageList = ["img1.jpg","img2.jpg","img3.jpg","img4.jpg","img5.jpg","img6.jpg","img7.jpg"]

def process(run,windowSize):
    for img in imageList:
        for i in range(0,5):
            os.system("java -cp bin "+run+ " "+img+" "+img+" "+str(windowSize))



process("MedianFilterSerial",3)
process("MedianFilterParallel",3)
process("MeanFilterSerial",3)
process("MeanFilterParallel",3)