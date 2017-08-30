import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public synchronized void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		response.getWriter().println("<h1>Mon Formulaire</h1><form action='./dynamic' method='post'> <div> <label for='nom'>Nom :</label>  <input type='text' id='name' name='name' /> </div> <div> Password: <input type='password' name='password'/> <br/><div class='button'> <button type='submit'>Envoyer votre message</button> </div>");
		}
}
