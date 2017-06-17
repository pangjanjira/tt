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
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import th.co.scb.service.ControlAccountService;

/**
 *
 * @author s62177
 */
public class ControlAccountInquiryRequestControl extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action").trim();
        try {
            if (action.equals("InquiryRequest")) { //Inquiry the pending List
                String jsonStr = request.getParameter("jsonRequest");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);

                ControlAccountService cs = new ControlAccountService();
                String controlAcc = new Gson().toJson(cs.inquiryControlAcc(jsonMap));
                out.print(controlAcc);

            } else if (action.equals("CreateRequest")) {
                String jsonStr = request.getParameter("jsonCreate");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                ControlAccountService cs = new ControlAccountService();
                String controlAcc = new Gson().toJson(cs.insertControl(jsonMap));
                out.print(controlAcc);
            }else if (action.equals("InquiryforUpdate")) {
                String jsonStr = request.getParameter("jsonData");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                ControlAccountService cs = new ControlAccountService();
                
                String controlAcc = new Gson().toJson(cs.findbyId(jsonMap));
                out.print(controlAcc);
            }else if (action.equals("UpdateRequest")) {
                String jsonStr = request.getParameter("jsonUpdate");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                ControlAccountService cs = new ControlAccountService();
                
                String controlAcc = new Gson().toJson(cs.updateControl(jsonMap));
                out.print(controlAcc);
            }else if (action.equals("DeleteRequest")) {
                String jsonStr = request.getParameter("jsonDelete");
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> jsonMap = gson.fromJson(jsonStr, stringStringMap);               
                ControlAccountService cs = new ControlAccountService();
                
                String controlAcc = new Gson().toJson(cs.deleteControl(jsonMap));
                out.print(controlAcc);
            }

        } finally {
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
            Logger.getLogger(ControlAccountInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControlAccountInquiryRequestControl.class.getName()).log(Level.SEVERE, null, ex);
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
