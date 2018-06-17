package fr.epita.quiz.web.action;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Login;
import fr.epita.quiz.services.AuthenticationService;

/**
 * Servlet implementation class Login
 */

@WebServlet(urlPatterns="/login")
@Named
public class LoginServlet extends SpringServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	AuthenticationService auth;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		final String email = request.getParameter("login");
		final String password = request.getParameter("password");
		
		Login login = new Login(email, password);
		
		final boolean authenticated = auth.authenticate(login);
		request.getSession().setAttribute("authenticated", authenticated);
		request.getSession().setAttribute("userName", login);
		
		response.sendRedirect("question-editing.jsp");
	}

}