/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.scb.controller.eguarantee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import th.co.scb.service.AccountALSService;
import th.co.scb.service.ControlAccountService;
import th.co.scb.service.mq.EGuaranteeMQMessageException;
/**
 *
 * @author s61962
 */
public class AccountALSInquiryRequestControl extends HttpServlet{
    
    
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, Exception, EGuaranteeMQMessageException {
       response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action").trim();
       
        try {
            if(action.equals("InquiryRequest")) { //Inquiry the pending List
                String jsonStr = request.getParameter("jsonRequest");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);

                AccountALSService cs = new AccountALSService();
                String acctALS = new Gson().toJson(cs.inquiryAccALS(jsonMap));
                out.print(acctALS);

            }else if(action.equals("getAccALSDetail")){
                
                String jsonStr = request.getParameter("jsonDataDetail");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                AccountALSService cs = new AccountALSService();
                
                String acctALS = new Gson().toJson(cs.getAccALSDetail(jsonMap));
                out.print(acctALS);
                
            }else if(action.equals("deleteAccountALS")){
                String jsonStr = request.getParameter("jsonDelete");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                AccountALSService cs = new AccountALSService();
                
                String acctALS = new Gson().toJson(cs.deleteAcctALS(jsonMap));
                out.print(acctALS);
            }else if (action.equals("InquiryforUpdate")) {
                String jsonStr = request.getParameter("jsonData");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                AccountALSService cs = new AccountALSService();
                
                String acctALS = new Gson().toJson(cs.findbyId(jsonMap));
                out.print(acctALS);
            }else if (action.equals("CreateRequest")) {
                String jsonStr = request.getParameter("jsonCreate");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                AccountALSService cs = new AccountALSService();
                String acctALS = new Gson().toJson(cs.insertAcctALS(jsonMap,request));
                out.print(acctALS);
            }else if (action.equals("UpdateRequest")) {
                String jsonStr = request.getParameter("jsonUpdate");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                AccountALSService cs = new AccountALSService();
                
                String acctALS = new Gson().toJson(cs.updateAcctALS(jsonMap,request));
                out.print(acctALS);
            }
        }finally {
            out.close();
        }
        
   }
    
    
   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ControlAccountInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControlAccountInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EGuaranteeMQMessageException ex) {
           Logger.getLogger(AccountALSInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AccountALSInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AccountALSInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EGuaranteeMQMessageException ex) {
           Logger.getLogger(AccountALSInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
       }
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
