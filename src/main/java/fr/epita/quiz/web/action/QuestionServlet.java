package fr.epita.quiz.web.action;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.QuestionDAO;

@WebServlet(urlPatterns={"/qcreate","/qupdate","/qdelete","/qsearch"})
public class QuestionServlet extends SpringServlet{
	
	
	private static final Logger LOGGER = LogManager.getLogger(QuestionServlet.class);
	
	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	MCQChoiceDAO mcqDAO;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		if ((req.getRequestURL() + "").contains(Constants.CREATE_OPERATION)) {
			createData(req, resp);
		} else if ((req.getRequestURL() + "").contains(Constants.SEARCH_OPERATION)) {
			searchData(req, resp);
		} else if ((req.getRequestURL() + "").contains(Constants.UPDATE_OPERATION)) {
			updateData(req, resp);
		} else if ((req.getRequestURL() + "").contains(Constants.DELETE_OPERATION)) {
			deleteData(req, resp);
		}
	}

	/**
	 * Method to handle the delete operation
	 * 
	 * @param req The HttpServlet request
	 * @param resp The HttpServlet response
	 */
	private void deleteData(HttpServletRequest req, HttpServletResponse resp) {
		
		Question question = new Question();
		question.setQuestion(req.getParameter(Constants.QUESTION));
		question.setType(type(req));
		if (question.getType() != null) {
			questionDAO.delete(question);
			mcqDAO.deleteAllMCQs(question);
			
			//mcqDAO.search(entity)
			
			
			//remaining mcq's
			///call service to return all mcsq's
			
//			HttpSession session=req.getSession();
//			session.setAttribute("remainingmcqs", );
			
		}
	}

	private void updateData(HttpServletRequest req, HttpServletResponse resp) {

		Question question = new Question();

		question.setQuestion(req.getParameter(Constants.QUESTION));
		question.setType(type(req));
		questionDAO.search(question);

		List<Question> questionList = questionDAO.search(question);
		Question fetchedQuestion = questionList.get(0);

		if (!fetchedQuestion.getQuestion().equalsIgnoreCase(req.getParameter(Constants.QUESTION))
				|| !fetchedQuestion.getType().toString().equalsIgnoreCase(req.getParameter("type"))) {
			question.setQuestion(req.getParameter(Constants.QUESTION));
			question.setType(type(req));
			if (question.getType() != null)
				questionDAO.update(question);
		}

//redirect
		
	}

	private void searchData(HttpServletRequest req, HttpServletResponse resp) {

		Question question = new Question();
		question.setQuestion(req.getParameter(Constants.QUESTION));
		question.setType(type(req));
		questionDAO.search(question);
	}

	private void createData(HttpServletRequest req, HttpServletResponse resp) {

		Question question = new Question();
        HttpSession hs = req.getSession();
		
		question.setQuestion(req.getParameter(Constants.QUESTION));
		question.setType(type(req));
		if (question.getType() != null){
			questionDAO.create(question);
			try {
				hs.setAttribute(Constants.QUESTION_LINK,question);
				resp.sendRedirect("addOptions.html");
			} catch (IOException e) {
				LOGGER.debug("Error while adding the question");
			}
		}
		    
	}

	private QuestionType type(HttpServletRequest req) {

		if (req.getParameter("type").contains("OPEN")) {
			return QuestionType.OPEN;
		} else if (req.getParameter("type").contains("MCQ")) {
			return QuestionType.MCQ;
		} else if (req.getParameter("type").contains("ASSOCIATED")) {
			return QuestionType.ASSOCIATED;
		}
		return null;
	}
}
