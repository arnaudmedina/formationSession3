import java.io.BufferedReader;
import java.io.FileReader;

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;

public class HelloServer {

	public static String enTete() {
		String retour;
		retour = integreHtml("src/main/entete.html");
		retour += integreHtml("src/main/menu.html");
		return retour;
	}

	public static String integreHtml(String nomFichier) {
		String retour = "";
		// lecture du fichier texte
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomFichier));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				retour += ligne;
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return retour;
	}

	public static void main(String args[]) throws Exception {
		Server server = new Server(9093);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		server.setHandler(context);
		context.addServlet(ServletVilleMaj.class, "/villeMAJ");
		context.addServlet(FormServlet.class, "/form");
		context.addServlet(HelloGenericServlet.class, "/hello");
		context.addServlet(HelloGenericServlet.class, "/");
		context.addServlet(ListeVillesServlet.class, "/listeVilles");
		context.addServlet(StatefulServlet.class, "/stateful");
		context.addServlet(ServletLogout.class, "/logout");

		String[] welcomeFiles = { "index.html" };
		context.setWelcomeFiles(welcomeFiles);
		context.setResourceBase("./src/main/resources/");
		System.err.println("resourcesBase:" + context.getResourceBase());

		server.setStopAtShutdown(true);

		server.start();
		server.join();
	}
}