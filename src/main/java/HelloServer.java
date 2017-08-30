
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;

public class HelloServer {

	public static void main(String args[]) throws Exception{
	 Server server = new Server(9093);
	 ServletHandler handler = new ServletHandler();
	 server.setHandler(handler);
	 handler.addServletWithMapping(HelloGenericServlet.class, "/hello");
	 handler.addServletWithMapping(StatefulServlet.class, "/stateful");
	 handler.addServletWithMapping(FormServlet.class, "/form");
	 handler.addServletWithMapping(DynamicServlet.class, "/dynamic");
	 server.start();
	 server.join();
	 }
}