# GeoSearch-Search-Engine

We developed a `location-based search system` for web documents on the internet.
This system can find documents based on the distance between locations that are
described in web documents and a location specified by a user. It consists of three
modules.

- (1) Gathering web documents,
- (2) a parser extracts address strings from documents and associates latitude-longitude information to the original document and

* (3) a retrieval module. This system can retrieve location-related web documents overlooked by conventional keyword-based search engines.

## To Run Project

- First create jar file of the project using any IDE Example `IntelliJ IDEA`,
- File -> Project Structure -> Artifacts -> + -> Follow default settings

1. To run project first create a `virtual environment` to run `Django baackend server`.
2. Install dependancies using the command

   > \$ pip install -r requirements.txt

3. To run `django server` go to root/django-server/geosearch/

   > \$ python manage.py runserver

4. To run Lucene runnable jar go to root/geosearch_lucene

   > \$ java -jar geosearch_lucene.jar <Query> <Latitude> <Longitude> <City Name>
