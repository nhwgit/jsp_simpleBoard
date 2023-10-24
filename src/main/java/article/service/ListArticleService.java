package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.connection.ConnectionProvider;

public class ListArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private static final int RECORD_COUNT_PER_PAGE = 10;
	
	public ArticlePage getArticlePage(int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = articleDao.selectCount(conn);
			List<Article> content = articleDao.select(conn, (pageNum-1)*RECORD_COUNT_PER_PAGE, RECORD_COUNT_PER_PAGE);
			return new ArticlePage(total, pageNum, RECORD_COUNT_PER_PAGE, content);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
