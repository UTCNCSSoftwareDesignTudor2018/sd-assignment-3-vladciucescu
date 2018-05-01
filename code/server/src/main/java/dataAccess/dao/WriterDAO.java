package dataAccess.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dataAccess.entity.Writer;
import dataAccess.util.ConnectionFactory;
import dataAccess.util.WriterAdapter;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class WriterDAO {

    private MongoCollection<Document> getWriterCollection() {
        ConnectionFactory cf = new ConnectionFactory();
        MongoDatabase db = cf.getDatabase();
        return db.getCollection("writer");
    }

    public Writer findWriter(String username) {
        MongoCollection<Document> collection = getWriterCollection();
        Document writerDoc = collection.find(eq("username", username)).first();
        WriterAdapter adapter = new WriterAdapter(writerDoc);
        return adapter.getWriter();
    }

    public void updateWriter(String username, Writer writer) {
        MongoCollection<Document> collection = getWriterCollection();
        WriterAdapter adapter = new WriterAdapter(writer);
        collection.updateOne(eq("username", username), adapter.getDocument());
    }
}
