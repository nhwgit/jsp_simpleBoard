package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

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
	
	public void delete(Connection conn, int no) throws SQLException {
		try(PreparedStatement pstmt = conn.prepareStatement(
				"delete from article " +
				"where article_no = ?")) {
			pstmt.setInt(1,  no);
			pstmt.executeUpdate();
		}
	}
	
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	public List<Article> select(Connection conn, int startRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article " +
					"order by article_no desc limit ?, ?");
			pstmt.setInt(1,  startRow);
			pstmt.setInt(2,  size);
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while(rs.next()) {
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from article where article_no = ?");
			pstmt.setInt(1,  no);
			rs = pstmt.executeQuery();
			Article article = null;
			if(rs.next()) {
				article = convertArticle(rs);
			}
			return article;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = 
				conn.prepareStatement(
					"update article set read_cnt = read_cnt + 1 " +
							"where article_no = ?")) {
			pstmt.setInt(1,  no);
			pstmt.executeUpdate();
		}
	}
	
	public int update(Connection conn, int no, String title) throws SQLException {
		try(PreparedStatement pstmt = 
				conn.prepareStatement(
						"update article set title = ?, moddate = now() "+
						"where article_no = ?")) {
			pstmt.setString(1,  title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
	
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"),
				new Writer(
						rs.getString("writer_id"),
						rs.getString("writer_name")),
				rs.getString("title"),
				toDate(rs.getTimestamp("regdate")),
				toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt")
				);
	}
	
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
}
