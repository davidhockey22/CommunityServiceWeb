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

import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.Services.SkillService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 */
@WebServlet(urlPatterns = { "/Android/getSkills" })
public class SkillsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public SkillsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String SkillId;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		SkillId = ((String) request.getParameter("ID"));

		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();

		List<Skill> list = null;
		Skill o;
		if (SkillId == null || SkillId.equals("")) {
			try {
				list = SkillService.getSkills();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				for (Skill clean : list) {
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
		} else {
			o = SkillService.getSkillById(Integer.parseInt(SkillId));
			try {
				HibernateUtil.clean(o);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println(gson.toJson(o));
		}

		out.close();

	}

}