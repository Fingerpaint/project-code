package nl.tue.jettycontainer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class EntryPoint {
	
	public void startServer() throws MalformedURLException, IOException {
	    Server server = new Server(80);
	    
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
		EntryPoint entryPoint = new EntryPoint();
		entryPoint.startServer();
	}
}