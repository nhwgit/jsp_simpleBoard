package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.ReadArticleService;
import article.util.PermissionChecker;
import auth.service.User;
import mvc.command.CommandHandler;

public class ModifyArticleHandler implements CommandHandler {
	private static final String MODIFY_VIEW = "/WEB-INF/view/modifyForm.jsp";
	private static final String SUCCESS_VIEW = "/WEB-INF/view/modifySuccess.jsp";
	
	private ReadArticleService readService = new ReadArticleService();
	private ModifyArticleService modifyService = new ModifyArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		}
		else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		}
		else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) 
		throws Exception {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			ArticleData articleData = readService.getArticle(no, false);
			User authUser = (User)req.getSession().getAttribute("authUser");
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no,
					articleData.getArticle().getTitle(),
					articleData.getContent());
			if(!PermissionChecker.canModify(modReq.getUserId(), articleData.getArticle())) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			req.setAttribute("modReq", modReq);
			return MODIFY_VIEW;
		} catch(ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
	throws Exception {
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);
		User authUser = (User)req.getSession().getAttribute("authUser");
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no,
				req.getParameter("title"),
				req.getParameter("content"));
		req.setAttribute("modReq",  modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		modReq.validate(errors);
		if(!errors.isEmpty()) {
			return MODIFY_VIEW;
		}
		try {
			modifyService.modify(modReq);
			return SUCCESS_VIEW;
		} catch(ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
