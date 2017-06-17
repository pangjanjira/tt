/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.controller.eguarantee;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.service.ETimeService;
import th.co.scb.service.eguarantee.RequestService;

/**
 *
 * @author s49099
 */
@WebServlet(name = "RequestMonitoringRequestControl", urlPatterns = {"/RequestMonitoringRequestControl"})
public class RequestMonitoringRequestControl extends HttpServlet {

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
            if (action.equals("InquiryMonitor")){ //Inquiry the pending List
                String taxId = request.getParameter("taxId").trim();
                String accountNo = request.getParameter("accountNo").trim();
                String expireDateFrom = request.getParameter("expireDateFrom").trim();
                String expireDateTo = request.getParameter("expireDateTo").trim();
                String egpStatus = request.getParameter("egpStatus").trim(); //add by Malinee T. UR58100048 @20151224
                String requestType = request.getParameter("requestType").trim(); //UR58120031 Request Type add by Tana L. @15022016
                
                RequestService rs = new RequestService();
                //String gpGuaStr = new Gson().toJson(rs.inquiryMonitor(taxId, accountNo, expireDateFrom, expireDateTo));
                //String gpGuaStr = new Gson().toJson(rs.inquiryMonitor(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus)); //add by Malinee T. UR58100048 @20151224
                String gpGuaStr = new Gson().toJson(rs.inquiryMonitor(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus, requestType)); //UR58120031  add by Tana L. @15022016
                
                out.print(gpGuaStr);
            } else if (action.equals("getMonitorDetail")) {
                String id = request.getParameter("id").trim();
                RequestService rs = new RequestService();
                String gpGuaStr = new Gson().toJson(rs.getMoniorDetail(id));
                out.print(gpGuaStr);
//            } else if (action.equals("getParentId")) { //UR58120031 getParentId add by Tana L. @12022016
//                String id = request.getParameter("id").trim();
//                RequestService rs = new RequestService();
//                String parentId = rs.getParentId(id);
//                out.print(parentId);
            //UR58120031 Phase 2 (protect appprove from multiple screen)
            } else if (action.equals("checkDupApproval")) { //checkDupApproval add by Tana L. @18042016
                String id = request.getParameter("id").trim();
                RequestService rs = new RequestService();
                try {
                    if (rs.isDupApproval(id)){                        
                        out.print("DUPLICATE");
                    } else {
                        out.print("NOT DUPLICATE");
                    }
                } catch (Exception ex) {
                    out.print("Cannot connect to DB");
                }
            } else if (action.equals("checkTimeOffHostALS")) {
                ETimeService eTimeService = new ETimeService();
                try {
                    if (eTimeService.isTimeOffHostALS().isTimeOffHostALS()){                        
                        out.print("OFFLINE");
                    } else {
                        out.print("ONLINE");
                    }
                } catch (Exception ex) {
                    out.print("Cannot connect to ALS");
                }
            } else if (action.equals("exportExcel")){
                String taxId = request.getParameter("taxId").trim();
                String accountNo = request.getParameter("accountNo").trim();
                String username = request.getParameter("username").trim();
                String expireDateFrom = request.getParameter("expireDateFrom").trim();
                String expireDateTo = request.getParameter("expireDateTo").trim();
                String egpStatus = request.getParameter("egpStatus").trim(); //add by Malinee T. UR58100048 @20151224
                String requestType = request.getParameter("requestType").trim(); //UR58120031 Request Type add by Tana L. @15022016
                
                RequestService rs = new RequestService();
                //String fileName = rs.inquiryMonitorForExportExcel(taxId, accountNo, expireDateFrom, expireDateTo, username);
                //String fileName = rs.inquiryMonitorForExportExcel(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus, username); //add by Malinee T. UR58100048 @20151224
                String fileName = rs.inquiryMonitorForExportExcel(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus, requestType, username); //UR58120031  add by Tana L. @15022016 
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
