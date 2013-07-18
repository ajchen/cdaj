package com.activ.cdaj.ccda.entry;

import java.util.Properties;

import com.activ.cdaj.ccda.Code;
import com.activ.cdaj.ccda.CodeSystem;
import com.activ.cdaj.ccda.Template;
import com.activ.cdaj.tx.Concept;
import com.activ.cdaj.tx.Vocabs;
import com.activ.cdaj.util.Utils;

public class VitalSigns extends Entry {
/*
	//key => value
	public Map<String,Obs> obsMap = new LinkedHashMap<String,Obs>();

	public void setData(Map<String, KV> vitals){
		for(String key : vitals.keySet()){
			KV kv = vitals.get(key);
			String v = String.valueOf(kv.value);
			Obs obs = new Obs(kv.key, kv.name, v, kv.unit, Ccda.EFFECTIVE_TIME.format(kv.time));
			obsMap.put(key, obs);
		}
	}
*/
	

	public void toEntry(StringBuffer sb, Properties props){
		Template org_t = Template.map.get(Template.VITALS_ORG);
		Template obs_t = Template.map.get(Template.VITALS_OBS);
		Code org_code = Code.orgCodeMap.get(Code.ORG_VITALS);

		//entry organizer
		sb.append(space(10)+"<entry>\n");
		sb.append(space(12)+props.getProperty("ccda.sec.org.line")+"\n");
		sb.append(space(14)+"<templateId root=\""+org_t.root+"\"/>\n");
		//<code code="46680005" displayName="Vital signs" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
		sb.append(space(14)+org_code+"\n");
		sb.append(space(14)+"<statusCode code=\"completed\"/>\n");

		//obs components
		for(String key : obsMap.keySet()){					
			Concept concept = null;
			if(Vocabs.loincCodeMap.containsKey(key)){
				concept = Vocabs.loincCodeMap.get(key);
			}
			if(concept == null){
				continue;
			}			
			
			//obs
			Obs obs = obsMap.get(key);

			sb.append(space(14)+"<component>\n");
			sb.append(space(16)+props.getProperty("ccda.sec.obs.line")+"\n");

			sb.append(space(18)+"<templateId root=\""+obs_t.root+"\"/>\n");
			sb.append(space(18)+"<id nullFlavor=\"NA\"/>\n");
			sb.append(space(18)+concept.toString(CodeSystem.LOINC)+"\n");
			sb.append(space(18)+"<statusCode code=\"completed\"/>\n");
			sb.append(space(18)+"<effectiveTime value=\""+obs.time+"\"/>\n");
			sb.append(space(18)+"<value xsi:type=\"PQ\" value=\""+obs.value+"\" unit=\""+concept.unit+"\"/>\n");

			sb.append(space(16)+"</observation>\n");
			sb.append(space(14)+"</component>\n");
		}

		sb.append(space(12)+"</organizer>\n");
		sb.append(space(10)+"</entry>\n");

	}


}
