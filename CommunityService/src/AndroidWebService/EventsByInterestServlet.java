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
import org.CommunityService.Services.EventService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(urlPatterns = { "/Android/byInterest" })
public class EventsByInterestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventsByInterestServlet() {
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
		
		//.... .... .... ....
		
		String interestId = ((String) request.getParameter("ID"));
		
		//test
		//interestId="17";

		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();

		List<Event> list;
		list = EventService.getEventsByInterest(Integer.parseInt(interestId));
		
		if(list != null) {
			try {
				for (Event clean : list) {
					HibernateUtil.clean(clean);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			out.println(gson.toJson(list));
		}

		out.close();

	}

}