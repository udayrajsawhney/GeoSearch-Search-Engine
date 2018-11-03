from __future__ import print_function
import sys

def main():
    # open the first argument on command line, or stdin if none is specified
    with open(sys.argv[1], 'r') if len(sys.argv) > 1 else sys.stdin as f:
        # read the whole contents and put it in memory
        lines = f.readlines()
        f.close()

    print(lines)
    # filter each line and replace 'g' characters by "gremlin",
    # upper or lowercase.
    for line in lines:
        print( line.strip() )

main()