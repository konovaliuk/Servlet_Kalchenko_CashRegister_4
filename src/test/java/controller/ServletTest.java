package controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import command.*;
import command.CommandFactory.Commands;
import entity.User;
import service.UserService;

/**
 * 
 * @author SergeyK
 */

@RunWith( PowerMockRunner.class )
public class ServletTest extends Mockito {
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

    @Test
    public void commandTest() throws Exception {
    	CommandFactory command = CommandFactory.getInstance();
    	Command com = command.getCommand(Commands.CHECK);
    	assertTrue(com.getClass().equals(CheckCommand.class));
    	assertTrue(command.getCommand(Commands.CHECKSPEC).getClass().equals(CheckSpecCommand.class));
    	assertTrue(command.getCommand(Commands.LOGIN).getClass().equals(LoginCommand.class));
    	assertTrue(command.getCommand(Commands.REGISTRATION).getClass().equals(RegistrationCommand.class));
    	assertTrue(command.getCommand(Commands.GOODS).getClass().equals(GoodsCommand.class));
    	assertTrue(command.getCommand(Commands.CANCEL).getClass().equals(CancelCommand.class));
    }
    
    @PrepareForTest( UserService.class )
	@Test
	public void serviceTest() throws ServletException, IOException {
        MainController servlet = new MainController();
        HttpServletRequest request= mock(HttpServletRequest.class);;
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        
        when(request.getServletPath()).thenReturn("/login");
        when(request.getRequestDispatcher("/WEB-INF/view/index.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        when(request.getParameter("btnLogin")).thenReturn("Enter");
        when(request.getParameter("email")).thenReturn("a1@gmail.com");
        when(request.getParameter("password")).thenReturn("1");
        verify(dispatcher).forward(request, response);
        
        PowerMockito.mockStatic(UserService.class );
        when(UserService.findUser(any(String.class), any(String.class))).thenReturn(null);        
        servlet.doPost(request, response);
        verify(request, times(1)).getParameter("btnLogin");
        verify(request, atLeast(2)).getParameter("email");
        
        when(request.getServletPath()).thenReturn("/registration");
        when(request.getRequestDispatcher("/WEB-INF/view/registration.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        when(UserService.registration(any(String.class), any(String.class), any(String.class))).thenReturn(new User());
        when(request.getParameter("btnReg")).thenReturn("Registration");
        servlet.doPost(request, response);
        
        when(request.getServletPath()).thenReturn("/check");
        when(request.getRequestDispatcher("/WEB-INF/view/check.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        
        when(request.getServletPath()).thenReturn("/cancel");
        when(request.getRequestDispatcher("/WEB-INF/view/cancel.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
	}
}
