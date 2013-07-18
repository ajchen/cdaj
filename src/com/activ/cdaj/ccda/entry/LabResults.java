package com.activ.cdaj.ccda.entry;

import java.util.Properties;

import com.activ.cdaj.ccda.CodeSystem;
import com.activ.cdaj.ccda.Template;
import com.activ.cdaj.tx.Concept;
import com.activ.cdaj.tx.Vocabs;

public class LabResults extends Entry {
	


	public void toEntry(StringBuffer sb, Properties props){
		Template org_t = Template.map.get(Template.LABS_ORG);
		Template obs_t = Template.map.get(Template.LABS_OBS);
		//entry organizer
		sb.append(space(10)+"<entry>\n");
		sb.append(space(12)+props.getProperty("ccda.sec.org.line")+"\n");
		sb.append(space(14)+"<templateId root=\""+org_t.root+"\"/>\n");
		sb.append(space(14)+"<code nullFlavor=\"NA\"/>\n");
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
