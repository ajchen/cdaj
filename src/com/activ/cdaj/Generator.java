package com.activ.cdaj;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.activ.cdaj.ccda.Ccda;
import com.activ.cdaj.data.Record;
import com.activ.cdaj.util.Config;
import com.activ.cdaj.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Generator {
	private static final Logger LOG = Logger.getLogger(Generator.class);
	
	//private Properties props;  //ccda properties
	
	public Generator(){
		//this.props = Config.get().getCdajProperties();
	}
	public Generator(Properties props){
		//this.props = props;
	}

	public String generateRecords(List<Record> records, String type){
		//json only for now
		return generateRecordsJson(records);
	}
	
	public String generateRecordsJson(List<Record> records){
		String json = "";
		try{
			ObjectMapper obj_mapper = new ObjectMapper();    
			ObjectNode root = obj_mapper.createObjectNode();
			root.put("desc", "latest records");
			ArrayNode an = root.putArray("records");
			for(Record r:records){
				ObjectNode on = an.addObject();
				on.put("id", r.id);
				on.put("name", r.name);
				on.put("type", r.type);
				on.put("date", r.date.getTime());
			}	   
			json = obj_mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		}catch(Exception e){
			LOG.error(e,e);
		}
		return json;
	}
	
	public String generateCcda(Ccda ccda, String type){
		//xml only for now
		return ccda.toXml();
	}
	/*
	public String generateCcdaXml(Ccda ccda){
		StringBuffer sb = new StringBuffer();
		sb.append(props.getProperty("ccda.xml.line")+"\n");
		sb.append(props.getProperty("ccda.ss.line")+"\n");
		sb.append(props.getProperty("ccda.cd.line")+"\n");
		
		//header
		sb.append(space(2)+"<realmCode code=\""+props.getProperty("ccda.realmCode")+"\"/>\n");
		sb.append(space(2)+props.getProperty("ccda.typeId.line")+"\n");
		sb.append(space(2)+props.getProperty("ccda.templateId.line")+"\n");
		sb.append(space(2)+props.getProperty("ccda.id.line")+"\n");
		sb.append(space(2)+ccda.cdCode+"\n");
		sb.append(space(2)+"<title>"+ccda.title+"</title>\n");
		sb.append(space(2)+"<effectiveTime value=\""+ccda.effectiveTime+"\"/>\n");
		sb.append(space(2)+ccda.confidentialityCode+"\n");
		sb.append(space(2)+"<languageCode code=\""+ccda.languageCode+"\"/>\n");

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
		if(ccda.vitalsList != null){
			//ccda.vitalsList.vitalSection(sb, ccda);
		}
		
		sb.append(space(4)+"</structuredBody>\n");
		sb.append(space(2)+"</component>\n");
		
		sb.append("</ClinicalDocument>\n");
		return sb.toString();
	}
	
*/

	
	private String space(int n){
		return Utils.space(n);
	}
}
