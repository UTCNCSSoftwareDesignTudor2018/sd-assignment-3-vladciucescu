package dataAccess.dao;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dataAccess.entity.Article;
import dataAccess.entity.Writer;
import dataAccess.util.ArticleAdapter;
import dataAccess.util.ConnectionFactory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDAO {

    private MongoCollection<Document> getArticleCollection() {
        ConnectionFactory cf = new ConnectionFactory();
        MongoDatabase db = cf.getDatabase();
        return db.getCollection("article");
    }

    public List<Article> findAllArticles() {
        MongoCollection<Document> collection = getArticleCollection();
        List<Article> articles = new ArrayList<>();
        Block<Document> addToList = (d->
            {ArticleAdapter adapter = new ArticleAdapter(d);
            articles.add(adapter.getArticle());}
            );
        collection.find().forEach(addToList);
        return articles;
    }

    public List<Article> findAllArticlesForWriter(Writer writer) {
        List<Article> articles = findAllArticles();
        return articles.stream().filter(a->a.getWriter().equals(writer)).collect(Collectors.toList());
    }

    public void addArticle(Article article) {
        MongoCollection<Document> collection = getArticleCollection();
        ArticleAdapter adapter = new ArticleAdapter(article);
        collection.insertOne(adapter.getDocument());
    }

    public void deleteArticle(Article article) {
        MongoCollection<Document> collection = getArticleCollection();
        ArticleAdapter adapter = new ArticleAdapter(article);
        collection.deleteOne(adapter.getDocument());
    }
}
