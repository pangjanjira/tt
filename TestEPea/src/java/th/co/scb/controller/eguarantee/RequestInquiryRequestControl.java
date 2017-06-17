/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.controller.eguarantee;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.service.ETimeService;
import th.co.scb.service.eguarantee.RequestService;

/**
 *
 * @author s49099
 */
@WebServlet(name = "RequestInquiryRequestControl", urlPatterns = {"/RequestInquiryRequestControl"})
public class RequestInquiryRequestControl extends HttpServlet {

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
        String action = request.getParameter("action").trim();
        try {
            if (action.equals("InquiryRequest")){ //Inquiry the pending List
                String jsonStr = request.getParameter("jsonRequest");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
                Map<String,String> jsonMap = gson.fromJson(jsonStr, stringStringMap);
                
                RequestService rs = new RequestService();
                String gpGuaStr = new Gson().toJson(rs.inquiry(jsonMap));
                out.print(gpGuaStr);
            } else if (action.equals("getInquiryDetail")) {
                String id = request.getParameter("id").trim();
                RequestService rs = new RequestService();
                String gpGuaStr = new Gson().toJson(rs.getMoniorDetail(id));
                out.print(gpGuaStr);
            } else if (action.equals("getParentId")) { //UR58120031 getParentId add by Tana L. @12022016
                String id = request.getParameter("id").trim();
                RequestService rs = new RequestService();
                String parentId = rs.getParentId(id);
                out.print(parentId);
            } else if(action.equals("exportExcel")){
                String jsonStr = request.getParameter("jsonRequest");
                String username = request.getParameter("username").trim();
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
                Map<String,String> jsonMap = gson.fromJson(jsonStr, stringStringMap);
                
                RequestService rs = new RequestService();
                String fileName = rs.inquiryRequestForExportExcel(jsonMap, username);  
                delay(5);
                out.print(fileName);
            }
            
        } finally {            
            out.close();
        }
    }
    
    public void delay(int sec) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            System.out.println("=========== delay for " + String.valueOf(sec) + " sec ============");
            Calendar dc = Calendar.getInstance(Locale.US);
            System.out.println("----- BEGIN DELAY: " + sdf.format(dc.getTime()) + " -----");
            java.util.concurrent.TimeUnit.SECONDS.sleep(sec);
            dc = Calendar.getInstance(Locale.US);
            System.out.println("----- END DELAY: " + sdf.format(dc.getTime()) + " -----");
        } catch (Exception e) {
            e.printStackTrace();
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
