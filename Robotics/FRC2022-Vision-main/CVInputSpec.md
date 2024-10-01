# Vision Input
Uses cv2 API

# Responsibilities:
* open/close the camera
* Return the camera video feed as images represented by tuples using the bgr channels
* Field of View
* Handle multiple cameras (processes)

# Video Live Generator
* Returns current image frame 
* Captures video from the camera itself

# CameraData
* Stores all the camera parameters
* Focal length
* Camera tilt angle
* Ball radius

# Data Sender
* Uses the video_live_generator.py file and camera_data.py file to gather images from the camera(s).
* Use Multiprocessing to treat each camera as a seperate stream of video input (if more than one camera is used)

# Jetson

Involves a collection of shell scripts to run on the Jetson. run2022vision.sh is in the FRC2022-Vision repository and other setup/preparation scripts are in Jetson-Setup.

# Testing
## Test Image Set

Set of videos and pictures from the robot camera perspective for CV testing/training

* Different lighting conditions/shadows
* Different angles and distances from camera to ball
* Both ball colors
* One ball, multiple balls, no balls
* Images with bumpers/other colored field elements

## Test Image Script

Script to get images using OpenCV when a key on the keyboard is clicked. Will be used to collect test images.

# API
### From Video Live Generator
* get_next_frame(self) - returns the next frame from the video file
* is_capturing(self) - returns the capturing variable (the capturing variable tells if the camera is currently recording)
* close_camera(self) - closes the camera