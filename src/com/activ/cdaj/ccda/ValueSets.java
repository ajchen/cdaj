package com.activ.cdaj.ccda;

import java.util.HashMap;
import java.util.Map;


public class ValueSets {
	
	//bck code key
	public static final String BCK_N = "N";
	public static final String BCK_R = "R";
	public static final String BCK_V = "V";

	//HL7 BasicConfidentialityKind code => code
	//eg: <confidentialityCode code="N" displayName="Normal" codeSystem="2.16.840.1.113883.5.25" codeSystemName="Confidentiality"/>
	public static Map<String, Code> bckCodeMap = new HashMap<String, Code>();
	static {
		bckCodeMap.put(BCK_N, new Code(BCK_N, "Normal", CodeSystem.CONFIDENTIALITY));
		bckCodeMap.put(BCK_R, new Code(BCK_R, "Restricted", CodeSystem.CONFIDENTIALITY));
		bckCodeMap.put(BCK_V, new Code(BCK_V, "Very Restricted", CodeSystem.CONFIDENTIALITY));
	}

	//language code ??
	
	
	//ActStatus 2.16.840.1.113883.5.14
	//Description:This value set indicates the status of the results observation or organizer
	public static Map<String, Code> actStatusMap = new HashMap<String, Code>();
	static {
		actStatusMap.put("aborted", new Code("aborted", "aborted", CodeSystem.ACT_STATUS));
		actStatusMap.put("active", new Code("active", "active", CodeSystem.ACT_STATUS));
		actStatusMap.put("cancelled", new Code("cancelled", "cancelled", CodeSystem.ACT_STATUS));
		actStatusMap.put("completed", new Code("completed", "completed", CodeSystem.ACT_STATUS));
		actStatusMap.put("held", new Code("held", "held", CodeSystem.ACT_STATUS));
		actStatusMap.put("suspended", new Code("suspended", "suspended", CodeSystem.ACT_STATUS));
	}

	//Value Set: HITSP Vital Sign Result Type 2.16.840.1.113883.3.88.12.80.62 DYNAMIC
	//Code System(s): LOINC 2.16.840.1.113883.6.1
	//Description: This identifies the vital sign result type
	public static Map<String, Code> vitalMap = new HashMap<String, Code>();
	static {
		vitalMap.put("9279-1", new Code("9279-1", "Respiratory Rate", CodeSystem.LOINC));
		vitalMap.put("8867-4", new Code("8867-4", "Heart Rate", CodeSystem.LOINC));
		vitalMap.put("2710-2", new Code("2710-2", "O2 % BldC Oximetry", CodeSystem.LOINC));
		vitalMap.put("8480-6", new Code("8480-6", "BP Systolic", CodeSystem.LOINC));
		vitalMap.put("8462-4", new Code("8462-4", "BP Diastolic", CodeSystem.LOINC));
		vitalMap.put("8310-5", new Code("8310-5", "Body Temperature", CodeSystem.LOINC));
		vitalMap.put("8302-2", new Code("8302-2", "Height", CodeSystem.LOINC));
		vitalMap.put("8306-3", new Code("8306-3", "Height (Lying)", CodeSystem.LOINC));
		vitalMap.put("8287-5", new Code("8287-5", "Head Circumference", CodeSystem.LOINC));
		vitalMap.put("3141-9", new Code("3141-9", "Weight Measured", CodeSystem.LOINC));
		vitalMap.put("39156-5", new Code("39156-5", "BMI (Body Mass Index)", CodeSystem.LOINC));
		vitalMap.put("3140-1", new Code("3140-1", "BSA (Body Surface Area)", CodeSystem.LOINC));
	}

	
	
}
