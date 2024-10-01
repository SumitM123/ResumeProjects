# Ball Detection
Identify location of balls within binary image. Accurately calculate distances to all specific-color balls in frame and determine the angle to the closest ball. 

## Input
* Binary Image

## Output
* Distance and angle to closest ball
* Potential: On-screen annotations (angle and distance to closest ball)

## API
* detect_ball(): returns center-coordinates (x, y) and radius of all balls detected in frame
* find_angle(): returns angle to balls in frame
* transform(): transforms ball coordinates from top-left corner axes to bottom-middle axes  
* prep_frame(): draws redefined axes on frame for visualization



