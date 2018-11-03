#!/usr/bin/python
import sys
import signal
import os
import subprocess

p = subprocess.Popen(["java", "test"], stdin=subprocess.PIPE)
p.stdin.write("Java is back\r\n".encode())
# os.killpg(os.getpgid(p.pid), signal.SIGTERM)
p.communicate()
# p.stdin.write("Second line\r\n")
# p.stdin.write("x\r\n") # this line will not be printed into the file
