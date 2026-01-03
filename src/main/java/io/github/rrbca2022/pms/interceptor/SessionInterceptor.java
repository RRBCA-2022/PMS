package io.github.rrbca2022.pms.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler
	) throws Exception {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);


		String path = request.getRequestURI();
		HttpSession session = request.getSession(false);
		boolean loggedIn = session != null && session.getAttribute("loggedInUser") != null;

		if (path.equals("/login")) {
			return true;
		}

		// If user is logged in, block access to login page
		if (loggedIn && path.equals("/")) {
			response.sendRedirect("/dashboard");
			return false;
		}

		// If user is NOT logged in, block protected pages
		if (!loggedIn && !path.equals("/")) {
			response.sendRedirect("/");
			return false;
		}

		return true;
	}
}

