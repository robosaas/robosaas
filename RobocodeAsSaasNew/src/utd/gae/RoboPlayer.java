package utd.gae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;

public class RoboPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String BUCKETNAME = "deepti-hw.appspot.com";
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoboPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	String selectedRobots;
	String gameRounds;
	HttpServletResponse finalResponse;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		gameRounds = request.getParameter("gameRounds");

		if ((gameRounds == null) || (!gameRounds.matches("[-+]?\\d*\\.?\\d+"))) {
			gameRounds = "10";
		}

		selectedRobots = request.getParameter("selectedRobots");
		String[] robots = selectedRobots.split(",");
		for (String robot : robots) {
			sendForCompilation(robot);
		}

		String result = sendBattle();

		response.getWriter().println("<table class='tftable' border='1'>");
		response.getWriter().println("<tr><th>Robot Name</th><th>Total Score</th><th>Survival</th><th>Surv Bonus</th><th>Bullet Dmg</th><th>Bullet Bonus</th><th>Ram Dmg * 2</th><th>Ram Bonus</th><th>1sts</th><th>2nds</th><th>3rds</th></tr>");
        response.getWriter().println(result);
	}

	protected String sendForCompilation(String robot) throws IOException {

		String[] roboInfo = robot.split("\\.");
		String fileName = roboInfo[1] + ".java";
		String folderName = roboInfo[0];

		robot = robot.replace(".", "/");
		robot = robot + ".java";
		String filenameNew = "/gs/" + BUCKETNAME + "/" + folderName + "/"
				+ fileName;
		FileService fileService = FileServiceFactory.getFileService();

		AppEngineFile readableFile = new AppEngineFile(filenameNew);
		FileReadChannel readChannel;

		readChannel = fileService.openReadChannel(readableFile, true);

		BufferedReader reader = new BufferedReader(Channels.newReader(
				readChannel, "UTF8"));
		return sendFile(reader, fileName, folderName);
	}

	private String sendFile(BufferedReader reader, String fileName,
			String folderName) throws IOException {
		// byte[] bytes = fileContent.getBytes("UTF-8");
		URL obj = new URL("http://robocode3.cloudapp.net/thirdserver/upload");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);

		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "text/java; charset=utf-8");
		// String length = ((Integer) bytes.length).toString();
		// con.setRequestProperty("Content-length", length);

		String content_disp = "filename=\"" + fileName + "\"";
		if (folderName != null) {
			content_disp = content_disp + ";foldername=\"" + folderName + "\"";
		}
		con.setRequestProperty("content-disposition", content_disp);

		OutputStream out = con.getOutputStream();
		// out.write(bytes);
		PrintWriter o = new PrintWriter(new OutputStreamWriter(out));
		String line = null;
		while ((line = reader.readLine()) != null) { // Read file

			o.println(line); // Write to output stream
			o.flush();
		}
		out.flush();
		out.close();
		con.connect();
		con.setReadTimeout(60000);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));

		StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("Done");
		return response.toString();
	}

	protected String sendBattle() throws IOException {
		URL obj = new URL(
				"http://robocode3.cloudapp.net/thirdserver/RoboPlayer");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);

		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "text/java; charset=utf-8");
		con.setRequestProperty("selectedRobots", selectedRobots);
		con.setRequestProperty("gameRounds", gameRounds);
		con.connect();
		con.setReadTimeout(60000);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));

		StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("Done");
		return response.toString();
	

	}
}
