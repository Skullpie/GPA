package ctrl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bll.ReservataireManager;
import bll.VehiculeManager;
import bo.FluxJson;
import bo.Reservataire;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ValiderImmat
 */
@WebServlet(name = "ValiderImmat", urlPatterns = { "/auth/validerImmat" })
public class ValiderImmat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValiderImmat() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String immatriculation = request.getParameter("valeur");
		FluxJson fluxJson = new FluxJson();
		Vehicule veh = null;
		List<Reservataire> reservataires = null;
		try {
			veh = VehiculeManager.get(immatriculation);
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		
		try {
			reservataires = ReservataireManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		Gson gson = new Gson();
		
		fluxJson.setNombreDePlace(veh.getNombrePlace());
		fluxJson.setReservataires(reservataires);
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(fluxJson));
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
