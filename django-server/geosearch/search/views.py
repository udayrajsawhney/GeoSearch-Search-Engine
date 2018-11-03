from django.shortcuts import render
from django.http import HttpResponse
import subprocess, os


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
    os.chdir('/Users/udaysawhney/Documents/repositories/Project-Geosearch/geosearch_lucene')
    p1 = subprocess.Popen(["java", "-jar", "geosearch_lucene.jar", query], stdin=subprocess.PIPE,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    p2 = subprocess.Popen(["python", "test.py"], stdin=p1.stdout,stdout=subprocess.PIPE)
    print("Results in python")
    results = p2.stdout.readlines()
    for result in results:
        print(type(result))
    # print(type(results))
    p1.stdout.close()
    out, err = p2.communicate()
    context = {
        'results': results
    }
    return render(request, template, context)
