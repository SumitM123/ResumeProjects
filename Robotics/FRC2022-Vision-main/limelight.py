import cv2
import numpy as np

# Camera Constants
KNOWN_DIAMETER_IN = 9.5  # in
FOCAL_LENGTH = 195.669  # 94% accuracy
RADIUS_THRESH = 18
MIN_BORDERS = 5
DIM = 320
CAMERA_TILT_DOWNWARDS = 20

# DISTANCE EQUATION
# Square Root
A = -1.6549
B = 0.216359
H = 184.484
K = 62.9699


# CV Input Code
def draw_image_annotations(image, angle, distance):
    cv2.putText(image, f"Angle: {angle} deg.", (120, 20), 0, 0.65, (255, 0, 255), 2)
    cv2.putText(image, f"Distance: {distance} in.", (120, 50), 0, 0.65, (255, 0, 255), 2)


# CV Color Detection Code
RED_LOWER = np.array([0, 50, 140])
RED_UPPER = np.array([200, 150, 255])

# BLUE_LOWER = np.array([0, 0, 0]) # YUV
# BLUE_UPPER = np.array([255, 255, 95])
BLUE_LOWER = np.array([70, 50, 50])
BLUE_UPPER = np.array([120, 255, 255])


def detect(image, color):

    blurred = cv2.blur(image, (15, 15))

    if color == "Blue":
        hsv = cv2.cvtColor(blurred, cv2.COLOR_BGR2HSV)
        mask = cv2.inRange(hsv, BLUE_LOWER, BLUE_UPPER)
    elif color == "Red":
        yuv = cv2.cvtColor(blurred, cv2.COLOR_BGR2YUV)
        mask = cv2.inRange(yuv, RED_LOWER, RED_UPPER)
    else:
        raise Exception('color must be "Blue" or "Red"')
    return mask


# CV Ball Detection Code
# from imutils github page
def grab_contours(cnts):
    # if the length the contours tuple returned by cv2.findContours
    # is '2' then we are using either OpenCV v2.4, v4-beta, or
    # v4-official
    if len(cnts) == 2:
        cnts = cnts[0]

    # if the length of the contours tuple is '3' then we are using
    # either OpenCV v3, v4-pre, or v4-alpha
    elif len(cnts) == 3:
        cnts = cnts[1]

    # otherwise OpenCV has changed their cv2.findContours return
    # signature yet again and I have no idea WTH is going on
    else:
        raise Exception(("Contours tuple must have length 2 or 3, otherwise OpenCV changed their cv2.findContours return signature yet again. Refer to OpenCV's documentation in that case"))

    # return the actual contours array
    return cnts


class BallDetection:

    def __init__(self, frame):
        self.frame = frame
        self.x_val = int(frame.shape[1])
        self.y_val = int(frame.shape[0])

    def detect_ball(self):
        distance = -1
        area = -1
        angle = -1
        # frame = cv2.cvtColor(self.frame, cv2.COLOR_BGR2GRAY)
        frame = self.frame
        self.prep_frame()
        cnts = cv2.findContours(frame, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
        cnts = grab_contours(cnts)

        if len(cnts) > 0:
            for cnt in cnts:
                ((x, y), radius) = cv2.minEnclosingCircle(cnt)
                if radius >= RADIUS_THRESH:
                    # print(radius)
                    if cv2.contourArea(cnt) > area:  # finding the closest ball
                        area = cv2.contourArea(cnt)
                        distance = self.find_distance(area)
                        angle = self.find_angle(x, y)
                    cv2.circle(self.frame, (int(x), int(y)), int(radius), (0, 255, 0), 2)
        return [distance, angle]

    def find_angle(self, x, y):
        center = DIM/2
        diff = center - x
        angle = np.degrees(np.arctan(diff/FOCAL_LENGTH))
        # angle annotations
        cv2.putText(self.frame, "Angle: " + str(angle) + " degrees", (50, 30), cv2.FONT_HERSHEY_SIMPLEX, 1,
                    (200, 200, 0), 1)
        return angle

    def find_distance(self, area):
        # DIST USING CONTOUR AREA
        if B*area - H < 0:
            return -1
        dist = A*((B*area - H)**(1/2)) + K
        return dist

    def prep_frame(self):
        # draw axes on screen for visualization
        cv2.line(self.frame, (int(self.x_val / 2), self.y_val), (int(self.x_val / 2), 0), (0, 0, 255), 2)
        cv2.line(self.frame, (0, self.y_val), (self.x_val, self.y_val), (0, 0, 255), 3)


# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    alliance_color = "Red" if llrobot[0] == 0 else "Blue"
    binary_image = detect(image, "Red")

    ball_detect = BallDetection(binary_image)
    data = ball_detect.detect_ball()

    # draw image annotations, round to 2 decimal places for distance and
    # angle values
    draw_image_annotations(image, round(data[1], 2), round(data[0], 2))

    # record the distance and angle of the ball to the robot to send back to the robot
    llpython = [float(data[0]), float(data[1])]
    # print(f"distance: {data[0]}, angle: {data[1]}")

    # return the largest countour, modified image, and custom robot data - add llpython
    return [], image, llpython
