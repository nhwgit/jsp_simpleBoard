package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.DeleteArticleService;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.ReadArticleService;
import article.util.PermissionChecker;
import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
	private static final String DELETE_SUCCESS_VIEW = "/WEB-INF/view/deleteSuccess.jsp";
	
	private DeleteArticleService deleteService = new DeleteArticleService();
	private ReadArticleService readService = new ReadArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			User authUser = (User)req.getSession().getAttribute("authUser");
			String userId = authUser.getId();
			ArticleData articleData = readService.getArticle(no, false);

			if(!PermissionChecker.canModify(userId, articleData.getArticle())) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			deleteService.delete(userId, no);
			return DELETE_SUCCESS_VIEW;
		} catch(ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
}
