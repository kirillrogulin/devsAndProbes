package com.itstep.servlets;

import java.util.*;
import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itstep.entities.Device;

@WebServlet("/")
public class DefaultServlet extends HttpServlet {
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
    
    public DefaultServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Device> devices = new ArrayList<>();
		Device d = null;
		Statement st = null;
		if(conn == null) {
			System.err.println("NO CONNECTION!!!\n\n");
		}
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM DEVICES");
			while(res.next()) {
				d = new Device();
				d.setId(res.getInt("ID"));
				d.setName(res.getString("NAME"));
				d.setPort(res.getInt("PORT"));
				devices.add(d);
			}
		} catch (Exception ex) { ex.printStackTrace(); }
		finally { try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} }
		request.setAttribute("devices", devices);
		request.getRequestDispatcher("startup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sourceDevId = Integer.parseInt(request.getParameter("source"));
		int destDevId = Integer.parseInt(request.getParameter("destination"));
		String app = request.getParameter("app");
		String probeType = request.getParameter("probe_type");
		PreparedStatement pstProbe = null;
		PreparedStatement pstProbeRel = null;
		Random r = new Random();
		int probeId = r.nextInt(100000);
		int resCount;
		try {
			pstProbe = conn.prepareStatement("INSERT INTO PROBES VALUES(?,?,?)");
			pstProbe.setInt(1, probeId);
			pstProbe.setString(2, app);
			pstProbe.setString(3, probeType);
			System.out.println("pstProbe.updated: " + pstProbe.executeUpdate());
			
			pstProbeRel = conn.prepareStatement("INSERT INTO PROBES_RELATIONS VALUES(?,?,?,?)");
			pstProbeRel.setInt(1, r.nextInt(100000));
			pstProbeRel.setInt(2, probeId);
			pstProbeRel.setInt(3, sourceDevId);
			pstProbeRel.setInt(4, destDevId);
			System.out.println("pstProbeRel.updated: " + pstProbeRel.executeUpdate());
		} catch (Exception ex) { ex.printStackTrace(); }
		request.getRequestDispatcher("startup.jsp").forward(request, response);
	}
}
