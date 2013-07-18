package com.activ.cdaj.ccda;

import java.util.Calendar;
import java.util.Date;


import com.activ.cdaj.ccda.entry.LabResults;
import com.activ.cdaj.ccda.entry.VitalSigns;
import com.activ.cdaj.data.DataMgr;
import com.activ.cdaj.data.Record;
import com.activ.cdaj.util.Config;

public class CcdaBuilder {

	
	public static Ccda createSummaryCcda(String patient_id, String record_id){
		Ccda ccda = new Ccda();
		ccda.tmpl = Template.map.get(Template.CCD);
		ccda.cdCode = Code.cdCodeMap.get(Code.CD_CCD);
		
		Record record = DataMgr.get().getRecord(record_id);
		if(record == null){
			ccda.title = "Visit Summary";
			//no specific record, get the latest summary 
			VitalSigns vs = new VitalSigns();
			vs.setData(DataMgr.get().getVitalSigns(patient_id));
			ccda.addVitalSigns(vs);
		}else{
			if(record.isType(Record.TYPE_VISIT)){
				ccda.title = "Visit Summary";
				VitalSigns vs = new VitalSigns();
				vs.setData(record.data);
				ccda.addVitalSigns(vs);
			}else if(record.isType(Record.TYPE_LAB)){
				ccda.title = "LAB Summary";
				LabResults lr = new LabResults();
				lr.setData(record.data);
				ccda.addLabResults(lr);
			}else{ 
				//??
			}
			
		}
		return ccda;
	}
	

}
