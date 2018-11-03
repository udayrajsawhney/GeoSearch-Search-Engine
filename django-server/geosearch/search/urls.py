from django.conf.urls import url

from . import views

app_name = 'geosearch'

urlpatterns = [
    url(r'^$', views.search, name='search'),
    url(r'^results/$', views.results, name='results'),
]