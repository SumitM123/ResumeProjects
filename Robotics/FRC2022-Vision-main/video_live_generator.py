# this file will be used to get images from the live camera feed

import cv2
import subprocess
from subprocess import CalledProcessError


class VideoLiveGenerator():
    def __init__(self, camera_port=0):
        self.camera_port = camera_port
        # stores the camera input video stream object
        self.live_input = cv2.VideoCapture(self.camera_port)
        # keeps track if the camera is still recording video
        self.capturing = False
        # call the method to setup the camera
        self.set_up_camera()

    def get_next_frame(self):
        # read() combines the get() and retrive() methods, gets the next image from the camera video feed
        self.capturing, image = self.live_input.read()
        return image

    def set_up_camera(self):
        camera_path = "/dev/video" + str(self.camera_port)

        # set the width and height property of the image
        self.live_input.set(cv2.CAP_PROP_FRAME_WIDTH, 1920)
        self.live_input.set(cv2.CAP_PROP_FRAME_HEIGHT, 1080)

        # use subprocess to set camera settings
        try:
            subprocess.check_call(["v4l2-ctl", "-d", camera_path, "-c", "white_balance_temperature_auto=0"])
            subprocess.check_call(["v4l2-ctl", "-d", camera_path, "-c", "white_balance_temperature=5400"])
            subprocess.check_call(["v4l2-ctl", "-d", camera_path, "-c", "exposure_auto=1"])
            subprocess.check_call(["v4l2-ctl", "-d", camera_path, "-c", "exposure_absolute=5"])
            print(subprocess.check_output(["v4l2-ctl", "-d", camera_path, "-C", "exposure_absolute"]))
            print("Camera setup complete")
        except CalledProcessError as cpe:
            print("Called Process Error occurred. Camera setup incomplete!")
            print("Error status:" + str(cpe.returncode))
        except FileNotFoundError:
            print("v4l2 not found. Camera setup incomplete!")

    def is_capturing(self):
        return self.capturing

    def close_camera(self):
        self.live_input.release()
