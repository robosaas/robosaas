package utd.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class GetRobot extends HttpServlet { 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		//Use CompositeFilter to combine multiple filters
		// Use class Query to assemble a query
		Query q = new Query("userdb");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);


		for (Entity result : pq.asIterable()) {
		  String packageName = (String) result.getProperty("folder");
		  String robotName = (String) result.getProperty("robotName");
		  packageName=packageName.replaceAll("/gs/deepti-hw.appspot.com/", "") ;
		  packageName=packageName.replaceAll("/", "") ;
		  resp.getWriter().println(packageName + "." + robotName + "<br>");
		}
	}
}
