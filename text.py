from os import path

PATH = os.path.expanduser('~') + '\Desktop\input.txt'
try:
    os.remove(os.path.expanduser('~') + '\Desktop\input.txt')
except OSError:
    pass
