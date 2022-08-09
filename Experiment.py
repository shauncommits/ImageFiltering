import os
import subprocess

imageList = ["img1.jpg","img4.jpg","img5.jpg","img3.jpg","img6.jpg","img2.jpg"]

def process(run,windowSize):
    for img in imageList:
        for i in range(0,4):
            os.system("java -cp bin "+run+ " "+img+" "+img+" "+str(windowSize))



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
