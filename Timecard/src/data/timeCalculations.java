package data;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import data.DBTrial;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previous;

public class timeCalculations {
	private DBTrial d = new DBTrial();
	
	public Timestamp totalHours(int eId){
		ArrayList<Timestamp> diffs = new ArrayList<Timestamp>();
		diffs = d.getTimediffs("", eId,null);
		Timestamp retval = new Timestamp(0L);
		for(int i=0;i<diffs.size()-1;i++){
			retval.setTime(retval.getTime() + diffs.get(i).getTime());
		}
		
		return retval;
	}
	
	public Timestamp totalHours(String type, int eId){
		ArrayList<Timestamp> diffs = new ArrayList<Timestamp>();
		diffs = d.getTimediffs(type, eId ,null);
		Timestamp retval = new Timestamp(0L);
		for(int i=0;i<diffs.size()-1;i++){
			retval.setTime(retval.getTime() + diffs.get(i).getTime());
		}
		
		return retval;
	}
	
	public Timestamp totalHours(String type, int eId, Timestamp t){
		ArrayList<Timestamp> diffs = new ArrayList<Timestamp>();
		diffs = d.getTimediffs(type, eId ,t);
		Timestamp retval = new Timestamp(0L);
		for(int i=0;i<diffs.size()-1;i++){
			retval.setTime(retval.getTime() + diffs.get(i).getTime());
		}
		 
		return retval;
	}
	
	public Timestamp totalHoursSat(String type, int eId){
		ArrayList<Timestamp> diffs = new ArrayList<Timestamp>();
		diffs = d.getTimediffs(type, eId ,getSaturday());
		Timestamp retval = new Timestamp(0L);
		for(int i=0;i<diffs.size()-1;i++){
			retval.setTime(retval.getTime() + diffs.get(i).getTime());
		}
		
		return retval;
	}
	public static Timestamp getSaturday(){
		LocalDate now = LocalDate.now();
		LocalDate temp = null;
		Timestamp sat = new Timestamp(0L);

		temp = now.with(previous(SATURDAY));
		ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
		long epoch = temp.atStartOfDay(zoneId).toEpochSecond();
		sat = new Timestamp(epoch);
		
		sat.setTime(sat.getTime() + 37800L);
		return sat;
	}
	
	public static void main (String[] args){
		Date d = new Date(getSaturday().getTime()*1000);
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.mmm");
        System.out.println(f.format(d));
	}
}
