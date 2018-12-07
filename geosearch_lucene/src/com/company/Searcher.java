package com.company;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
    private static final String INDEX_DIR = "indexedfiles";

    public static void main(String[] args) throws Exception {
        IndexSearcher searcher = createSearcher();
        String query = "SriCity";
        double lat1 = 13.5568;
        double lon1 = 80.0261;
        double threshold = 5000.0;
        /*try{
            File file = new File("input.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            query = br.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        try {
            if (!args[0].equals(null)) {
                query = args[0];
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*//Search by ID
        TopDocs foundDocs = searchById(1, searcher);

        System.out.println("Total Results :: " + foundDocs.totalHits);

        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("firstName")));
        }
        */
        //Search by City
        System.out.println("Query = "+query);

        TopDocs foundDocs2 = searchByCity(query, searcher);

        System.out.println("Total Results :: " + foundDocs2.totalHits);

        for (ScoreDoc sd : foundDocs2.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            
            double lat2 = Double.parseDouble(d.get("latitude"));
            double lon2 = Double.parseDouble(d.get("longitude"));
            
            System.out.println("asd");
            
            //System.out.println(lat2);
            //System.out.println(lon2);
            
            final int R = 6371; // Radius of the earth

            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c * 1000; // convert to meters

            //double height = el1 - el2;
            double height = 0;

            distance = Math.pow(distance, 2) + Math.pow(height, 2);
            
            distance = Math.sqrt(distance);
            
            System.out.println(distance);
            
            if  (distance < threshold) {
                System.out.println(String.format(d.get("utility")));
                System.out.println(String.format(d.get("location")));
                System.out.println(Double.parseDouble(d.get("latitude")));
                System.out.println(Double.parseDouble(d.get("longitude")));
            }
            
        }

    }

    private static TopDocs searchByCity(String city, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("city", new StandardAnalyzer());
        Query city_query = qp.parse(city);
        TopDocs hits = searcher.search(city_query, 10);
        return hits;
    }
    private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(firstName);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }

    private static TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("id", new StandardAnalyzer());
        Query idQuery = qp.parse(id.toString());
        TopDocs hits = searcher.search(idQuery, 10);
        return hits;
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
}
