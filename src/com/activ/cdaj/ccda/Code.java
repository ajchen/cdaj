package com.activ.cdaj.ccda;

import java.util.HashMap;
import java.util.Map;

/**ccda Code object */
public class Code {

	//clinical doc code key
	public static final String CD_CCD = "ccd";   //aka visit summary

	//HL7 clinical doc code key => code
	//eg: <code code="34133-9" displayName="Summarization of episode note" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	public static Map<String, Code> cdCodeMap = new HashMap<String, Code>();
	static {
		cdCodeMap.put(CD_CCD, new Code("34133-9", "Summarization of episode note", CodeSystem.LOINC));
	}

	//section code key
	public static final String SEC_VITALS = "vitals";
	public static final String SEC_LABS = "labs";
	
	//HL7 section code key => code
	//eg: <code code="8716-3" displayName="Vital signs" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	public static Map<String, Code> secCodeMap = new HashMap<String, Code>();
	static {
		secCodeMap.put(SEC_VITALS, new Code("8716-3", "Vital signs", CodeSystem.LOINC));
		secCodeMap.put(SEC_LABS, new Code("30954-2", "Relevant diagnostic tests and/or laboratory data", CodeSystem.LOINC));
	}
		
	public static final String ORG_VITALS = "vitals";

	//organizer code, examples 
	//<code code="46680005" displayName="Vital signs" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>  
	//<code code="57021-8" displayName="CBC W Auto Differential panel" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	public static Map<String, Code> orgCodeMap = new HashMap<String, Code>();
	static {
		orgCodeMap.put(ORG_VITALS, new Code("46680005", "Vital signs", CodeSystem.SNOMED_CT));
	}
	
	
	public String code;
	public String displayName;
	public CodeSystem system;
	public String templateId;
	
	public Code(String code, String display, CodeSystem system){
		this.code = code;
		this.displayName = display;
		this.system = system;
	}
	
	public Code(String code, String display, CodeSystem system, String root){
		this.code = code;
		this.displayName = display;
		this.system = system;
		this.templateId = root;
	}

	//example:
	//<code code="34133-9" displayName="Summarization of episode note" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	public String toString(){
		return "<code code=\""+code
			+"\" displayName=\""+displayName
			+"\" codeSystem=\""+system.id
			+"\" codeSystemName=\""+system.name +"\"/>";
	}
}
