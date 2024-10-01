import cv2
import pandas as pd
import ball_detection as bd

img = cv2.imread("test_images/blue_light_blue_distance_50_angle_30_background_elements_N.jpg")
detect = bd.BallDetection(img)
dist, angle = detect.detect_ball()  # note: dist & angle are floats

if dist != -1 and angle != -1:
    data = f"[{dist}, {angle}"
    # encode the "data" variable and send over serial
