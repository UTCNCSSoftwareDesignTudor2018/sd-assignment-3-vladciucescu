package dataAccess.util;

import dataAccess.entity.Article;
import org.bson.Document;

public class ArticleAdapter {

    private Article article;
    private Document document;

    public ArticleAdapter(Article article) {
        this.article = article;
        WriterAdapter writerAdapter = new WriterAdapter(article.getWriter());
        document = new Document("_id", article.getId())
                .append("title", article.getTitle())
                .append("abstract", article.getAbstractt())
                .append("writer", writerAdapter.getDocument())
                .append("body", article.getBody());
    }

    public ArticleAdapter(Document document) {
        this.document = document;
        WriterAdapter writerAdapter = new WriterAdapter(document.get("writer", Document.class));
        article = new Article(
                document.getObjectId("_id"),
                document.getString("title"),
                document.getString("abstract"),
                writerAdapter.getWriter(),
                document.getString("body")
        );
    }

    public Article getArticle() {
        return article;
    }

    public Document getDocument() {
        return document;
    }

}
