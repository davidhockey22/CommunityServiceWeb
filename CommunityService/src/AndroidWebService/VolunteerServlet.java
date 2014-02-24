package AndroidWebService;

import hibernate.HibernateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 */
@WebServlet(urlPatterns = { "/Android/getVolunteers" })
public class VolunteerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VolunteerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

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
		// b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();

		List<Volunteer> vList;
		Volunteer v;
		if (volunteerId == null || volunteerId.equals("")) {
			vList = VolunteerService.getVolunteers();
			try {
				for(Volunteer clean: vList){
					HibernateUtil.clean(clean);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			out.println(gson.toJson(vList));
		} else {
			v = VolunteerService.getVolunteerByName(volunteerId);
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