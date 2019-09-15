package controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author SergeyK
 */
public class TestServlet extends Mockito {
	
    private MainController servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() throws ServletException, IOException {
        request = mock(HttpServletRequest.class);       
        response = mock(HttpServletResponse.class); 
        servlet = new MainController();
       // ((HttpServletResponse)response).sendRedirect("login");
        //servlet.doPost(request, response);
        //assertEquals("text/html", response.getContentType());
		//fail("Not yet implemented");
	}

}
