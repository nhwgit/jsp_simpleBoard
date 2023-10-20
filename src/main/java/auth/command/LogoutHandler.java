package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.NotExistId;
import auth.service.NotMatchIdAndPasswd;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return null;
	}
	
}
