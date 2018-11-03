import subprocess, os
os.chdir('/Users/udaysawhney/Documents/repositories/project-geosearch/geosearch')
p = subprocess.Popen(["java", "-jar","geosearch.jar","SriCity"], stdin=subprocess.PIPE)
p.communicate()
# line = p.stdout.readline()
# print(line)


'''
os.chdir('/Users/udaysawhney/Documents/repositories/project-geosearch/geosearch')
    p = subprocess.Popen(["java", "-jar","geosearch.jar",query], stdin=subprocess.PIPE)
    p.communicate()
'''