package data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("serial")
public class Employee implements Serializable {

	private String first_name;
	private String last_name;
	private int id;
	private double pay;
	private int deptID;
	private double vacationHours;

	public Employee()
	{
		first_name = "";
		setLastName("");
		id = 0;
		pay = 0.0;
		deptID = 0;
		vacationHours = 0.0;
	}

	public Employee(String first_name,String last_name, int id, double pay,int deptID, double vacationHours)
	{
		this.first_name = first_name;
		this.id = id;
		this.pay = pay;
		this.deptID = deptID;
		this.vacationHours = vacationHours;
	}

	public void setFirstName(String first_name)
	{
		this.first_name = first_name;
	}

	public String getFirstName()
	{
		return this.first_name;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}

	public void setDeptID(int deptID)
	{
		this.deptID = deptID;
	}

	public int getDeptID()
	{
		return this.deptID;
	}

	public void setPay(double pay)
	{
		this.pay = pay;
	}

	public double pay()
	{
		return this.pay;
	}

	public void setVacationHours(double vacationHours)
	{
		this.vacationHours = vacationHours;
	}

	public  double getVacationHours()
	{
		return this.vacationHours;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String toString(){
		timeCalculations t = new timeCalculations();
		DBTrial trial = new DBTrial();
		String deptType = trial.getDepartment(deptID).getclassy();
		
        DateFormat f = new SimpleDateFormat("HH:mm");
		return this.id + "|" + this.first_name + "|" + this.last_name  + "|" + this.pay + "|" + this.vacationHours  + "|" 
        + this.deptID + "|" + deptType;
	}
}
