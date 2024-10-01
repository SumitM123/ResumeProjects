# This file is used to grab images from the camera when the respective keys on the keyboard are
# pressed. This is used to get test images from the webcam and not intended for use in the actual
# competition

import cv2
from video_live_generator import VideoLiveGenerator

input = VideoLiveGenerator(2)

print("ready to capture image")
# create a window first to allow cv2.waitKey(0) to work
image = input.get_next_frame()
cv2.imshow("Saved Image", image)

while True:
    try:
        # if the 't' key is pressed (t for take photo), then save the image gotten from the camera
        key_pressed = cv2.waitKey(0)
        if key_pressed == ord('t'):
            image = input.get_next_frame()
            if not input.is_capturing():
                print("\nno frames have been grabbed, aborting process")
                break
            cv2.imwrite("lighting_color_distance_#_angle_#_background_elements_Y.jpg", image)
            cv2.imshow("Saved Image", image)
        # else if the 's' key is pressed (s for stop), stop waiting for images
        elif key_pressed == ord('s'):
            print("\nquitting")
            break
    # handle the case when the program is interrupted (control + c)
    except KeyboardInterrupt:
        print("\ninterrupted")
        break

# close the camera
input.close_camera()
print("camera closed")
# destroy all the image windows shows by cv2.imshow()
cv2.destroyAllWindows()
