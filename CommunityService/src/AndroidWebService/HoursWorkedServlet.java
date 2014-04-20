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
@WebServlet(urlPatterns = { "/Android/hoursWorked" })
public class HoursWorkedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HoursWorkedServlet() {
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

		String volId = ((String) request.getParameter("volID"));
		String hours = ((String) request.getParameter("hours"));
		String rating = ((String) request.getParameter("rating"));
		String bonus = ((String) request.getParameter("bonus"));
		
//		String volId = "18";
//		String hours = "4";
//		String rating = "100";
//		String bonus = "1.5";		
		
		try {
			
			Volunteer v = VolunteerService.getVolunteerById(Integer.parseInt(volId));
			if(v == null) {
				out.println("0");
				out.close();
				return;
			}
			
			VolunteerService.updateVolunteerPoints(v, Integer.parseInt(hours), Integer.parseInt(rating), Float.parseFloat(bonus));
			
			out.println("1");
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			
			out.println("0");
		}

		out.close();

	}

}