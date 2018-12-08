import subprocess
import os
query = "Sri City"
latitude = str(13.71)
longitude = str(79.99)
city = "Sri City"
path = os.getcwd()
path.replace("/django-server/geosearch", "/geosearch_lucene")
# os.chdir('../../geosearch_lucene')
print(path)
p1 = subprocess.Popen(["java", "-jar", "geosearch_lucene.jar", query, latitude, longitude, city],
                          stdin=subprocess.PIPE,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
results = p1.stdout.readlines()
p1.communicate()
print(results)