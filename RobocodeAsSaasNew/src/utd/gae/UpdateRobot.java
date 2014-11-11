package utd.gae;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UpdateRobot extends HttpServlet {
	public static final String BUCKETNAME = "deepti-hw.appspot.com";

	 private BlobstoreService blobstoreService = BlobstoreServiceFactory
			   .getBlobstoreService();
			 private static final Logger logger = Logger
			   .getLogger(SaveRobot.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String filename1 = req.getParameter("robotName");
		String packagename = req.getParameter("packageName");
		String filename = null;
		if(packagename!=null){
		filename1=filename1+".java";
		filename = "/gs/"+BUCKETNAME+"/"+packagename+"/"+filename1;}
		else{
			filename1=filename1+".java";
			filename = "/gs/"+BUCKETNAME+"/"+filename1;
		}
		FileService fileService = FileServiceFactory.getFileService();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.getWriter().println("Please login Code");
		}

		else {
			resp.setContentType("text/plain");
			boolean lockForRead = false;
			
			AppEngineFile readableFile = new AppEngineFile(filename);
			FileReadChannel readChannel = fileService.openReadChannel(readableFile, lockForRead);

			// Read the file in whichever way you'd like
			BufferedReader reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));
			String line;
			while((line=reader.readLine() )!=null){
			resp.getWriter().println(line);
			}
			readChannel.close();
			
			
		}
	}
}
