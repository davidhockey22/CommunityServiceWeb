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

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.EventService;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 */
@WebServlet(urlPatterns = { "/Android/orgApprove" })
public class OrgApproveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrgApproveServlet() {
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
		
		String eventId = ((String) request.getParameter("eventId"));
		String volId;
		String total = ((String) request.getParameter("total"));
		int tot = Integer.parseInt(total);
		
		//test
//		String eventId = "23";
//		String volId = "14";
//		int tot = 1;
		
		for(int i = 0; i < tot; i++) {
			
			try {
				
				volId = ((String) request.getParameter("approve" + i));
				boolean succeeded = VolunteerService.approveVolunteer(Integer.parseInt(volId), Integer.parseInt(eventId));
				if(!succeeded) {
					
					out.println("0");
					out.close();
					return;
				}
				
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				
				out.println("0");
				out.close();
				return;
			}			
		}
				
		out.println("1");		
		out.close();
	}

}