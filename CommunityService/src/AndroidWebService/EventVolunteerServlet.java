package AndroidWebService;

import hibernate.HibernateProxyTypeAdapter;
import hibernate.HibernateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.EventService;
import org.CommunityService.Services.VolunteerService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(urlPatterns = { "/Android/getEventVolunteer" })
public class EventVolunteerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventVolunteerServlet() {
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
		String eventId = ((String) request.getParameter("eventID"));
		
		//test
//		volId = "18";
//		eventId = "21";

		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();

		EventVolunteer v;
		if (volId == null || volId.equals("") || eventId == null || eventId.equals("")) {
		} else {
			v = EventService.getEventVolunteerById(Integer.parseInt(eventId), Integer.parseInt(volId));
			try {
				HibernateUtil.clean(v);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println(gson.toJson(v));
		}

		out.close();

	}

}