import cv2
from numpy import array


class ColorDetectorHSV:

    BLUE_LOWER = array([70, 50, 50])
    BLUE_UPPER = array([120, 255, 255])

    RED_LOWER1 = array([0, 50, 50])
    RED_UPPER1 = array([10, 255, 255])

    RED_LOWER2 = array([170, 50, 50])
    RED_UPPER2 = array([180, 255, 255])

    def __init__(self, image):
        self.image = image

    def detect(self, color, return_all=False):

        blurred = cv2.blur(self.image, (15, 15))
        hsv = cv2.cvtColor(blurred, cv2.COLOR_BGR2HSV)

        if color == "blue":
            mask = cv2.inRange(hsv, self.BLUE_LOWER, self.BLUE_UPPER)
        elif color == "red":
            mask1 = cv2.inRange(hsv, self.RED_LOWER1, self.RED_UPPER1)
            mask2 = cv2.inRange(hsv, self.RED_LOWER2, self.RED_UPPER2)
            mask = cv2.bitwise_or(mask1, mask2)
        else:
            raise Exception('color must be "blue" or "red"')

        if return_all:
            return blurred, hsv, mask
        else:
            return mask
