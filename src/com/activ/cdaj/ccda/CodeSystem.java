package com.activ.cdaj.ccda;

public class CodeSystem {

	//common code system
	public static final CodeSystem LOINC = new CodeSystem("LOINC", "2.16.840.1.113883.6.1");
	public static final CodeSystem SNOMED_CT = new CodeSystem("SNOMED CT", "2.16.840.1.113883.6.96");
	
	//hl7 specific
	public static final CodeSystem CONFIDENTIALITY = new CodeSystem("Confidentiality", "2.16.840.1.113883.5.25");
	public static final CodeSystem LANGUAGE = new CodeSystem("Internet Society Language", "2.16.840.1.113883.1.11.11526");	
	public static final CodeSystem ACT_STATUS = new CodeSystem("ActStatus", "2.16.840.1.113883.5.14");
	
	public String id;
	public String name;
	public CodeSystem(String name, String id){
		this.id = id;
		this.name = name;
	}
	

}
