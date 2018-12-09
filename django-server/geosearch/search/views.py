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
        context = {}
        return render(request, template, context)

    city = distance(float(latitude), float(longitude))
    print("City = ",city)
    p1 = subprocess.Popen(["java", "-jar", "geosearch_lucene.jar", query, latitude, longitude, city],
                          stdin=subprocess.PIPE,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    results = p1.stdout.readlines()
    p1.communicate()
    row_entry = []
    data = []
    for i in range(len(results)):
        if i>0 and i%4==0:
            data.append(row_entry)
            row_entry = []
        row_entry.append(results[i])
    context = {
        'query' : query,
        'results': data
    }
    if data:
        print(data)
    return render(request, template, context)
