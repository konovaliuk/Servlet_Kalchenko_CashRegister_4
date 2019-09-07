package controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author SergeyK
 */
public class ReqFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			//request.getServletContext().getRequestDispatcher("/").forward(request, response);
			//request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
			//request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
			//((HttpServletResponse)response).sendRedirect("/");
		}
		chain.doFilter(request, response);
	}
}
