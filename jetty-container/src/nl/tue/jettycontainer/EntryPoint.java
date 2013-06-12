package nl.tue.jettycontainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class EntryPoint {
	
	public void startServer() {
	    Server server = new Server(80);
	    
	    WebAppContext context = new WebAppContext();
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