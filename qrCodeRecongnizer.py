import requests
import sys,json
from PIL import Image
import cv2
import zbarlight
import numpy as np


r = requests.get('http://192.168.8.102:8080/video', auth=('user', 'password'), stream=True)

def get_frame_from_stream(r):
    if(r.status_code == 200):
        bytes_buffer = bytes()
        for chunk in r.iter_content(chunk_size=1024):
            bytes_buffer += chunk
            a = bytes_buffer.find(b'\xff\xd8')
            b = bytes_buffer.find(b'\xff\xd9')
           # print("run")
            if a != -1 and b != -1:
                jpg = bytes_buffer[a:b + 2]
                bytes_buffer = bytes_buffer[b + 2:]
                i = cv2.imdecode(np.fromstring(
                    jpg, dtype=np.uint8), cv2.IMREAD_COLOR)
                yield i

    else:
        print("Received unexpected status code {}".forqmat(r.status_code))
        return None

def main():
        # main Work
    for frame in get_frame_from_stream(r):
        #print(frame)

        small_frame = cv2.resize(frame, (0, 0), fx=0.25, fy=0.25)

        # Converts image to grayscale.
        gray = cv2.cvtColor(small_frame, cv2.COLOR_BGR2GRAY)

        # Uses PIL to convert the grayscale image into a ndary array that ZBar can understand.
        image = Image.fromarray(gray)
        width, height = image.size
        codes = zbarlight.scan_codes('qrcode', image)

        # Prints data from image.
        # for decoded in zbar_image:
        #   print(decoded.data)
        print('QR codes: %s' % codes)





        cv2.imshow('imafast', frame)
        if cv2.waitKey(25) & 0xFF == ord('q'):
            break




#start process
if __name__ == '__main__':
    main()
