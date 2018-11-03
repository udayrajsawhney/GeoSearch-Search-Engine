package com.company;

import java.nio.file.Paths;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
//import org.apache.lucene.document.LatLonPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
    private static final String INDEX_DIR = "indexedfiles";

    public static void main(String[] args) throws Exception
    {
        IndexWriter writer = createWriter();
        List<Document> documents = new ArrayList<>();

        Document document1 = createDocument(1, "SriCity", "coffee", "New City - (x,y)");
        documents.add(document1);

        Document document2 = createDocument(2, "SriCity", "coffee", "Sunny Cafe - (x,y)");
        documents.add(document2);

        //Let's clean everything first
        writer.deleteAll();

        writer.addDocuments(documents);
        writer.commit();

        writer.close();
    }

    private static Document createDocument(Integer id, String city, String utility, String location)
    {
        Document document = new Document();
        document.add(new StringField("id", id.toString() , Field.Store.YES));
        document.add(new TextField("city", city , Field.Store.YES));
        document.add(new TextField("utility", utility , Field.Store.YES));
        document.add(new TextField("location", location , Field.Store.YES));
        return document;
    }

    private static IndexWriter createWriter() throws IOException
    {
        FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }
}
