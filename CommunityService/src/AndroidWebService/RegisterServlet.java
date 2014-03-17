package AndroidWebService;

import hibernate.HibernateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 */
@WebServlet(urlPatterns = { "/Android/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		String userName = ((String) request.getParameter("username"));
		String password = ((String) request.getParameter("password"));
		String firstName = ((String) request.getParameter("firstName"));
		String lastName = ((String) request.getParameter("lastName"));
		String phoneNumber = ((String) request.getParameter("phoneNumber"));
		String email = ((String) request.getParameter("email"));
		
		try {
			VolunteerService.registerVolunteer(
					userName,
					password,
					phoneNumber,
					email,
					firstName,
					lastName );
			
			out.println("1");
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			
			out.println("0");
		}

		out.close();

	}

}