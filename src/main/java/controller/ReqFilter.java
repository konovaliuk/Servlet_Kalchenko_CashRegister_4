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
		HttpSession session = ((HttpServletRequest)request).getSession();
		String path = ((HttpServletRequest)request).getServletPath();
 		if ((session == null || session.getAttribute("user") == null) 
				&& !path.startsWith("/log")) {
			((HttpServletResponse)response).sendRedirect("login");
			return;
		}		
		chain.doFilter(request, response);
	}
}
