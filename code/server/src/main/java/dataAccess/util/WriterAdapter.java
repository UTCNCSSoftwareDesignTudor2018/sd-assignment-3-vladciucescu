package dataAccess.util;

import dataAccess.entity.Writer;
import org.bson.Document;

import java.util.List;

public class WriterAdapter {

    private Writer writer;
    private Document document;

    public WriterAdapter(Writer writer) {
        this.writer = writer;
        document = new Document()
                .append("username", writer.getUsername())
                .append("name", writer.getName())
                .append("surname", writer.getSurname());
    }

    public WriterAdapter(Document document) {
        this.document = document;
        writer = new Writer(
                document.getObjectId("_id"),
                document.getString("username"),
                document.getString("name"),
                document.getString("surname")
        );
    }

    public Writer getWriter() {
        return writer;
    }

    public Document getDocument() {
        return document;
    }
}
