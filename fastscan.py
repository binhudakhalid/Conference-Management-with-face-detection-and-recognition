import requests
import sys,json
import face_recognition
import cv2
# for feeding data
import glob,os
from pathlib import Path
import numpy as np
from time import gmtime, strftime


home = str(os.path.dirname(os.path.abspath(__file__))) + "/" # "/../../"
file_names = glob.glob(home + "/known_people/*.jp*g")



#end
#Read data from stdin
def read_in():
    lines = sys.stdin.readline()
    # Since our input would only be having one line, parse our JSON data from that
    return lines

#Function to check if the person is authorised based on certain parameters


def authorised(name):
    # Assuming if person is not in Database then it is Un-authorised
    return not "Unknown" in name

r = requests.get('http://192.168.8.102:8080/video', auth=('user', 'password'), stream=True)
#r = requests.get('http://192.168.1.4:8080/video', auth=('user', 'password'), stream=True)

def get_frame_from_stream(r):
    if(r.status_code == 200):
        bytes_buffer = bytes()
        for chunk in r.iter_content(chunk_size=1024):
            bytes_buffer += chunk
            a = bytes_buffer.find(b'\xff\xd8')
            b = bytes_buffer.find(b'\xff\xd9')
            #print("run")
            if a != -1 and b != -1:
                jpg = bytes_buffer[a:b + 2]
                bytes_buffer = bytes_buffer[b + 2:]
                i = cv2.imdecode(np.fromstring(
                    jpg, dtype=np.uint8), cv2.IMREAD_COLOR)
                yield i

    else:
        print("Received unexpected status code {}".format(r.status_code))
        return None

#while True:
 #   if img is not None:
  #      img = get_frame_from_stream(r)
   #     cv2.imshow('i', img)
    #    cv2.waitKey(0)
    #else:httcamgene.py
     #   break


def main():
    #print("nothing Freat")

    home = str(os.path.dirname(os.path.abspath(__file__))) + "/"  # "/../../"
    #print("home : " + home)

    known_encodings_file_path = home + "/data/known_encodings_file.csv"
    people_file_path = home + "/data/people_file.csv"
    # For storing the encoding of a face
    known_encodings_file = Path(known_encodings_file_path)
    if known_encodings_file.is_file():
        known_encodings = np.genfromtxt(known_encodings_file, delimiter=',')
    else:
        known_encodings = []

    # #For Storing the name corresponding to the encoding
    people_file = Path(people_file_path)
    if people_file.is_file():
        people = np.genfromtxt(people_file, dtype='U', delimiter=',')
    else:
        people = []
    # Initialize some variables
    face_locations = []
    face_encodings = []
    face_names = []
    process_this_frame = True

    # main Work
    for frame in get_frame_from_stream(r):
        #print(frame)

        small_frame = cv2.resize(frame, (0, 0), fx=0.25, fy=0.25)
        if process_this_frame:
            #Find the face locations
            face_locations = face_recognition.face_locations(small_frame)
            #Find the face encodings 128 Dimensional!!
            face_encodings = face_recognition.face_encodings(small_frame, face_locations)

            face_names=[]
            other = 0 #Count of un-authorised people
            for face_encoding in face_encodings:
                match = face_recognition.compare_faces(known_encodings, face_encoding,0.5)
                name = "Unknown"

                #Find if this person is in the present people array
                for i in range(len(match)):
                    if match[i]:
                        name = people[i]
                        print(name+ "\n")
                        showtime = strftime("%Y-%m-%d %H:%M:%S", gmtime())
                        file = open("testname.txt", "a")
                        file.write(showtime + "  " + name + "\n")

                        file.close()
                        break
                #Change it, run the loop to find no. of Unknown
                if "Unknown" in name:
                    other += 1
                    name += str(other)
                face_names.append(name)
            print(face_names, flush=True)
        process_this_frame = not process_this_frame

        # Display the border
        for (top, right, bottom, left), name in zip(face_locations, face_names):
            # Scale up the coordinates by 4 to get face
            top *= 4
            right *= 4
            bottom *= 4
            left *= 4

            # Assuming person in authenticated
            color = (0, 255, 0)  # GREEN
            if not authorised(name):
                # Unauthenticated person
                color = (0, 0, 255)  # RED
                # print so that parent process in Node.js can use it
                # print(name,flush=True)

            # Display border
            cv2.rectangle(frame, (left, top), (right, bottom), color, 2)

            # Draw a label with name
            cv2.rectangle(frame, (left, bottom - 35), (right, bottom), color, cv2.FILLED)
            font = cv2.FONT_HERSHEY_DUPLEX
            cv2.putText(frame, name, (left + 6, bottom - 6), font, 1.0, (255, 255, 255), 1)
        # Display the resulting image with borders and names
        cv2.imshow('imafast', frame)
        if cv2.waitKey(25) & 0xFF == ord('q'):
            break



      #  if cv2.waitKey(1) == 27:
       #     cv2.destroyAllWindows()
            #exit(0)
        #print("fram")

    """
for frame in get_frame_from_stream(r):
    print (frame)


   # img = r
    print("int   " + str(r) )
    cv2.imshow('imafes', frame)
    if cv2.waitKey(1) == 27:
        exit(0)
    print("fram")"""

#start process
if __name__ == '__main__':
    main()


#recog.append(people[i])
"""      print("List :" + str(recog))
                        mySet = set(recog)
                        print("Set :" + str(mySet))

                        if people[i] in mySet:
                             name = str(people[i]) + " (Already)"
                        else:
                            name = people[i]


                            print(name+ "\n")
                            file = open("testname.txt", "a")
                            file.write(name + "\n")

                            file.close()
                        break"""""
