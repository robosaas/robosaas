package utd.gae;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class DeleteRobot extends HttpServlet {
	public static final String BUCKETNAME = "deepti-hw.appspot.com";

	 private BlobstoreService blobstoreService = BlobstoreServiceFactory
			   .getBlobstoreService();
			 private static final Logger logger = Logger
			   .getLogger(SaveRobot.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String filename1 = req.getParameter("robotName");
		String filename2="";
		String nickName="deeps";
		String packagename = req.getParameter("packageName");
		String filename = null;
		if(packagename!=null){
		filename2=filename1+".java";
		filename = "/gs/"+BUCKETNAME+"/"+packagename+"/"+filename2;}
		else{
			filename2=filename1+".java";
			filename = "/gs/"+BUCKETNAME+"/"+filename2;
		}
		FileService fileService = FileServiceFactory.getFileService();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.getWriter().println("Please login Code");
		}

		else {
			AppEngineFile deleteFile = new AppEngineFile(filename);
			fileService.delete(deleteFile);
		}
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		//Use CompositeFilter to combine multiple filters
		// Use class Query to assemble a query
		String packageName = "/gs/deepti-hw.appspot.com/"+packagename;
		resp.getWriter().println("robotName:"+filename1+"folder:"+packageName+"domain:"+"domain2"+"nickName:"+nickName);
		Query q = new Query("userdb")
        .addFilter("robotName", Query.FilterOperator.EQUAL,filename1 )
        .addFilter("folder", Query.FilterOperator.EQUAL,packageName)
        .addFilter("domain", Query.FilterOperator.EQUAL,"domain2" )
        .addFilter("nickName", Query.FilterOperator.EQUAL,nickName );
		

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		
		for (Entity result : pq.asIterable()) {
		  
		  datastore.delete(result.getKey());
		}
	}
}
