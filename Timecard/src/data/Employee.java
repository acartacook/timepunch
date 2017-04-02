package data;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Employee implements Serializable {

	private String name;
	private int id;
	private double pay;
	private int deptID;
	private double vacationHours;
	
	public Employee()
	{
		name = "";
		id = 0;
		pay = 0.0;
		deptID = 0;
		vacationHours = 0.0;
	}
	
	public Employee(String name, int id, double pay,int deptID, double vacationHours)
	{
		this.name = name;
		this.id = id;
		this.pay = pay;
		this.deptID = deptID;
		this.vacationHours = vacationHours;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
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
}
