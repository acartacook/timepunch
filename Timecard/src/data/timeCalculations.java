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
	private static DBTrial d = new DBTrial();
	
	public timeCalculations(){
		
	}
	
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
	//tHIS.setTime(THIS.getTime() + shift.getTime());
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
	
	public static String getTotal(int eId){
		ArrayList<Timestamps> diffs = d.getTimestamps(getSaturday(), eId);

		@SuppressWarnings("deprecation")
		long totalHours = -2209057200000L;
		long hoursWorked = -2209057200000L;
		long regHours = -2209057200000L;
		long OT = -2209057200000L;
		long CBworked = -2209057200000L;
		long CBnotWorked = -2209057200000L;
		long CBOT = -2209057200000L;
		long vacationHours = -2209057200000L;
		long holiday = -2209057200000L;
		long juryHours = -2209057200000L;
		long bereavementHours = -2209057200000L;
		
		
		long leftovers = 0;
		
		for(int i=0;i<diffs.size()-1;i++){
			Timestamps ts = diffs.get(i);
			Timestamp shift = new Timestamp(ts.getOut().getTime() - ts.getIn().getTime());
			
			//********************REGULAR PAY********************
			if (ts.getType().equals("REG")){
				totalHours+=shift.getTime();
				regHours+=shift.getTime();
				
				
				//Calculates overtime if over 40 regular hours
				if (regHours >= 144000L){
					leftovers = regHours - 144000L;
					regHours = regHours - leftovers;
					OT = leftovers + OT;
				}
				
			}
			//********************VACATION PAY********************
			else if (ts.getType().equals("VAC")){
				vacationHours +=shift.getTime();
			}
			//********************HOLIDAY PAY********************
			else if (ts.getType().equals("HOL")){
				holiday+=shift.getTime();
			}
			//********************JURY PAY********************
			else if (ts.getType().equals("JD")){
				juryHours+=shift.getTime();
			}
			//********************BEREAVEMENT PAY********************
			else if (ts.getType().equals("BR")){
				bereavementHours+=shift.getTime();
			}
			
			//********************CALLBACK PAY********************
			else if (ts.getType().equals("CB")){
			
				if (shift.getTime() < (3600 * 4)){							//if shift is less than 4 hours
					regHours+=shift.getTime();	//add the shift to the regular hours
					
					if (regHours >= 144000L){						//if reg hours are greater than 40
						leftovers = regHours - 144000L;
						regHours =(regHours - leftovers);	//this brings it back to 40 hours of regular time
						CBOT = CBOT + shift.getTime();
					}
					
					if (shift.getTime() < (4 * 3600)){							//if the CB shift is less than 4 hours
						CBworked+=shift.getTime();
						CBnotWorked= CBnotWorked + ((4*3600) - shift.getTime());	//CBnot worked is 4 hours minus worked hours
					}
					
					else {
						CBworked+=shift.getTime();		//otherwise just add the total shift	
					}
				}
			}

		}
		DateFormat f = new SimpleDateFormat("HH.mm");
		Double.parseDouble(f.format(new Date(hoursWorked)));
		return (Double.parseDouble(f.format(new Date(totalHours*1000))) + "|" +
				Double.parseDouble(f.format(new Date(hoursWorked*1000))) + "|" +
				Double.parseDouble(f.format(new Date(regHours*1000))) + "|" +
				Double.parseDouble(f.format(new Date(OT*1000))) + "|" +
				Double.parseDouble(f.format(new Date(CBworked*1000))) + "|" +
				Double.parseDouble(f.format(new Date(CBnotWorked*1000))) + "|" +
				Double.parseDouble(f.format(new Date(CBOT*1000))) + "|" +
				Double.parseDouble(f.format(new Date(vacationHours*1000))) + "|" +
				Double.parseDouble(f.format(new Date(holiday*1000))) + "|" +
				Double.parseDouble(f.format(new Date(juryHours*1000))) + "|") + 
				Double.parseDouble(f.format(new Date(totalHours*1000))) + "|";
	}
/*
	public long checkOvertime(Timestamp regHours, Timestamp shift1){
		if (regHours >= 144000L){
			long leftovers = regHours - 144000L;
			regHours.setHours(regHours.getTime() - leftovers);
			return leftovers;
		}
		else return 0;
	}
	*/
	public static void main (String[] args){
		Date d = new Date(getSaturday().getTime()*1000);
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.mmm");
        System.out.println(getTotal(1001));
        
	}
}
