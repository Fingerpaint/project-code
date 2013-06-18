package nl.tue.jettycontainer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class EntryPoint {

	private static final int PORT = 80;
	
	public void startServer() throws MalformedURLException, IOException {
		startServer(PORT);
	}

	public void startServer(int portNumber) throws MalformedURLException, IOException {
	    Server server = new Server(portNumber);
	    
	    WebAppContext context = new WebAppContext();
	    context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
	    context.setServer(server);
	    context.setContextPath("/");

	    String url = getClass().getClassLoader().getResource("war").toString();
	    context.setWar(url);
	 
	    server.setHandler(context);
	    try {
	      server.start();
	      System.in.read();
	      server.stop();
	      server.join();
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.exit(100);
	    }
	}
 
	public static void main(String[] args) throws Exception {
		int portNumber = PORT;
		if (args.length > 0) {
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
				// okay, use default port
			}
		}

		EntryPoint entryPoint = new EntryPoint();
		entryPoint.startServer(portNumber);
	}
}
