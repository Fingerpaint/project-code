import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Tester {
	public static void main(String[] args) {
		try {
			String json = "{\"glossary\": {\"title\": \"example glossary\",\"GlossDiv\": { \"title\": \"S\",\"GlossList\": {\"GlossEntry\": {\"ID\": \"SGML\",\"SortAs\": \"SGML\",\"GlossTerm\": \"Standard Generalized Markup Language\",\"Acronym\": \"SGML\",\"Abbrev\": \"ISO 8879:1986\",\"GlossDef\": {\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\": [\"GML\", \"XML\"]},\"GlossSee\": \"markup\"}}}}}";
			Socket socket = new Socket("localhost", 6110);
			socket.getOutputStream().write(json.getBytes("US-ASCII"));
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}