/**
 * 
 */
package th.co.scb.controller.eguarantee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class TestReadXML extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		//----------- Reading a request InputStream ---------------
				StringBuilder stringBuilder = new StringBuilder();
		        BufferedReader bufferedReader = null;
		        String inputStr = "";
		        String fileName = "";

		        try {
		        	//1. Receive Parameters from URL
		            //POST file
		            // get access to file that is uploaded from client
		            Part p1 = request.getPart("file");
		            InputStream inputStream = p1.getInputStream();
		 
		            // read filename which is sent as a part
		            Part p2  = request.getPart("filename");
		            Scanner s = new Scanner(p2.getInputStream());
		            fileName = s.nextLine();    // read filename from stream
		            
		            //InputStream inputStream = request.getInputStream();

		            if (inputStream.available()>0) {
		            //if (inputStream != null) {
		            	
		                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Constants.ENCODING));
		                String b = "";
		                
		                bufferedReader.read();
		                while ((b = bufferedReader.readLine()) != null) {
		                    stringBuilder.append(b.trim());
		                }
		                
		                
		            } else {
		            	System.out.println("-------- no data-----------");
		            }
		            
		            inputStr = stringBuilder.toString();
		            System.out.println("fileName : " + fileName);
		            System.out.println("inputStr : " + inputStr);
		            
		          //-- String to file---
		            File f = new File("newfile.xml");
		            Writer exportFile = null;
		            
		            exportFile = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");  
		            exportFile.write(inputStr);
		            exportFile.flush();
		            exportFile.close();
		            
		        } catch (Exception ex) {
		        	
		        	ex.printStackTrace();
	
		        	
		        } 
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -- Auto-generated method stub
		processRequest(request, response);
	}

}
