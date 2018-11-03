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
        String query = "";
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
            System.out.println(String.format(d.get("utility")));
            System.out.println(String.format(d.get("location")));
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
