/**
 * 
 */
package th.co.scb.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author s51486
 *
 */
public class XPathReader {
	
	private	String		xmlString;
	private Document	xmlDocument;
	private	XPath		xPath;
	
	public XPathReader(String xmlString) {
		super();
		this.xmlString = xmlString;
		initObjects();
	}
	
	private void initObjects(){
		
		try{
			//xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
			xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
			//System.out.println("xml : " + xmlDocument.get);
			xPath = XPathFactory.newInstance().newXPath();

		}catch (IOException e) {
			// --: handle exception
			e.printStackTrace();
		}catch (SAXException se) {
			se.printStackTrace();
		}catch (ParserConfigurationException pe) {
			pe.printStackTrace();
		}
		
	}
	
	public Object read(String expression, QName returnType){
		
		try{
			
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(xmlDocument, returnType);
			
		}catch (XPathExpressionException e) {
			// --: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String getFirstChild(){
		
		return xmlDocument.getFirstChild().getNodeName();
		
	}
	
	public String getTagValue(String tag){
		
		String value = "";
		
		NodeList nodeList = xmlDocument.getElementsByTagName(tag);
		if(nodeList!=null && nodeList.getLength() > 0){
			Node aNode = nodeList.item(0);
			value = aNode.getTextContent();
		}
		
		return value;
		
	}
	
}
