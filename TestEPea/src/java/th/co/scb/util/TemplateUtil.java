package th.co.scb.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public class TemplateUtil {
	
	private	String 	templateFile;
	private VelocityContext context;
	private VelocityEngine ve;
	
	public TemplateUtil(String templateFile, ServletContext servletContext){
		
		this.templateFile = templateFile;
		context = new VelocityContext();
		
		ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "webapp");
        ve.addProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
        ve.addProperty("webapp.resource.loader.path", "/WEB-INF/XMLTemplate/");
        ve.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
   
        ve.init();
		
	}
	
	public void setContext(String name, Object value){
		context.put(name, value);
	}
	
	public String getXML(){
		
		StringWriter writer = null;
		String xmlStr = "";
		try{
			
			Template t = ve.getTemplate(templateFile);
			writer = new StringWriter();

			t.merge(context, writer);
			//System.out.println(writer.toString());
			
			xmlStr = writer.toString();
			
		}catch (Exception e) {
			// --: handle exception
			e.printStackTrace();
		}finally{
			if ( writer != null){
                try{
                    writer.close();
                }catch( Exception ee ){
                    ee.printStackTrace();
                }
            }
		}
        
		
		return xmlStr;
	}
	
	public void exportXMLFile(String pathOutputFile, String encoding){
		Writer exportFile = null;
		
		try{
			
	        exportFile = new OutputStreamWriter(new FileOutputStream(pathOutputFile),encoding);  
	        
	        String xmlStr = getXML();
	        
	        exportFile.write(xmlStr); 
	        
        }catch(Exception ex){
            //errMsg += ex.getMessage();
            ex.printStackTrace();
        }finally{
			if ( exportFile != null){
                try{
                	exportFile.flush();
                	exportFile.close();
                }catch( Exception ee ){
                    ee.printStackTrace();
                }
            }
		}
	}
	
	public void exportXMLFile(String pathOutputFile){
		
		exportXMLFile(pathOutputFile, "UTF-8");
	}
	
}
