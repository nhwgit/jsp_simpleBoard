package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.util.PermissionChecker;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			int articleNumber = modReq.getArticleNumber();
			String userId = modReq.getUserId();
			String title = modReq.getTitle();
			String content = modReq.getContent();
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = articleDao.selectById(conn, articleNumber);
			if(article == null) throw new ArticleNotFoundException();
			if(!PermissionChecker.canModify(userId, article))
				throw new PermissionDeniedException();
			
			articleDao.update(conn, articleNumber, title);
			contentDao.update(conn, articleNumber, content);
			conn.commit();
			
		} catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch(PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
