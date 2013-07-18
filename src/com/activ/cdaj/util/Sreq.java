package com.activ.cdaj.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Expanded servlet request.
 * 
 *  
 */
public class Sreq {
	private static final Logger LOG = Logger.getLogger(Sreq.class);
  
	public static final String DEFAULT_ENCODING = "UTF-8";
  public static final String CONTENT_TYPE_JSON = "application/json";
  
  /**servlet request */
	public HttpServletRequest req;
	
	/**request url */
	public String url;
	/**servlet path */
	public String path;
	/**path info */
	public String info;
	/**request param: act */
	public String act;
	
	/**
	 * Expanded servlet request.
	 * @param req  servlet request
	 */
	public Sreq(HttpServletRequest req){ 
		this.req = req;
		try{
	    url = req.getRequestURL().toString(); 
	    String queryString = req.getQueryString(); 
	    if (queryString != null) { 
	      url += "?"+queryString; 
	    } 
	    path = req.getServletPath();
	    if(path != null){
	    	path = path.toLowerCase();
	    }
	    info = req.getPathInfo();
	    if(info != null){
	    	info = info.toLowerCase();
	    }
	    act = req.getParameter("act");
	    if(act != null){ 
	      act = act.trim().toLowerCase();
	    }

	    
		}catch(Exception e){
			LOG.error(e);
		}
	}
	
  /**
   * Read content from request Input Stream and save it as a String.
   * @return String that was read from the stream
   */
	public String getStreamAsString(){
		try{
			String enc = req.getCharacterEncoding();
			if(enc == null){
				return getStreamAsString(req.getInputStream());
			}else{
				return getStreamAsString(req.getInputStream(), enc);				
			}
		}catch(Exception e){
			LOG.error(e,e);
		}
		return "";
	}

  /**
   * Read content from the given Input Stream and save it as a String.
   * @param is InputStream to be read
   * @return String that was read from the stream
   */
  public static String getStreamAsString(InputStream is) {
  	return getStreamAsString(is, DEFAULT_ENCODING);
  }

  /**
   * Get the entity content as a String, using the provided default character set
   * if none is found in the entity.
   * If defaultCharset is null, the default "UTF-8" is used.
   *
   * @param is      input stream to be saved as string
   * @param charset character set to be applied if none found in the entity
   * @return the entity content as a String
   */
  public static String getStreamAsString(InputStream is, String charset) {
    StringBuilder sb = new StringBuilder();
  	if(is != null) {
    	if (charset == null) {
    		charset = DEFAULT_ENCODING;
      }
    	Reader reader = null;
    	try{
      	reader = new InputStreamReader(is, charset);
        int l;
        char[] tmp = new char[4096];
        while ((l = reader.read(tmp)) != -1) {
        	sb.append(tmp, 0, l);
        }
    	}catch(Exception e){
    		LOG.error(e);
    	} finally {
    		try{reader.close();}catch(Exception e){}
    	}
    }

  	return sb.toString();
  }

}
