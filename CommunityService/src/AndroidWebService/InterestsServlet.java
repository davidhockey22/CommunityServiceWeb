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

import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.Services.InterestService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 */
@WebServlet(urlPatterns = { "/Android/getInterests" })
public class InterestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InterestsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String InterestId;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		InterestId = ((String) request.getParameter("ID"));

		GsonBuilder b = new GsonBuilder();
		// b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();

		List<Interest> vList = null;
		Interest v;
		if (InterestId == null || InterestId.equals("")) {
			try {
				vList = InterestService.getInterests();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				for (Interest clean : vList) {
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
			v = InterestService.getInterestsById(Integer.parseInt(InterestId));
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
