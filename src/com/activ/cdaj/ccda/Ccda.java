package com.activ.cdaj.ccda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.activ.cdaj.ccda.entry.LabResults;
import com.activ.cdaj.ccda.entry.VitalSigns;
import com.activ.cdaj.util.Config;
import com.activ.cdaj.util.Utils;


public class Ccda {

	//YYYYMMDDHHMMSS.UUUU[+|-ZZzz]
  public static final SimpleDateFormat EFFECTIVE_TIME = new SimpleDateFormat("yyyyMMddHHmmssZ");
	
  private Properties props;  //properties in config

  //common
  public String version;
  public String realm;
	public String effectiveTime;
	public Code confidentialityCode;
	public String languageCode;

	//doc specific
  public String title;
	public Code cdCode; 
	public Template tmpl;

	//section data, each section having a list of entries 
	public List<VitalSigns> vitalsList;
	public List<LabResults> labsList;
	
	public Ccda(){
		props = Config.get().getCdajProperties();
		version = props.getProperty("ccda.version");
		realm = props.getProperty("ccda.realmCode");
		Date time = Calendar.getInstance().getTime();
		effectiveTime = Ccda.EFFECTIVE_TIME.format(time);
		confidentialityCode = ValueSets.bckCodeMap.get(ValueSets.BCK_N);
		languageCode = props.getProperty("ccda.langCode"); //"en-US";
	}
	
	public void addVitalSigns(VitalSigns v){
		if(vitalsList == null){
			vitalsList = new ArrayList<VitalSigns>();
		}
		vitalsList.add(v);
	}
	
	public void addLabResults(LabResults l){
		if(labsList == null){
			labsList = new ArrayList<LabResults>();
		}
		labsList.add(l);
	}
	
	public String toXml(){
		StringBuffer sb = new StringBuffer();
		sb.append(props.getProperty("ccda.xml.line")+"\n");
		sb.append(props.getProperty("ccda.ss.line")+"\n");
		sb.append(props.getProperty("ccda.cd.line")+"\n");
		
		//header
		sb.append(space(2)+"<realmCode code=\""+realm+"\"/>\n");
		sb.append(space(2)+props.getProperty("ccda.typeId.line")+"\n");
		sb.append(space(2)+props.getProperty("ccda.templateId.line")+"\n");
		//eg. for CCD
		//<templateId root="2.16.840.1.113883.10.20.22.1.1"/>
	  //<templateId root="2.16.840.1.113883.10.20.22.1.2"/>
		sb.append(space(2)+"<templateId root=\""+tmpl.root+"\"/>\n");
		//sb.append(space(2)+props.getProperty("ccda.id.line")+"\n"); //optional?
		sb.append(space(2)+cdCode+"\n");
		sb.append(space(2)+"<title>"+title+"</title>\n");

		//<confidentialityCode code="N" displayName="Normal" codeSystem="2.16.840.1.113883.5.25" codeSystemName="Confidentiality"/>
		sb.append(space(2)+confidentialityCode+"\n");
		sb.append(space(2)+"<confidentialityCode code=\""+confidentialityCode.code
				+"\" displayName=\""+confidentialityCode.displayName
				+"\" codeSystem=\""+confidentialityCode.system.id
				+"\" codeSystemName=\""+confidentialityCode.system.name+"\"/>\n");

		sb.append(space(2)+"<effectiveTime value=\""+effectiveTime+"\"/>\n");
		sb.append(space(2)+"<languageCode code=\""+languageCode+"\"/>\n");
		sb.append(space(2)+"<versionNumber value=\""+version+"\"/>\n");
		
		//recordTarget
		//sb.append(space(2)+props.getProperty("")+"\n");

		//author
		//sb.append(space(2)+props.getProperty("")+"\n");
		
		//custodian
		//sb.append(space(2)+props.getProperty("")+"\n");

		//body component
		sb.append(space(2)+"<component>\n");
		sb.append(space(4)+props.getProperty("ccda.body.line")+"\n");
		sb.append(space(6)+props.getProperty("ccda.body.templateId.line")+"\n");
		
		//vitals section
		if(vitalsList != null){
			toVitalsSection(sb, props);
		}

		if(labsList != null){
			toLabsSection(sb, props);
		}
		
		sb.append(space(4)+"</structuredBody>\n");
		sb.append(space(2)+"</component>\n");
		
		sb.append("</ClinicalDocument>\n");
		return sb.toString();
	}
	

	private void toVitalsSection(StringBuffer sb, Properties props){
		Template sec_t = Template.map.get(Template.VITALS_SEC);
		Code sec_code = Code.secCodeMap.get(Code.SEC_VITALS);
		sb.append(space(6)+"<component>\n");
		sb.append(space(8)+props.getProperty("ccda.sec.line")+"\n");
		sb.append(space(10)+"<templateId root=\""+sec_t.root+"\"/>\n");
		sb.append(space(10)+sec_code+"\n");
		sb.append(space(10)+"<title>"+sec_code.displayName+"</title>\n");
		
		//entries
		for(VitalSigns vitals : vitalsList){
			vitals.toEntry(sb, props);
		}

		sb.append(space(8)+"</section>\n");
		sb.append(space(6)+"</component>\n");

	}
	
	private void toLabsSection(StringBuffer sb, Properties props){
		Template sec_t = Template.map.get(Template.LABS_SEC);
		Code sec_code = Code.secCodeMap.get(Code.SEC_LABS);
		
		sb.append(space(6)+"<component>\n");
		sb.append(space(8)+props.getProperty("ccda.sec.line")+"\n");
		sb.append(space(10)+"<templateId root=\""+sec_t.root+"\"/>\n");
		sb.append(space(10)+sec_code+"\n");
		sb.append(space(10)+"<title>"+sec_code.displayName+"</title>\n");
		
		//entries
		for(LabResults labs : labsList){
			labs.toEntry(sb, props);			
		}

		sb.append(space(8)+"</section>\n");
		sb.append(space(6)+"</component>\n");

	}
	
	private String space(int n){
		return Utils.space(n);
	}
}
