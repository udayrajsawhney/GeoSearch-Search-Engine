#!/usr/bin/python

import subprocess

p = subprocess.Popen(["java", "MyClass"], stdin=subprocess.PIPE)
p.stdin.write("First line\r\n".encode())
# p.stdin.write("Second line\r\n")
# p.stdin.write("x\r\n") # this line will not be printed into the file