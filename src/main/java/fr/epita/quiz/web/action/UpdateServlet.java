package fr.epita.quiz.web.action;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.GenericORMDao;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.QuestionOperationsService;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet(urlPatterns = { "/update", "/qmodifyquestion" })
public class UpdateServlet extends SpringServlet {
	private static final long serialVersionUID = 1L;

	
	private static final Logger LOGGER = LogManager.getLogger(UpdateServlet.class);

	@Inject
	QuestionOperationsService qService;
	
	@Inject
	QuestionDAO questionDao;
	
	HttpSession session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Question question = new Question();

		request.setAttribute("lstOfQuestions", qService.getAllQuestions(question));
		request.getRequestDispatcher("/WEB-INF/views/finalUpdatePage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		session = request.getSession(); 

		if ((request.getRequestURL() + "").contains("update")) {

			final int question_Id = Integer.parseInt(request.getParameter("updateQuestion"));

			Question questions = new Question();

			List<Question> lstQuestions = qService.listAllQuestions(questions);
			

			for (int i = 0; i < lstQuestions.size(); i++) {
				if (question_Id == lstQuestions.get(i).getId()) {

					request.setAttribute("qid", lstQuestions.get(i).getId());
					request.setAttribute("que", lstQuestions.get(i).getQuestion());
					request.setAttribute("qty", lstQuestions.get(i).getType());
					
					session.setAttribute("qid", lstQuestions.get(i).getId());
					session.setAttribute("qty", lstQuestions.get(i).getType());
				}
			}
			try {
				request.getRequestDispatcher("/WEB-INF/views/updateAQuestion.jsp").forward(request, response);
			} catch (Exception e) {
				LOGGER.debug("Error while navigating to the next Page:");
			}
		}else if ((request.getRequestURL() + "").contains("qmodifyquestion")){
			
			String newQuestion= request.getParameter("question");
			
			int qid=(int) session.getAttribute("qid");
			
			questionDao.update(new Question(qid,newQuestion,QuestionType.MCQ));
			
			response.sendRedirect("dashboard.html");

		}
	}
}
