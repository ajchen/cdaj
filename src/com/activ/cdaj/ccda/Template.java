package com.activ.cdaj.ccda;

import java.util.HashMap;
import java.util.Map;

public class Template {

	//doc keys
	public static final String CCD = "ccd";   //aka visit summary
	
	//sec keys
	public static final String VITALS_SEC = "vitals-sec";
	public static final String VITALS_ORG = "vitals-org";
	public static final String VITALS_OBS = "vitals-obs";	
	public static final String LABS_SEC = "labs-sec";
	public static final String LABS_ORG = "labs-org";
	public static final String LABS_OBS = "labs-obs";

	//hl7 ccda template id map: key=> code
	public static Map<String,Template> map = new HashMap<String, Template>();
	static{
		map.put(CCD, new Template("2.16.840.1.113883.10.20.22.1.2"));

		map.put(VITALS_SEC, new Template("2.16.840.1.113883.10.20.22.2.4.1"));
		map.put(VITALS_ORG, new Template("2.16.840.1.113883.10.20.22.4.26"));
		map.put(VITALS_OBS, new Template("2.16.840.1.113883.10.20.22.4.27"));
		map.put(LABS_SEC, new Template("2.16.840.1.113883.10.20.22.2.3.1"));
		map.put(LABS_ORG, new Template("2.16.840.1.113883.10.20.22.4.1"));
		map.put(LABS_OBS, new Template("2.16.840.1.113883.10.20.22.4.2"));
	}

	
	public String root;

	public Template(String root){
		this.root = root;
	}
	
	
}
