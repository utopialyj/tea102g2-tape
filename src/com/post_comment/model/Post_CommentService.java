package com.post_comment.model;

import java.sql.Timestamp;
import java.util.List;

public class Post_CommentService {

	private Post_CommentDAO_interface dao;
	
	public Post_CommentService(){
		dao = new Post_CommentJDBCDAO();
	}
	
	
	public Post_CommentVO addPostCom(String cmnt_memid, Timestamp cmnt_date, String cmnt_context, Integer lk_count, String post_code, Integer cmnt_sts) {
		Post_CommentVO pcVO = new Post_CommentVO();

		pcVO.setCmnt_memid(cmnt_memid);
		pcVO.setCmnt_date(cmnt_date);
		pcVO.setCmnt_context(cmnt_context);
		pcVO.setLk_count(lk_count);
		pcVO.setPost_code(post_code);
		pcVO.setCmnt_sts(cmnt_sts);

		dao.insert(pcVO);
	
		return pcVO;
	}
	
	
	public Post_CommentVO updatePostCom(String cmnt_uid, String cmnt_memid, Timestamp cmnt_date, String cmnt_context, Integer lk_count, String post_code, Integer cmnt_sts) {
		Post_CommentVO pcVO = new Post_CommentVO();

		pcVO.setCmnt_uid(cmnt_uid);
		pcVO.setCmnt_memid(cmnt_memid);
		pcVO.setCmnt_date(cmnt_date);
		pcVO.setCmnt_context(cmnt_context);
		pcVO.setLk_count(lk_count);
		pcVO.setPost_code(post_code);
		pcVO.setCmnt_sts(cmnt_sts);

		dao.update(pcVO);
		
		return pcVO;
	}
	
	
	
	public Post_CommentVO getOnePostComment(String cmnt_uid) {
		return dao.findByPrimaryKey(cmnt_uid);
	}
	
	public List<Post_CommentVO> getAll(){
		return dao.getAll();
	}
	
	
	public List<Post_CommentVO> findByPost_Code(String post_code){
		return dao.findByPost_Code(post_code);
	}
	
	
	
	public void deletePostCmnt(String cmnt_uid) {
		dao.delete(cmnt_uid);
	}
	
	
	
}
