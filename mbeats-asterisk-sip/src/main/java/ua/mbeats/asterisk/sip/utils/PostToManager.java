package ua.mbeats.asterisk.sip.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.mbeats.asterisk.sip.spring.beans.PostTo;

public class PostToManager {

	public static int doPost(Map<String, String> params) throws IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");

		PostTo post = (PostTo) ctx.getBean("host");
		String host = post.getHost();

		URL url = new URL("http://" + host + "/proc.php");
		URLConnection con = url.openConnection();

		// activate the output
		con.setDoOutput(true);
		PrintStream ps = new PrintStream(con.getOutputStream());

		int mapPosition = 0;
		String req = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			mapPosition++;

			req += key + "=" + val;
			if (mapPosition != params.size())
				req += "&";
		}
		// System.out.println("Request: "+req);
		// send your parameters to your site
		ps.print(req);

		con.getInputStream();

		// close the print stream
		ps.close();

		int responseCode = ((HttpURLConnection) con).getResponseCode();
		
		ctx.close();
		
		return responseCode;
	}

	public static int doGet(Map<String, String> params) throws IOException {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");

		PostTo post = (PostTo) ctx.getBean("host");
		String host = post.getHost();

		int mapPosition = 0;
		String req = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			mapPosition++;

			req += key + "=" + val;
			if (mapPosition != params.size())
				req += "&";
		}
		
		URL url = new URL("http://" + host + "/?"+req);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();

		con.disconnect();
		
		ctx.close();
		
		return responseCode;
	}
}
