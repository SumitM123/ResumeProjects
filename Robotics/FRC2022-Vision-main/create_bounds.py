import cv2
from pathlib import Path
import statistics
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("path")
path = parser.parse_args().path
Y = []
U = []
V = []
for file in Path(path).glob('**/*'):
    file = str(file)
    if "blue" not in file:
        continue
    test = cv2.imread(path + file)
    correct = cv2.imread(
        f"/Users/akshatmehta/Downloads/test_images_revision_2/{file[file.index('benchmark_') + len('benchmark_'):]}")
    correct = cv2.cvtColor(correct, cv2.COLOR_BGR2YUV)

    for i in range(0, len(test), 2):
        for j in range(0, len(test[i]), 2):
            if test[i][j][0] > 128:
                Y.append(correct[i][j][0])
                U.append(correct[i][j][1])
                V.append(correct[i][j][2])
print(f"Y: mean: {statistics.mean(Y)}, stdev: {statistics.stdev(Y)}")
print(f"U: mean: {statistics.mean(U)}, stdev: {statistics.stdev(U)}")
print(f"V: mean: {statistics.mean(V)}, stdev: {statistics.stdev(V)}")
