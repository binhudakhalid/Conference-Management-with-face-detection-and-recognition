import glob,os
from pathlib import Path
import numpy as np
import pathlib

import face_recognition

home = str(os.path.dirname(os.path.abspath(__file__))) + "/"  #"/../../"
#print(home)
file_names = glob.glob(home + "known_people/*.jp*g") #get filename from knowfloder waqas mama
#print("file_names : " + str(file_names))

known_encodings_file_path = home + "data/known_encodings_file.csv"
people_file_path = home + "data/people_file.csv"




#deleete

p = pathlib.Path(known_encodings_file_path)
pfile = pathlib.Path(people_file_path)

if p.is_file():  # or p.is_dir() to see if it is a directory
    os.remove(known_encodings_file_path)

if pfile.is_file():  # or p.is_dir() to see if it is a directory
    os.remove(people_file_path)


#os.remove(known_encodings_file_path)
#os.remove(people_file_path)






# #For storing the encoding of a face
known_encodings_file = Path(known_encodings_file_path)
if known_encodings_file.is_file():
    known_encodings = np.genfromtxt(known_encodings_file, delimiter=',')
else:
    known_encodings = []

# #For Storing the name corresponding to the encoding
people_file = Path(people_file_path)
if people_file.is_file():
    people = np.genfromtxt(people_file, dtype='U',delimiter=',')
else:
    people = []

known_encodings_new = []
people_new = []

for file_name in file_names:
    temp = file_name.split('/')
    image_file_name = temp[-1]
    person_name = image_file_name.split('.')[0]
    if len(people) and person_name in people:
        # print("found {}".format(person_name))
        continue
    # print("Image name is {}".format(image_file_name.split('.')[0]))
    image_name = face_recognition.load_image_file(file_name)
    image_face_encoding = face_recognition.face_encodings(image_name)
    if len(image_face_encoding) == 1:
        face_encoding = image_face_encoding[0]
        known_encodings_new.append(face_encoding)
        people_new.append(person_name)

    else:
        print("NOTE: {} has more than one face.".format(image_file_name))

known_encodings_save = np.array(known_encodings_new)
people_save = np.array(people_new)

#print(known_encodings_save)

#Print the new people added for debugging( CAUTION: Disable for large cases)
# print("people = {} and {}".format(people_save, type(people_save)))

print("i am here :: " + str(known_encodings_file))

# Save the face_encodings
if known_encodings_file.is_file():
    with open(known_encodings_file, 'ab') as f:
        np.savetxt(f,known_encodings_save,delimiter=',')
else:
    np.savetxt(known_encodings_file_path,known_encodings_save,delimiter=',' )

# Save the people names
if people_file.is_file():
    with open(people_file, 'ab') as f:
        np.savetxt(f,people_save,delimiter=',', fmt="%s")
else:
    np.savetxt(people_file_path, people_save, delimiter=',', fmt="%s")


print("... done Tranning ...");
