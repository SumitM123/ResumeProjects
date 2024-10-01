import serial
import ball_detection as bd


class Encoding:
    def send_data(self, ball_data, s):   # ball_data: array from cv, s: serial port opened by cv
        data_string = "".join("\nD{:.2f}\nA{:.2f}\n".format(round(ball_data[0], 2), round(ball_data[1], 2)))
        # ball_data[0] = distance, #ball_data[1] = angle
        # one line break: new value (angle), two line breaks: end of data
        print("X " + data_string + "E")  # testing code
        s.write(bytes("X" + data_string + "E", "utf-8"))  # "X" = start, "E" = end
