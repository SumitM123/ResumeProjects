from pathlib import Path
import cv2
from color_detector import detect
import numpy as np
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("path")
path = parser.parse_args().path

blank = np.full((1080, 1920, 3), 255, dtype=np.uint8)


def show_image(title, image):
    cv2.imshow(title, image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def detect_and_show(file, color):
    image = cv2.imread(file)
    blurred, yuv, mask = detect(image, color, True)
    output = cv2.bitwise_and(blank, blank, mask=mask)
    show_image(
        f"Detecting {color} in {file[len(path):]}", np.vstack((np.hstack((image, blurred)), np.hstack((yuv, output)))))


def detect_and_save(file, color):
    image = cv2.imread(file)
    result = detect(image, color)
    cv2.imwrite(f"/Users/akshatmehta/Downloads/binary_yuv/{color}_{file[len(path):]}", result)


if __name__ == '__main__':
    for img in Path(path).glob("**/*"):
        img = str(img)
        if "blue" in img or "both" in img:
            detect_and_show(img, "blue")
        if "red" in img or "both" in img:
            detect_and_show(img, "red")
