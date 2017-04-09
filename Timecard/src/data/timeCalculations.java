package data;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import data.DBTrial;

public class timeCalculations {
	private DBTrial d = new DBTrial();
	
	public Timestamp totalHours(int eId){
		ArrayList<Timestamp> diffs = new ArrayList<Timestamp>();
		diffs = d.getTimediffs("", eId);
		Timestamp retval = new Timestamp(0L);
		for(int i=0;i<diffs.size()-1;i++){
			retval.setTime(retval.getTime() + diffs.get(i).getTime());
		}
		
		return retval;
	}
}
