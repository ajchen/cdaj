package com.activ.cdaj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Utils {
	private static final Logger LOG = Logger.getLogger(Utils.class);

	public static String ENCODING = "UTF-8";

	public static final class ContentType {
    public static final String URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String JSON = "application/json";
    public static final String XML = "application/xml";
  }
	
	public static final class DataScope {
		public static final String SUMMARY = "summary";
		public static final String SEARCH = "search";
		public static final String LIST = "list";
	}
	

  public static boolean isEmpty(String value) {
    return value == null || "".equals(value);
  }
	
	public static String space(int n){
		String s = "";
		for(int i=0; i<n; i++){
			s +=" ";
		}
		return s;
	}
	
  public static void saveStringToFile(String text, File file){
    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      writer.write(text);      
      writer.close();
    }catch(Exception e){
      LOG.error(e, e); 
    }
  }

  
  /**print server and request info*/
  public static void printServletInfo(HttpServletRequest req){
  	if(req != null){
    	StringBuffer sb = new StringBuffer();
      sb.append("-----request info---\n");
      sb.append("url=" + req.getRequestURL().toString()+"\n");
      sb.append("uri=" + req.getRequestURI()+"\n");
      sb.append("protocol=" + req.getProtocol()+"\n");
      sb.append("server=" + req.getServerName()+"\n");
      sb.append("port=" + req.getServerPort()+"\n");
      sb.append("local name="+ req.getLocalName()+"\n");
      sb.append("context path="+ req.getContextPath()+"\n");  //web app context
      sb.append("servelt path="+ req.getServletPath()+"\n");
      sb.append("pathinfo=" + req.getPathInfo()+"\n");
      sb.append("query="+ req.getQueryString()+"\n");
      sb.append("content type=" + req.getContentType()+"\n");
      sb.append("encoding=" + req.getCharacterEncoding()+"\n");
      sb.append("remote addr="+ req.getRemoteAddr()+"\n");
      sb.append("remote host="+ req.getRemoteHost()+"\n");
    
      Enumeration headers = req.getHeaderNames();
      while(headers.hasMoreElements()){
      	String n = (String)headers.nextElement();
      	sb.append("header: "+n+"="+(String)req.getHeader(n)+"\n");
      }
      
      Enumeration params = req.getParameterNames();
      while(params.hasMoreElements()){
        String n = (String)params.nextElement();
        sb.append("param: "+n+"="+(String)req.getParameter(n)+"\n");
      }

      Enumeration attr_names = req.getAttributeNames();
      while(attr_names.hasMoreElements()){
      	String n = (String)attr_names.nextElement();
      	sb.append("attr: "+n+"="+(String)req.getAttribute(n)+"\n");
      }
      LOG.info(sb.toString());  
    }
  }   
}
