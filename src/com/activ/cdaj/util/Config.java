package com.activ.cdaj.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Application Configuration. Singleton.
 * Default file structure:
 *   app/conf
 *   app/logs
 *   app/res
 *   app/src
 *   app/web
 *   
 * @author aj
 *
 */
public class Config {
	protected Logger LOG; 

	public static final String CDAJ = "cdaj";
	
	
	protected File appDir;     //application dir
  public String contextUrl;  //app context url
	
	//main app config params
  protected Properties appProps;  
  //module properties: module name => props
  protected Map<String, Properties> propsMap = new HashMap<String, Properties>();  

	private static Config _instance;
	
	private Config(File conf_file){
    //expecting config file path from jvm params: -Dbbpi.conf=
		//String conf = System.getProperty("bbpi.conf");
    //File conf_file = new File(conf);
    //expecting: app/conf/*.cf
    appDir = conf_file.getParentFile().getParentFile();

    //load main config
    loadAppConfig(conf_file);      
    
    //load modules config
    //todo: list modules names in app config
    File cdaj_cf = new File(appDir, "/conf/cdaj.cf");
    loadModConfig("cdaj", cdaj_cf);
    
    setKeyConfig();
	}

	/**load main application configuration file */
  private void loadAppConfig(File file){
    try{
      //load app properties
      appProps = new Properties();
      FileInputStream in = new FileInputStream(file);
      appProps.load(in);
      in.close();          
      
      //init log4j first
      String log4j = getValue("log4j.file");
      PropertyConfigurator.configure(log4j);
      LOG = Logger.getLogger(Config.class);

      LOG.info("app conf file: "+file.getCanonicalPath());
      LOG.info("log4j file: "+log4j);

    }catch(Exception e){
    	e.printStackTrace();
      LOG.error("failed to init app config: "+e, e);
    }
  }
  private void setKeyConfig(){
  	String host_url = getValue("host.url");
  	String context = getValue("webapp.context");
  	contextUrl = host_url+context;
  }
  
	public static void init(File file){
		if(_instance == null){
			_instance = new Config(file);
		}
	}
	
	public static Config get(){
		return _instance;
	}

	public File appDir(){
		return appDir;
	}
	
  /**get config value as String for the given field name */
	public String getValue(String name){
		return appProps.getProperty(name);
	}

	public int getIntValue(String name, int val){
		if(appProps.containsKey(name)){
			try{
				return Integer.parseInt(appProps.getProperty(name));
			}catch(Exception e){
				LOG.error("invalid number: "+name);
			}
		}
		return val;
	}
	public long getLongValue(String name, long val){
		if(appProps.containsKey(name)){
			try{
				return Long.parseLong(appProps.getProperty(name));
			}catch(Exception e){
				LOG.error("invalid number: "+name);
			}
		}
		return val;
	}

	public boolean getBooleanValue(String name, boolean val) {
    if(appProps.containsKey(name)){
      String valstr = appProps.getProperty(name);
      if("true".equalsIgnoreCase(valstr) || "yes".equalsIgnoreCase(valstr)){
        return true;
      }else if("false".equalsIgnoreCase(valstr) || "no".equalsIgnoreCase(valstr)){
      	return false;
      }      	
    }
    return val;
  }


	public Properties getCdajProperties(){
		return propsMap.get(CDAJ);
	}

	public Properties getModProperties(String mod){
		return propsMap.get(mod);
	}

	public String getValue(String mod, String name){
		return propsMap.get(mod).getProperty(name);
	}


	/** load additional configuration file, expecting conf/[mod].cf */
	public void loadModConfig(String mod){
    File cf = new File(appDir, "/conf/"+mod+".cf");
    loadModConfig(mod, cf);
	}
	
	/** load additional configuration file*/
	public void loadModConfig(String mod, File file){
		try{
	    FileInputStream in = new FileInputStream(file);
	    Properties p = new Properties();
	    p.load(in);
	    in.close();
	    propsMap.put(mod, p);
		}catch(Exception e){
			LOG.error(e,e);
		}
	}

}
