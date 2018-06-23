package fr.epita.quiz.web.action;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.quiz.datamodel.Login;
import fr.epita.quiz.services.AuthenticationService;
import fr.epita.quiz.tests.TestCase;

/**
 * Servlet implementation class Login
 */

/**
 *Login Servlet  
 *
 * @author itsme_omkar
 *
 */


@WebServlet(urlPatterns="/login")
public class LoginServlet extends SpringServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);

	@Inject
	AuthenticationService auth;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		//empty constructor
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String email = request.getParameter("login");
		final String password = request.getParameter("password");
		
		Login login = new Login(email, password);
		
		final boolean authenticated = auth.authenticate(login);
		request.getSession().setAttribute("authenticated", authenticated);
		request.getSession().setAttribute("userName", login);
		
		try{
			if(authenticated){
				//redirect to dashboard
				response.sendRedirect("dashboard.html");
			}
			else
				response.sendRedirect("index.html");
			
		}catch(Exception e){
			LOGGER.debug("Error while authenticating the user.");
		}
	}
	

}