package co.simplon;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;

public class HelloServer {

	public static String enTete() {
		String retour;
		retour = integreHtml("entete.html");
		retour += integreHtml("menu.html");
		return retour;
	}

	public static String integreHtml(String nomFichier) {
		String retour = "";
		// lecture du fichier texte
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(nomFichier)));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				retour += ligne;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
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
		context.addServlet(ListeVillesServletJson.class, "/listeVillesJson");
		context.addServlet(StatefulServlet.class, "/stateful");
		context.addServlet(ServletLogout.class, "/logout");

		String[] welcomeFiles = { "index.html" };
		context.setWelcomeFiles(welcomeFiles);
		context.setResourceBase("./src/main/webapp/WEB-INF/");
		System.err.println("resourcesBase:" + context.getResourceBase());

		server.setStopAtShutdown(true);

		server.start();
		server.join();
	}
}