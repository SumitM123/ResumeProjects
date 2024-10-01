import cv2
import numpy as np
import imutils

KNOWN_DIAMETER_IN = 9.5  # in
# ADJ_FOCAL_LENGTH = 1480.1
FOCAL_LENGTH = 1367.043233  # 94% accuracy
RADIUS_THRESH = 85
MIN_BORDERS = 6


class BallDetection:

    def __init__(self, frame):
        self.frame = frame
        self.x_val = int(frame.shape[1])
        self.y_val = int(frame.shape[0])

    def detect_ball(self):
        distance = -1
        angle = -1
        frame = cv2.cvtColor(self.frame, cv2.COLOR_BGR2GRAY)
        self.prep_frame()
        cnts = cv2.findContours(frame, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
        cnts = imutils.grab_contours(cnts)

        if len(cnts) > 0:
            for cnt in cnts:
                ((x, y), radius) = cv2.minEnclosingCircle(cnt)
                if radius >= RADIUS_THRESH:
                    # determine the num of border points on the contour (ex: differentiates between circle and rect)
                    perimeter = cv2.arcLength(cnt, closed=True)
                    borders = cv2.approxPolyDP(curve=cnt, epsilon=0.0085 * perimeter, closed=True)
                    if len(borders) > MIN_BORDERS:
                        angle = self.find_angle(x, y)
                        # print(radius)
                        distance = self.find_distance(radius)
                    cv2.circle(self.frame, (int(x), int(y)), int(radius), (0, 255, 0), 2)
        return distance, angle

    def transform(self, x, y):
        # transform coordinates to a system with the origin at the bottom-middle of screen
        dims = self.frame.shape
        x = x - (dims[1] / 2)
        y = dims[0] - y
        return x, y

    def find_angle(self, x, y):
        tsfm_x, tsfm_y = self.transform(x, y)
        angle_rad = np.arctan(abs(tsfm_x) / tsfm_y)
        angle_deg = round(np.degrees(angle_rad), 2)

        # angle annotations
        cv2.putText(self.frame, "Angle: " + str(angle_deg) + " degrees", (50, 30), cv2.FONT_HERSHEY_SIMPLEX, 1,
                    (200, 200, 0), 1)
        cv2.line(self.frame, (int(self.x_val / 2), self.y_val), (int(x), int(y)), (0, 0, 255), 2)
        return angle_deg

    def find_distance(self, radius):
        dist = (KNOWN_DIAMETER_IN * FOCAL_LENGTH) / (radius * 2)
        # print("Distance: " + str(dist))
        return dist

    def prep_frame(self):
        # draw axes on screen for visualization
        cv2.line(self.frame, (int(self.x_val / 2), self.y_val), (int(self.x_val / 2), 0), (0, 0, 255), 2)
        cv2.line(self.frame, (0, self.y_val), (self.x_val, self.y_val), (0, 0, 255), 3)
