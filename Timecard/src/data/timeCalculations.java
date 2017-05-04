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
		
		Timestamp totalHours = new Timestamp(0L);
		Timestamp hoursWorked = new Timestamp(0L);
		Timestamp regHours = new Timestamp(0L);
		Timestamp OT = new Timestamp(0L);
		Timestamp CBworked = new Timestamp(0L);
		Timestamp CBnotWorked = new Timestamp(0L);
		Timestamp CBOT = new Timestamp(0L);
		Timestamp vacationHours = new Timestamp(0L);
		Timestamp holiday = new Timestamp(0L);
		Timestamp juryHours = new Timestamp(0L);
		Timestamp bereavementHours = new Timestamp(0L);
		
		
		long leftovers = 0;
		
		for(int i=0;i<diffs.size()-1;i++){
			Timestamps ts = diffs.get(i);
			Timestamp shift = new Timestamp(ts.getOut().getTime() - ts.getIn().getTime());
			
			//********************REGULAR PAY********************
			if (ts.getType().equals("REG")){
				totalHours.setTime( totalHours.getTime() +shift.getTime());
				regHours.setTime( regHours.getTime() +shift.getTime());
				
				
				//Calculates overtime if over 40 regular hours
				if (regHours.getTime() >= 144000L){
					leftovers = regHours.getTime() - 144000L;
					regHours.setTime(regHours.getTime() - leftovers);
					OT.setTime(leftovers + OT.getTime());
				}
				
			}
			//********************VACATION PAY********************
			else if (ts.getType().equals("VAC")){
				vacationHours.setTime(vacationHours.getTime() + shift.getTime());
			}
			//********************HOLIDAY PAY********************
			else if (ts.getType().equals("HOL")){
				holiday.setTime(holiday.getTime() + shift.getTime());
			}
			//********************JURY PAY********************
			else if (ts.getType().equals("JD")){
				juryHours.setTime(juryHours.getTime() + shift.getTime());
			}
			//********************BEREAVEMENT PAY********************
			else if (ts.getType().equals("BR")){
				bereavementHours.setTime(bereavementHours.getTime() + shift.getTime());
			}
			
			//********************CALLBACK PAY********************
			else if (ts.getType().equals("CB")){
			
				if (shift.getTime() < (3600 * 4)){							//if shift is less than 4 hours
					regHours.setTime(regHours.getTime() + shift.getTime());	//add the shift to the regular hours
					
					if (regHours.getTime() >= 144000L){						//if reg hours are greater than 40
						leftovers = regHours.getTime() - 144000L;
						regHours.setTime(regHours.getTime() - leftovers);	//this brings it back to 40 hours of regular time
						CBOT.setTime(CBOT.getTime() + shift.getTime());
					}
					
					if (shift.getTime() < (4 * 3600)){							//if the CB shift is less than 4 hours
						CBworked.setTime(CBworked.getTime() + shift.getTime());
						CBnotWorked.setTime(CBnotWorked.getTime() + ((4*3600) - shift.getTime()));	//CBnot worked is 4 hours minus worked hours
					}
					
					else {
						CBworked.setTime(CBworked.getTime() + shift.getTime());			//otherwise just add the total shift	
					}
				}
			}

		}
		DateFormat f = new SimpleDateFormat("HH.mm");
		Double.parseDouble(f.format(new Date(hoursWorked.getTime())));
		return (Double.parseDouble(f.format(new Date(totalHours.getTime()))) + "|\t\t\t" +
				Double.parseDouble(f.format(new Date(hoursWorked.getTime()))) + "|\t\t\t" +
				Double.parseDouble(f.format(new Date(regHours.getTime()))) + "|\t\t" +
				Double.parseDouble(f.format(new Date(OT.getTime()))) + "|\t\t" +
				Double.parseDouble(f.format(new Date(CBworked.getTime()))) + "|\t\t\t" +
				Double.parseDouble(f.format(new Date(CBnotWorked.getTime()))) + "|\t\t\t\t" +
				Double.parseDouble(f.format(new Date(CBOT.getTime()))) + "|\t\t\t" +
				Double.parseDouble(f.format(new Date(vacationHours.getTime()))) + "|\t\t" +
				Double.parseDouble(f.format(new Date(holiday.getTime()))) + "|\t\t" +
				Double.parseDouble(f.format(new Date(juryHours.getTime()))) + "|\t\t") + 
				Double.parseDouble(f.format(new Date(totalHours.getTime()))) + "|\t\t";
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
