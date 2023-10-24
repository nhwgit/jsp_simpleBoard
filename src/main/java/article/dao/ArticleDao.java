package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {

	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Writer writer = article.getWriter();
			String writer_id = writer.getId();
			String writer_name = writer.getName();
			String title = article.getTitle();
			Date regDate = article.getRegDate();
			Date modifiedDate = article.getModifiedDate();
			
			pstmt = conn.prepareStatement("insert into article " +
					"(writer_id, writer_name, title, regdate, moddate, read_cnt) " +
					"values(?,?,?,?,?,0)");
			pstmt.setString(1, writer_id);
			pstmt.setString(2,  writer_name);
			pstmt.setString(3,  title);
			pstmt.setTimestamp(4,  toTimestamp(regDate));
			pstmt.setTimestamp(5,  toTimestamp(modifiedDate));
			int insertedCount = pstmt.executeUpdate();
			
			if(insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from article");
				if(rs.next()) {
					Integer newNum = rs.getInt(1);
					return new Article(newNum, writer, title, regDate, modifiedDate, 0);
							
				}
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}
	
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
