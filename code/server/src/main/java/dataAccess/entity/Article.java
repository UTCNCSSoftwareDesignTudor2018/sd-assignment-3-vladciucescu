package dataAccess.entity;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Article {

    private final ObjectId id;
    private String title;
    private String abstractt;
    private Writer writer;
    private String body;

    public Article(ObjectId id, String title, String abstractt, Writer writer, String body) {
        this.id = id;
        this.title = title;
        this.abstractt = abstractt;
        this.writer = writer;
        this.body = body;
    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractt() {
        return abstractt;
    }

    public void setAbstractt(String abstractt) {
        this.abstractt = abstractt;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Article{" +
                ", title='" + title + '\'' +
                ", abstractt='" + abstractt + '\'' +
                ", writer=" + writer +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
