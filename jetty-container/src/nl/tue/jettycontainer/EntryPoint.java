package nl.tue.jettycontainer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class EntryPoint {
	
	private static final String QUIT = "quit";
	
	public void startServer() throws MalformedURLException, IOException {
	    Server server = new Server(80);
	    
	    WebAppContext context = new WebAppContext();
	    context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
	    context.setServer(server);
	    context.setContextPath("/");

	    String url = getClass().getClassLoader().getResource("war").toString();
	    context.setWar(url);
	 
	    server.setHandler(context);

		try (Scanner scanner = new Scanner(System.in)) {
			server.start();
			System.out.println("\n\nType \"" + QUIT + "\" to close the server\n\n");
			while (true) {
				if (scanner.next().equals(QUIT)) {
					break;
				}
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			throw new Error(e);
		}
	}
 
	public static void main(String[] args) throws Exception {
		EntryPoint entryPoint = new EntryPoint();
		entryPoint.startServer();
	}
}