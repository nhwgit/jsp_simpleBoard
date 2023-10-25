package article.service;

import java.util.List;

import article.model.Article;

public class ArticlePage {
	private static final int PAGE_BAR_SIZE = 10; 
	private int total;
	private int currentPage;
	private List<Article> content;
	private int totalPages;
	private int startPage;
	private int endPage;
	
	public ArticlePage(int total, int currentPage, int size, List<Article> content) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		if(total==0) {
			totalPages = 1;
			startPage = 1;
			endPage = 1;
		} else {
			totalPages = total/size;
			if(total % size > 0) totalPages++;
		}
		if(currentPage <1 || currentPage > totalPages ) currentPage = 1;
		
		int modVal = currentPage % PAGE_BAR_SIZE;
		startPage = currentPage / PAGE_BAR_SIZE * PAGE_BAR_SIZE + 1;
		if(modVal == 0) startPage -= PAGE_BAR_SIZE;
		
		endPage = startPage + PAGE_BAR_SIZE - 1;
		if(endPage > totalPages) endPage = totalPages;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<Article> getContent() {
		return content;
	}

	public void setContent(List<Article> content) {
		this.content = content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public boolean hasNoArticles() {
		return total == 0;
	}
	
	public boolean hasArticles() {
		return total > 0;
	}
}
