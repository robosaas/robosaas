package utd.gae;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.files.GSFileOptions.GSFileOptionsBuilder;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SaveRobot extends HttpServlet {
	public static final String BUCKETNAME = "deepti-hw.appspot.com";

	 private BlobstoreService blobstoreService = BlobstoreServiceFactory
			   .getBlobstoreService();
			 private static final Logger logger = Logger
			   .getLogger(SaveRobot.class.getCanonicalName());
			 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String robotCode= req.getParameter("finalCode");
		String filename1 = req.getParameter("robotName");
		String packagename = req.getParameter("packageName");
		filename1=filename1+".java";
		resp.getWriter().print(robotCode);
		resp.setContentType("text/plain");
	    FileService fileService = FileServiceFactory.getFileService();
	    GSFileOptionsBuilder optionsBuilder = new GSFileOptionsBuilder()
	       .setBucket(BUCKETNAME)
	       .setKey(packagename+"/"+filename1)
	       .setMimeType("text/java")
	       .setAcl("public-read");
	    AppEngineFile writableFile =
	         fileService.createNewGSFile(optionsBuilder.build());
	    boolean lock = false;
	     FileWriteChannel writeChannel =
	         fileService.openWriteChannel(writableFile, lock);
	     
	     PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
	     out.println(robotCode);
	     out.close();
	     String path = writableFile.getFullPath();
	     writableFile = new AppEngineFile(path);
	     lock = true;
	     writeChannel = fileService.openWriteChannel(writableFile, lock);
	     writeChannel.write(ByteBuffer.wrap
	               ("".getBytes()));

	     writeChannel.closeFinally();
	     DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	     UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			if (user == null) {
				resp.getWriter().println("Please login Code");
			}

			else {
				String folder = "/gs/"+BUCKETNAME+"/"+packagename+"/";
				String userName=user.getNickname();
				 resp.getWriter().println(userName);
			   
			    String domain = "domain2";
			    String roboName = req.getParameter("robotName");
			    resp.getWriter().println(folder);
			    String userPK = "deepti"+domain+roboName;
			    resp.getWriter().println(domain);
			    
			    Entity updating = new Entity("userdb",userPK );
			    updating.setProperty("user", user);
			    updating.setProperty("nickName", "deeps");
			    updating.setProperty("domain", domain);
			    updating.setProperty("folder", folder);
			    updating.setProperty("robotName", roboName);
			    ds.put(updating);
			}
		 }
}
