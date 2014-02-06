package AndroidWebService;

import hibernate.HibernateProxyTypeAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 */
@WebServlet(urlPatterns = { "/Android/getEvents" })
public class VolunteerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int kindVolQuery = 1, // id = name
			kindEventVolQuery = 2, // id = volunteer id
			kindEventQuery = 3, // id = event id

			kindFindQuery = 4, // id = null (returns all events)
			kindInterestQuery = 5, // id = null (returns all interests)
			kindEventInterestQuery = 6; // id = event id

	public VolunteerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String volunteerId;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		volunteerId = ((String) request.getParameter("ID"));

		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();
		
		List<Volunteer> vList;
		Volunteer v;
		if (volunteerId == null || volunteerId.equals("")) {
			vList = VolunteerService.getVolunteers();
			r(vList);
			out.println(gson.toJson(vList));
		} else {
			v = VolunteerService.getVolunteerByName(volunteerId);
			r(v);
			out.println(gson.toJson(v));
		}

		out.close();

	}

	public void r(Object j) {
		for (Field field : j.getClass().getDeclaredFields()) {
			try {
				if (field.get(j) instanceof HibernateProxy) {
					field.set(j, null);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}