import serial
from video_live_generator import VideoLiveGenerator
import color_detector
from ball_detection import BallDetection
from encoding import Encoding


auto_camera = VideoLiveGenerator(1)

# open the serial port
ser = serial.Serial('/dev/')
print(ser.name)

# create the encoding object to use to pass data via the serial port
encoding_input = Encoding()

# run ball detection indefinitely
while True:
    next_img = auto_camera.get_next_frame()
    # get the binary image
    binary_image = color_detector.detect(next_img, "blue")
    # get the ball distance and angle from the robot
    ball_detector = BallDetection(next_img)
    cv_info = ball_detector.detect_ball()
    ball_info = [float(cv_info[0]), float(cv_info[1])]
    encoding_input.send_data(ball_info, ser)
