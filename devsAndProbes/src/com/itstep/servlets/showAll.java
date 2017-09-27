package com.itstep.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itstep.dbservices.DbService;
import com.itstep.dbservices.H2DbParams;
import com.itstep.entities.*;

@WebServlet("/showAll")
public class showAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public showAll() {
        super();
        DbService.setDbParams(new H2DbParams());
		try {
			DbService.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Started showAll.doGet()");
		String queryAll = "select " +
			    "PROBES_RELATIONS.ID REL_ID," +
			    "PROBES_RELATIONS.PROBE_ID P_ID," +
			    "(select PROBES.APP from PROBES where PROBES.ID=PROBES_RELATIONS.PROBE_ID) P_APP," +
			    "(select PROBES.TYPE from PROBES where PROBES.ID=PROBES_RELATIONS.PROBE_ID) P_TYPE," +
			    "(select DEVICES.NAME from DEVICES where DEVICES.ID=PROBES_RELATIONS.SOURCE_ID) REL_S_NAME," +
			    "(select DEVICES.PORT from DEVICES where DEVICES.ID=PROBES_RELATIONS.SOURCE_ID) REL_S_PORT," +
			    "(select DEVICES.NAME from DEVICES where DEVICES.ID=PROBES_RELATIONS.DESTINATION_ID) REL_D_NAME," +
			    "(select DEVICES.PORT from DEVICES where DEVICES.ID=PROBES_RELATIONS.DESTINATION_ID) REL_D_PORT " +
			    "from PROBES_RELATIONS";
		List<AllProbesResult> allProbes = new ArrayList<>();
		AllProbesResult allP = null;
		
		try {
			ResultSet res = DbService.executeSelect(queryAll);
			System.out.println("showAll.doGet(): executeSelect");
			System.out.println(res.toString());
			while(res.next()) {
				allP = new AllProbesResult();
				allP.setProbeRel(res.getInt("REL_ID"));
				allP.setProbeId(res.getInt("P_ID"));
				allP.setProbeApp(res.getString("P_APP"));
				allP.setProbeType(res.getString("P_TYPE"));
				allP.setProbeSourceDevName(res.getString("REL_S_NAME"));
				allP.setProbeSourceDevPort(res.getInt("REL_S_PORT"));
				allP.setProbeDestDevName(res.getString("REL_D_NAME"));
				allP.setProbeDestDevPort(res.getInt("REL_D_PORT"));
				allProbes.add(allP);
			}
		} catch (Exception ex) { ex.printStackTrace(); }
		System.out.println("showAll.doGet(): allProbes.size(): " + allProbes.size());
		request.setAttribute("allProbes", allProbes);
		request.getRequestDispatcher("showAllProbes.jsp").forward(request, response);
		System.out.println("showAll.doGet(): forwarded!");
	}

}
