# Detection
Accurately detect a required target (either red or blue ball) on the field

## Input
* Image filename
* Specified color as a String (blue/red)

## Responsibilities 
* Identify HSV color ranges that correspond to each target (blue/red ball), especially at different lightings
* Convert outside of those color ranges on the image to black depending on the target

## Output
* Contours target to white over black background (cv2.findContours)

## Class structure 
* ColorDetector: returns an image with the target area contoured as white 

## API
* ColorDetector(): constructor
* detect(): returns contoured image



