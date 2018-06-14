package com.predic8.workshop.stock.dto;

public class Article {
	private String articleId;
	private Long quantity;

	public Article() {
	}

	public String getArticleId() {
		return this.articleId;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String toString() {
		return "Article(articleId=" + articleId + ", quantity=" + quantity + ")";
	}
}