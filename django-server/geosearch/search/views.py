from django.shortcuts import render
from django.http import HttpResponse
import subprocess
import os


# def search(request):
#     return HttpResponse("Java will be back")

def search(request):
    template = 'index.html'
    context = {

    }
    return render(request, template, context)


def results(request):
    template = 'results.html'
    path = os.getcwd()
    query = request.GET.get('query')
    print(query)
    os.chdir(
        '/Users/udaysawhney/Documents/repositories/GeoSearch-Search-Engine/geosearch_lucene')
    p1 = subprocess.Popen(["java", "-jar", "geosearch_lucene.jar", query], stdin=subprocess.PIPE,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    results = p1.stdout.readlines()
    print(results)
    p1.communicate()
    context = {
        'results': results
    }
    return render(request, template, context)
