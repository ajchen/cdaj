package com.activ.cdaj.tx;

import com.activ.cdaj.ccda.CodeSystem;

public class Concept {

	public String code;
	public String displayName;
	public String unit;
	
	public Concept(String code, String display){
		this.code = code;
		this.displayName = display;
	}
	public Concept(String code, String display, String unit){
		this.code = code;
		this.displayName = display;
		this.unit = unit;
	}

	public String toString(){
		return 
		"<code code=\""+code+"\" displayName=\""+displayName+"\"/>";
	}
	
	public String toString(CodeSystem system){
		return 
		"<code code=\""+code+"\" displayName=\""+displayName+"\" codeSystem=\""+system.id+"\""+ 
		" codeSystemName=\""+system.name+"\"/>";
	}

}
