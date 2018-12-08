from django.shortcuts import render
from django.http import HttpResponse
import subprocess
import os

from django.views.decorators.csrf import csrf_exempt

from search.latlong import distance


def search(request):
    template = 'index.html'
    context = {

    }
    return render(request, template, context)


@csrf_exempt
def results(request):
    template = 'results.html'
    path = os.getcwd()
    path = path.replace("django-server/geosearch", "geosearch_lucene")
    os.chdir(path)

    query = request.GET.get('query')
    print(request.GET)
    if request.GET.get('loc'):
        print("here")
        if len(request.GET.get('loc')) != 0:
            latlong = request.GET.get('loc')
            latitude = latlong.split(',')[0]
            longitude = latlong.split(',')[1]
    else:
        context = {
            'results': "Unable to access location"
        }
        return render(request, template, context)

    city = distance(float(latitude), float(longitude))
    print("City = ",city)
    p1 = subprocess.Popen(["java", "-jar", "geosearch_lucene.jar", query, latitude, longitude, city],
                          stdin=subprocess.PIPE,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    results = p1.stdout.readlines()
    p1.communicate()
    context = {
        'results': results
    }
    print(results[0])
    return render(request, template, context)
