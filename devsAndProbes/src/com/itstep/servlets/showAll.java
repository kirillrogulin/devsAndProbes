package com.itstep.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itstep.entities.*;

@WebServlet("/showAll")
public class showAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn = null;
	static {
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/devsandprobes", "sa", "");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    
    public showAll() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Statement st = null;
		List<AllProbesResult> allProbes = new ArrayList<>();
		AllProbesResult allP = null;
		if(conn == null) {
			System.err.println("NO CONNECTION!!!\n\n");
		}
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("");
			while(res.next()) {
				allP = new AllProbesResult();
				allP.setProbeRel(res.getInt("PROBE_RELATIONS.ID"));
				allP.setProbeId(res.getInt("PROBES.ID"));
				allP.setProbeApp(res.getString("PROBES.APP"));
				allP.setProbeType(res.getString("PROBES.TYPE"));
				allP.setProbeSourceDevName(res.getString("DEVICES.NAME"));
				allP.setProbeSourceDevPort(res.getInt("DEVICES.PORT"));
				allP.setProbeDestDevName(res.getString("DEVICES.NAME"));
				allP.setProbeDestDevPort(res.getInt("DEVICES.PORT"));
				allProbes.add(allP);
			}
		} catch (Exception ex) { ex.printStackTrace(); }
		request.setAttribute("AllProbes", allProbes);
		request.getRequestDispatcher("showAllProbes.jsp").forward(request, response);
	}

}
