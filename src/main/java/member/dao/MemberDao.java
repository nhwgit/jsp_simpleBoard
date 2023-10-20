package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.Member;

public class MemberDao {
	
	public Member selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from member where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new Member(
						rs.getString("id"),
						rs.getString("name"),
						rs.getString("password"),
						rs.getInt("grade"),
						toDate(rs.getTimestamp("regDate"))
						);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return member;
	}
	
	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}
	
	public void insert(Connection conn, Member mem) throws SQLException {
		try(PreparedStatement pstmt =
				conn.prepareStatement("insert into member values(?,?,?,?)")) {
			pstmt.setString(1,  mem.getId());
			pstmt.setString(2,  mem.getName());
			pstmt.setString(3, mem.getPassword());
			pstmt.setInt(4, mem.getGrade());
			pstmt.setTimestamp(5, new Timestamp(mem.getRegDate().getTime()));
		}
		
	}
}