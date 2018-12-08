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
import java.util.Arrays;

public class Searcher {
    private static final String INDEX_DIR = "indexedfiles";

    public static void main(String[] args) throws Exception {
        IndexSearcher searcher = createSearcher();
        String query = "";
        String query1 = "";
        String city = "Sri City";
        String city1 = "";
        double lat1=13.5568;
        double lon1=80.0261;
        double threshold = 5000.0;
        try {
            if (!args[0].equals(null)) {
                for(int i=0;i<args.length -3;i++)
                    query += args[i] + " ";
                city = args[args.length-1];
                lat1 = Double.parseDouble(args[args.length-3]);
                lon1 = Double.parseDouble(args[args.length-2]);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*
        System.out.println("Query = " + query);
        System.out.println("Latitude = " + lat1);
        System.out.println("Longitude = " + lon1);
        System.out.println("City = " + city);
        System.out.println("Coming from java");
        */

        city1 = getCodes(city);
        TopDocs foundDocs2 = searchByCity(city1, searcher);
        query1 = getCodes(query);
        //TopDocs foundDocs2 = searchByUtility(query1, searcher);

        //System.out.println("Total Results :: " + foundDocs2.totalHits);
        for (ScoreDoc sd : foundDocs2.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            
            double lat2 = Double.parseDouble(d.get("latitude"));
            double lon2 = Double.parseDouble(d.get("longitude"));
            
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
            
            if  (distance < threshold && query1.equals(d.get("utility"))) {
                //System.out.println(String.format(d.get("utility")));
            	//System.out.println(String.format(query));
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
    private static TopDocs searchByUtility(String utility, IndexSearcher searcher) throws Exception
    {
    	QueryParser qp = new QueryParser("utility", new StandardAnalyzer());
    	Query utility_query = qp.parse(utility);
    	TopDocs hits = searcher.search(utility_query, 10);
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
    public static String getCodes(String s)
    {
        char[] x = s.toUpperCase().toCharArray();
        
        
        char firstLetter = x[0];
 
        //RULE [ 2 ]
        //Convert letters to numeric code
        for (int i = 0; i < x.length; i++) {
            switch (x[i]) {
            case 'B':
            case 'F':
            case 'P':
            case 'V': {
                x[i] = '1';
                break;
            }
 
            case 'C':
            case 'G':
            case 'J':
            case 'K':
            case 'Q':
            case 'S':
            case 'X':
            case 'Z': {
                x[i] = '2';
                break;
            }
 
            case 'D':
            case 'T': {
                x[i] = '3';
                break;
            }
 
            case 'L': {
                x[i] = '4';
                break;
            }
 
            case 'M':
            case 'N': {
                x[i] = '5';
                break;
            }
 
            case 'R': {
                x[i] = '6';
                break;
            }
 
            default: {
                x[i] = '0';
                break;
            }
            }
        }
 
        //Remove duplicates
        //RULE [ 1 ]
        String output = "" + firstLetter;
        
        //RULE [ 3 ]
        for (int i = 1; i < x.length; i++)
            if (x[i] != x[i - 1] && x[i] != '0')
                output += x[i];
 
        //RULE [ 4 ]
        //Pad with 0's or truncate
        output = output + "0000";
        return output.substring(0, 4);
    }

}
