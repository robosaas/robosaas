package utd.gae;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * Servlet implementation class GetRobots
 */

public class PlayerGetRobots extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> robots = new ArrayList<String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlayerGetRobots() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getRobots();
		response.getWriter()
				.print("<select name='availableRobots' id='availableRobots' size='10' style='width: 150px;' multiple='multiple'>");
		for (String robot : robots) {
			response.getWriter().print(
					"<option value='" + robot + "'>" + robot + "</option>");
		}
		response.getWriter().print("</select>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void getRobots() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Use CompositeFilter to combine multiple filters
		// Use class Query to assemble a query
		Query query = new Query("userdb");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery preparedQuery = datastore.prepare(query);

		for (Entity result : preparedQuery.asIterable()) {
			String packageName = (String) result.getProperty("folder");
			String robotName = (String) result.getProperty("robotName");
			packageName = packageName.replaceAll("/gs/deepti-hw.appspot.com/",
					"");
			packageName = packageName.replaceAll("/", "");
			robots.add(packageName + "." + robotName);
		}
	}

}
