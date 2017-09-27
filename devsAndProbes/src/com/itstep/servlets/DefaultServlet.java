package com.itstep.servlets;

import java.util.*;
import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itstep.dbservices.DbService;
import com.itstep.dbservices.H2DbParams;
import com.itstep.entities.Device;

@WebServlet("/")
public class DefaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public DefaultServlet() {
		super();
		DbService.setDbParams(new H2DbParams());
		try {
			DbService.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Device> devices = new ArrayList<>();
		Device d = null;
		ResultSet res = null;
		try {
			try {
				res = DbService.executeSelect("SELECT * FROM DEVICES");
			} catch (org.h2.jdbc.JdbcSQLException ex) {
				DbService.firstJdbcRun();
				res = DbService.executeSelect("SELECT * FROM DEVICES");
			}
			while (res.next()) {
				d = new Device();
				d.setId(res.getInt("ID"));
				d.setName(res.getString("NAME"));
				d.setPort(res.getInt("PORT"));
				devices.add(d);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("devices", devices);
		request.getRequestDispatcher("startup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int sourceDevId = Integer.parseInt(request.getParameter("source"));
		int destDevId = Integer.parseInt(request.getParameter("destination"));
		String app = request.getParameter("app");
		String probeType = request.getParameter("probe_type");
		Random r = new Random();
		int probeId = r.nextInt(100000);
		int resCount;
		try {
			resCount = DbService.executeUpdate("INSERT INTO PROBES VALUES(?,?,?)", String.valueOf(probeId), app, probeType);
			System.out.println("Probe.updated: " + resCount);
			
			resCount = DbService.executeUpdate("INSERT INTO PROBES_RELATIONS VALUES(?,?,?,?)", 
					String.valueOf(r.nextInt(100000)), String.valueOf(probeId), String.valueOf(sourceDevId), String.valueOf(destDevId));
			System.out.println("ProbeRel.updated: " + resCount);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		doGet(request, response);
	}
}
