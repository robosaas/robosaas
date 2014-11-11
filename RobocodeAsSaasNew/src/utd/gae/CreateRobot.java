package utd.gae;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class CreateRobot extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.getWriter().println("Please login Code");
		}

		else {
			String userName=user.getNickname();
			String robotName = req.getParameter("robotName");
			String packageName = req.getParameter("packageName");
			ServletContext context = getServletContext();
			String defaultCodeFilePath = context
					.getRealPath("/WEB-INF/resources/robocode/newrobot.txt");
			Scanner scan = new Scanner(new File(defaultCodeFilePath), "UTF-8");
			StringBuffer buffer = new StringBuffer();
			String readData = "";
			while ((scan.hasNext()) && ((readData = scan.nextLine()) != null)) {
				buffer.append(readData).append('\n');
			}
			String code = buffer.toString();
			code = code.replaceAll("PACKAGENAME", packageName);
			code = code.replaceAll("CLASSNAME", robotName);
			code = code.replaceAll("(your name here)", userName);
			resp.getWriter().println(code);
			scan.close();
		}
	}
}
