package article.service;

import article.model.Article;
import article.model.ArticleContent;

public class ArticleData {
	private Article article;
	private String content;
	
	public ArticleData(Article article, String content) {
		this.article = article;
		this.content = content;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
