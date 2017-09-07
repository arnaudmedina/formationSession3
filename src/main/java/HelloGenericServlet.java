import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloGenericServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// On envoie le header HTML
		response.setContentType("text/html");

		response.getWriter().println(HelloServer.enTete());
		response.getWriter().println("\n\nHelloGenericServlet vous dit bonjour !\n" + "J'ai bien reçu votre requête :\n" + request);
	}
}