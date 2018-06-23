package fr.epita.quiz.web.action;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.tests.TestCase;

@WebServlet(urlPatterns={"/mcqcreate","/mcqupdate","/mcqdelete","/mcqsearch"})
public class McqServlet extends SpringServlet {
	
	private static final long serialVersionUID = -7142061957533154990L;
	
	private static final Logger LOGGER = LogManager.getLogger(McqServlet.class);
	
	@Inject
	MCQChoiceDAO mcqDAO;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if ((req.getRequestURL() + "").contains(Constants.CREATE_OPERATION)) {
			createMCQChoice(req, resp);
		} else if ((req.getRequestURL() + "").contains(Constants.SEARCH_OPERATION)) {
			searchMCQChoice(req, resp);
		} else if ((req.getRequestURL() + "").contains(Constants.UPDATE_OPERATION)) {
			updateMCQChoice(req, resp);
		} else if ((req.getRequestURL() + "").contains(Constants.DELETE_OPERATION)) {
			deleteMCQChoice(req, resp);
		}
	}

	private void deleteMCQChoice(HttpServletRequest req, HttpServletResponse resp) {

		MCQChoice choice = new MCQChoice();
		choice.setChoice(req.getParameter(Constants.MCQ));
		choice.setOrder(Integer.valueOf(req.getParameter(Constants.ORDER_STRING)));
		mcqDAO.delete(choice);
	}

	private void updateMCQChoice(HttpServletRequest req, HttpServletResponse resp) {

		MCQChoice choice = new MCQChoice();
		choice.setChoice(req.getParameter(Constants.MCQ));
		choice.setOrder(Integer.valueOf(req.getParameter(Constants.ORDER_STRING)));
		choice.setValid(validOption(req));
		List<MCQChoice> mcqList = mcqDAO.search(choice);
		MCQChoice fetchedMCQ = mcqList.get(0);
		if (!fetchedMCQ.getChoice().equalsIgnoreCase(req.getParameter(Constants.MCQ))
				|| fetchedMCQ.getOrder() != (Integer.valueOf(req.getParameter(Constants.ORDER_STRING)))
				|| (fetchedMCQ.isValid() == (req.getParameter("valid").equalsIgnoreCase("true")? true : false))) 

{

			choice.setChoice(req.getParameter(Constants.MCQ));
			choice.setOrder(Integer.valueOf(req.getParameter(Constants.ORDER_STRING)));
			choice.setValid(validOption(req));
			mcqDAO.update(choice);

		}

	}

	private void searchMCQChoice(HttpServletRequest req, HttpServletResponse resp) {

		MCQChoice choice = new MCQChoice();
		choice.setChoice(req.getParameter(Constants.MCQ));
		choice.setOrder(Integer.valueOf(req.getParameter(Constants.ORDER_STRING)));
		choice.setValid(validOption(req));
		mcqDAO.search(choice);

	}

	private void createMCQChoice(HttpServletRequest req, HttpServletResponse resp) {

		MCQChoice choice;
		String[] arrayOptions = req.getParameterValues(Constants.MCQ);
		String[] arrayValid = req.getParameterValues(Constants.OPTION_VALID);
        HttpSession session = req.getSession();

		Question question = (Question) session.getAttribute(Constants.QUESTION_LINK);

		for (int i = 0; i < arrayOptions.length; i++) {
				choice = new MCQChoice();
				choice.setChoice(arrayOptions[i]);
				choice.setOrder(1);
				choice.setValid(validOrInvalid(arrayValid[i]));
				choice.setQuestion(question);
				mcqDAO.create(choice);				
		}
		try {
			resp.sendRedirect("dashboard.html");
		} catch (IOException e) {
			LOGGER.debug("Error while creating the MCQ for the question");
		}
	}

	private boolean validOption(HttpServletRequest req) {

		return (req.getParameter("valid").contains("true"));
	}
	private boolean validOrInvalid(String str) {

		return (str.contains("Valid"));
	}
}
