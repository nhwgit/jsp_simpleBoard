package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleContentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import article.util.StringUtil;
import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readService = new ReadArticleService();
	private static final String FORM_VIEW = "/WEB-INF/view/readArticle.jsp";
	private static final String LIST_VIEW = "/WEB-INF/view/listArticle.jsp";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("no");
		int articleNum = StringUtil.parseInt(noVal);
		if(articleNum == -1) return LIST_VIEW;
		try {
			ArticleData articleData = readService.getArticle(articleNum, true);
			req.setAttribute("articleData",  articleData);
			return FORM_VIEW;
		} catch(ArticleNotFoundException| ArticleContentNotFoundException e) {
			req.getServletContext().log("no article", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
