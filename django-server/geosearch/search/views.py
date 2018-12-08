from django.shortcuts import render
from django.http import HttpResponse
import subprocess
import os


# def search(request):
#     return HttpResponse("Java will be back")
from search.latlong import distance


def search(request):
    template = 'index.html'
    context = {

    }
    return render(request, template, context)


def results(request):
    template = 'results.html'
    path = os.getcwd()
    print("initial path ",path)
    longitude = 0.0
    latitude = 0.0
    query = request.GET.get('query')
    if request.GET.get('loc') != None:
        latlong = request.GET.get('loc')
        print(latlong)
        latitude = latlong.split(',')[0]
        longitude = latlong.split(',')[1]
    else :
        latitude = 13.52
        longitude = 79.99

    print(query)
    print(latitude)
    print(longitude)
    city = distance(float(latitude),float(longitude))
    # print(city)
    os.chdir('../../geosearch_lucene')
    print("needed path ",path)
    # os.chdir(
    #     '/Users/udaysawhney/Documents/repositories/GeoSearch-Search-Engine/geosearch_lucene')
    p1 = subprocess.Popen(["java", "-jar", "geosearch_lucene.jar", query, latitude, longitude, city], stdin=subprocess.PIPE,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    results = p1.stdout.readlines()
    # print(results)
    p1.communicate()
    context = {
        'results': results
    }
    return render(request, template, context)
