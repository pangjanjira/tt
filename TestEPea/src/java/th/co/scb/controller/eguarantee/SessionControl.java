/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.controller.eguarantee;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import th.co.scb.db.eguarantee.MasUserAppTable;
import th.co.scb.db.eguarantee.MasUserProfileTable;
import th.co.scb.model.eguarantee.MasMenu;
//import th.co.scb.model.eguarantee.UserAuth;

/**
 *
 * @author s49099
 */
@WebServlet(name = "SessionControl", urlPatterns = {"/SessionControl"})
public class SessionControl extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	System.out.println("----- [BEGIN SessionControl] -----");
            String objectStr = request.getParameter("object").trim();
            if (objectStr.equals("UserProfile")){
            	
            	String funcCode = request.getParameter("funcCode");
            	
                HttpSession session = request.getSession(true);
                String userName = (String) session.getAttribute("user_name");
                String ocCode = (String) session.getAttribute("oc_code");
                List<Object> userProfile = new ArrayList<Object>();
                //UR58060060 Phase 3.2 CADM Function
                //changed to
                MasUserAppTable userAppTable = new MasUserAppTable();
                boolean isAuthorized = userAppTable.checkAuthorization(userName, funcCode);
                if (userName == null) {
                	System.out.println("session expired");
                    userProfile.add("");
                    userProfile.add("");
                    userProfile.add("N");
                }else{
                	System.out.println("session not expired");
                    if(!isAuthorized){
                    	System.out.println("isAuthorized == false");
                        userProfile.add(userName);
                        userProfile.add(ocCode);
                        userProfile.add("N");
                    }else{
                    	System.out.println("isAuthorized == true");
                    	userProfile.add(userName);
                        userProfile.add(ocCode);
                        userProfile.add("Y");
                    }	
                }
                String userProfileStr = new Gson().toJson(userProfile);
                out.println(userProfileStr);
            }else if(objectStr.equals("Menu")){
            	//List Menu
                HttpSession session = request.getSession(true);
                if(session.getAttribute("user_menu") != null){
                	try{
                		List<MasMenu> menu = (List<MasMenu>) session.getAttribute("user_menu");	
                	}catch(Exception e){
                		e.printStackTrace();
                	}
                }
            }else if(objectStr.equals("isApprover")){
            	
                HttpSession session = request.getSession(true);
                String userName = (String) session.getAttribute("user_name");
                String ocCode = (String) session.getAttribute("oc_code");
                List<Object> userProfile = new ArrayList<Object>();
                //UR58060060 Phase 3.2 CADM Function
                //changed to
                MasUserAppTable userAppTable = new MasUserAppTable();
                boolean isAuthorized = userAppTable.checkApproverUser(userName);
                if (userName == null) {
                	System.out.println("session expired");
                    userProfile.add("");
                    userProfile.add("");
                    userProfile.add("N");
                }else{
                	System.out.println("session not expired");
                    if(!isAuthorized){
                    	System.out.println("isAuthorized == false");
                        userProfile.add(userName);
                        userProfile.add(ocCode);
                        userProfile.add("N");
                    }else{
                    	System.out.println("isAuthorized == true");
                    	userProfile.add(userName);
                        userProfile.add(ocCode);
                        userProfile.add("Y");
                    }	
                }
                String userProfileStr = new Gson().toJson(userProfile);
                out.println(userProfileStr);
            }else if(objectStr.equals("Logout")){
            	//Release Session
            	System.out.println("----- [SessionControl ReleaseSession] -----");
            	HttpSession session = request.getSession();
            	session.invalidate();
            }else{
            	System.out.println("not UserProfile");
            }
            System.out.println("----- [END SessionControl] -----");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
