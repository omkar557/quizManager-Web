package fr.epita.quiz.web.action;

import java.awt.Window.Type;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.QuestionOperationsService;

/**
 * Servlet for the update question list
 * @author itsme_omkar
 *
 */
@WebServlet("/questions")
public class UpdateQListServlet extends SpringServlet{

	
	public UpdateQListServlet() {
		super();
	}

	@Inject
	QuestionOperationsService queOprSer;
	/**
	 * The Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	super.doGet(req, resp);
}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		super.doPost(req, resp);
		
		final String question_Id = req.getParameter("question_ID");
		
		Question questions = new Question();
		List<Question> lstQuestion = queOprSer.listAllQuestions(questions);
		
		for (int i = 0; i < lstQuestion.size(); i++) {
			if (question_Id.equals(lstQuestion.get(i).getQuestion())) {
				
				req.setAttribute("theQuestion", lstQuestion.get(i).getQuestion());
				req.getRequestDispatcher("/WEB-INF/updateAQuestion.jsp").forward(req, resp);
				
			}
		}
		
	}

}
