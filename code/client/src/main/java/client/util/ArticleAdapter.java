package client.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import client.entity.Article;

public class ArticleAdapter {

    public ArticleAdapter() {}

    public Article getArticle(Document document) {
    	if (document == null) {
    		return null;
    	}
        WriterAdapter writerAdapter = new WriterAdapter();
        Article article = new Article(
        		document.getString("title"),
        		document.getString("abstract"),
                writerAdapter.getWriter(document.get("writer", Document.class)),
                document.getString("body")
        			);
        return article;
    }

    public Article getArticle(String json) throws JsonParseException, JsonMappingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.readValue(json, Article.class);
    }
    
    public List<Article> getArticleList(String json) throws JsonParseException, JsonMappingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	@SuppressWarnings("unchecked")
    	CollectionType javaType = mapper.getTypeFactory()
        .constructCollectionType(List.class, Article.class);
		List<Article> articles = mapper.readValue(json, javaType);
    	return articles;
    }
    
    public String getJson(Article article) throws JsonProcessingException {
    	if (article == null) {
    		return null;
    	}
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(article);
    }
    
    public String getJson(List<Article> articles) throws JsonProcessingException {
    	if (articles == null) {
    		return null;
    	}
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(articles);
    }
    
    public Document getDocument(Article article) {
    	if (article == null) {
    		return null;
    	}
    	WriterAdapter writerAdapter = new WriterAdapter();
        return new Document()
                .append("title", article.getTitle())
                .append("abstract", article.getAbstractt())
                .append("writer", writerAdapter.getDocument(article.getWriter()))
                .append("body", article.getBody());
    }

}
