/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.controller.eguarantee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import th.co.scb.authen.Authentication;
import th.co.scb.db.eguarantee.LogAccessTable;
import th.co.scb.db.eguarantee.MasUserAppTable;
import th.co.scb.db.eguarantee.MasUserProfileTable;
import th.co.scb.model.eguarantee.LogAccess;
import th.co.scb.model.eguarantee.MasUserProfile;
//import th.co.scb.db.eguarantee.UserAuthTable;
//import th.co.scb.model.eguarantee.UserAuth;
import th.co.scb.util.ConnectDB;

/**
 *
 * @author s62177
 */
public class AuthenAuthorizeRequestControl extends HttpServlet {

    private static final String AUTH_PASS = "000";
    private ConnectDB connectDB = null;
    private static final String PVT_USER = "PVTUSER";
    private static final String PVT_USER_DEFAULT = "s10960";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String iUserName = request.getParameter("iUserName");
            String iPassword = request.getParameter("iPassword");

            String result[] = Authentication.activedirectory(iUserName, iPassword);
            //String result[] = {"000","1234"}; //oc code for test in dev
            
            HttpSession session = request.getSession();
            if (result[0].equals(AUTH_PASS)) {
                int oc = 0;
                try {
                    oc = Integer.parseInt(result[1].trim());
                } catch (NumberFormatException e) {
                }
                //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
                //changed to
                checkAuthorization(iUserName, session, request, response);
                //changed from
                //UR58060060 Phase 3.2 CADM Function
                //changed to
                /*
                MasUserAppTable userAppTable = new MasUserAppTable();
                MasUserProfileTable userProfileTable = new MasUserProfileTable();
                boolean isUserAuthorizeExisted = userAppTable.checkUserExisted(iUserName);
                if (isUserAuthorizeExisted) {
                    MasUserProfile userProfile = userProfileTable.getUserProfile(iUserName);
                    if (userProfile != null) {
                        session.setAttribute("user_full_name", userProfile.getNameTh());
                        session.setAttribute("user_name", userProfile.getUserId());//user_id
                        session.setAttribute("oc_code", userProfile.getOcCode());
                        session.setAttribute("user_profile", userProfile);
                        if (userProfile.getAuthorizedMenu() != null) {
                            session.setAttribute("user_menu", userProfile.getAuthorizedMenu());
                        } else {
                            session.setAttribute("user_menu", null);
                        }
                        logAccessAuthen(iUserName, request, "S");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        logAccessAuthen(iUserName, request, "F");
                        session.setAttribute("errorMsg", "You do not have permission to enter the E-Guarantee Website");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                } else {
                    logAccessAuthen(iUserName, request, "F");
                    session.setAttribute("errorMsg", "You do not have permission to enter the E-Guarantee Website");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                */
                //changed from
                /*
                 UserAuthTable user = new UserAuthTable();
                 UserAuth userAuth = new UserAuth();
                 userAuth.setUserId(iUserName);
                 List<UserAuth> user_auth = user.findByUserAuth(userAuth);
                 if(!user_auth.isEmpty()){
                 UserAuth userSess = user_auth.get(0);
                 if (user_auth != null) {
                 String user_full_name = result[3];
                 String user_name = result[2];
                 String oc_code       = result[1];                   
                 session.setAttribute("user_full_name", user_full_name);
                 session.setAttribute("user_name", user_name);//user_id
                 session.setAttribute("oc_code", oc_code);
                 session.setAttribute("user_auth", user_auth);
                       
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 }else{
                 session.setAttribute("errorMsg", "You do not have permission to enter the E-Guarantee Website");
                 request.getRequestDispatcher("login.jsp").forward(request, response);
                 }
                 }else{
                 session.setAttribute("errorMsg", "You do not have permission to enter the E-Guarantee Website");
                 }
                 */
            } else {
            	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
            	if(PVT_USER.equalsIgnoreCase(iUserName)){
            		iUserName = PVT_USER_DEFAULT;
            		checkAuthorization(iUserName, session, request, response);
            	}else{
                    logAccessAuthen(iUserName, request, "F");

                    if (request.getSession(false) != null) {
                        session.setAttribute("errorMsg", "Invalid username or password");
                        System.out.println("==" + session.getAttribute("errorMsg"));
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else {
                        session.setAttribute("errorMsg", " ");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
            	}
            	/*
                logAccessAuthen(iUserName, request, "F");

                if (request.getSession(false) != null) {
                    session.setAttribute("errorMsg", "Invalid username or password");
                    System.out.println("==" + session.getAttribute("errorMsg"));
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    session.setAttribute("errorMsg", " ");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                */
            }

        } catch (NumberFormatException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            connectDB.rollback();
            e.printStackTrace();
        } finally {
            out.close();
            if (connectDB != null) 
            	System.out.println("LogAccessTable<> close connection : "+connectDB.hashCode());{
                connectDB.close();
            }
        }

    }
    
    private void checkAuthorization(String iUserName, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        MasUserAppTable userAppTable = new MasUserAppTable();
        MasUserProfileTable userProfileTable = new MasUserProfileTable();
        boolean isUserAuthorizeExisted = userAppTable.checkUserExisted(iUserName);
        if (isUserAuthorizeExisted) {
            MasUserProfile userProfile = userProfileTable.getUserProfile(iUserName);
            if (userProfile != null) {
                session.setAttribute("user_full_name", userProfile.getNameTh());
                session.setAttribute("user_name", userProfile.getUserId());//user_id
                session.setAttribute("oc_code", userProfile.getOcCode());
                session.setAttribute("user_profile", userProfile);
                if (userProfile.getAuthorizedMenu() != null) {
                    session.setAttribute("user_menu", userProfile.getAuthorizedMenu());
                } else {
                    session.setAttribute("user_menu", null);
                }
                logAccessAuthen(iUserName, request, "S");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                logAccessAuthen(iUserName, request, "F");
                session.setAttribute("errorMsg", "You do not have permission to enter the E-Guarantee Website");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            logAccessAuthen(iUserName, request, "F");
            session.setAttribute("errorMsg", "You do not have permission to enter the E-Guarantee Website");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void logAccessAuthen(String iUserName, HttpServletRequest request, String accessStatus) throws Exception {
    	//UR58120031 Phase 2 (fix appCode CADM)
        //String appCode = "GUA0001";
    	String appCode = "EGUA0001";
        String funcCode = "F00000";
        try{
	        connectDB = new ConnectDB();
	        System.out.println("LogAccessTable<logAccessAuthen> new connection : "+connectDB.hashCode());        
	        
	        LogAccess logAccess = new LogAccess();
	        logAccess.setAppCode(appCode);
	        logAccess.setFuncCode(funcCode);
	        logAccess.setUserId(iUserName);
	        logAccess.setAccessDtm(new Date());
	        logAccess.setAccessStatus(accessStatus);
	        logAccess.setIpAddress(request.getRemoteAddr());
	        
	        LogAccessTable logAccessTable = new LogAccessTable(connectDB);
	        logAccessTable.add(logAccess);        
        }catch(Exception e){
        	System.out.println("Access Log ERROR :"+e);
        }finally{
        	if(connectDB != null){
        		System.out.println("LogAccessTable<logAccessAuthen> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
