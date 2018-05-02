package client.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {

    private String title;
    private String abstractt;
    private Writer writer;
    private String body;

    @JsonCreator
    public Article(@JsonProperty("name")String title,@JsonProperty("abstractt") String abstractt, @JsonProperty("writer") Writer writer,@JsonProperty("body") String body) {
        this.title = title;
        this.abstractt = abstractt;
        this.writer = writer;
        this.body = body;
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
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title) &&
        		Objects.equals(abstractt, article.abstractt) &&
        		Objects.equals(writer, article.writer) &&
        		Objects.equals(body, article.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, abstractt, writer, body);
    }
}
