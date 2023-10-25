package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import article.util.StringUtil;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/listArticle.jsp";
	
	private ListArticleService listService = new ListArticleService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = StringUtil.parseInt(pageNoVal);
		if(pageNo == -1) pageNo = 1;
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		req.setAttribute("articlePage", articlePage);
		
		return FORM_VIEW;
	}
	
}
