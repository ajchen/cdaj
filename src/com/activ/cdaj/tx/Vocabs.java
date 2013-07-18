package com.activ.cdaj.tx;

import java.util.HashMap;
import java.util.Map;


public class Vocabs {

	//////LOINC//////// 
	
	//code => name
	//examples:
	//<code code="8462-4" displayName="BP Diastolic" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	//<code code="8480-6" displayName="BP Systolic" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	//<code code="8302-2" displayName="Body Height (Measured)" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
  //<code code="3141-9" displayName="Body Weight (Measured)" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
	/*
	Cholesterol	LOINC	2093-3	mg/dL
	HDL	LOINC	2085-9	mg/dL
	LDL	LOINC	2089-1	mg/dL
	Triglyceride	LOINC	2571-8	mg/dL
	Hemoglobin A1c	LOINC	4548-4	%
  */
	public static Map<String,Concept> loincCodeMap = new HashMap<String,Concept>();
	static {
		loincCodeMap.put("8462-4", new Concept("8462-4", "BP Diastolic", "mm[Hg]"));
		loincCodeMap.put("8480-6", new Concept("8480-6", "BP Systolic", "mm[Hg]"));
		loincCodeMap.put("8302-2", new Concept("8302-2", "Body Height (Measured)", "[in_us]"));
		loincCodeMap.put("3141-9", new Concept("3141-9", "Body Weight (Measured)", "[lb_av]"));
		loincCodeMap.put("2093-3", new Concept("2093-3", "Cholesterol", "mg/dL"));
		loincCodeMap.put("2085-9", new Concept("2085-9", "HDL", "mg/dL"));
		loincCodeMap.put("2089-1", new Concept("2089-1", "LDL", "mg/dL"));
		loincCodeMap.put("2571-8", new Concept("2571-8", "Triglyceride", "mg/dL"));
		loincCodeMap.put("4548-4", new Concept("4548-4", "Hemoglobin A1c", "%"));
		//loincCodeMap.put("", new Concept("", ""));
	}
	
	
	
}
