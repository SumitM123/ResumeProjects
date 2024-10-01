import cv2
from video_live_generator import VideoLiveGenerator

camera_stream = VideoLiveGenerator()

for i in range(100):
    image = camera_stream.get_next_frame()
    cv2.imwrite(f"image_{i}.jpg", image)
    print("Testing")
