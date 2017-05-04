package data;

import java.sql.Timestamp;

public class Timestamps{
	private int id;
	private int employeeID;
	private Timestamp in;
	private Timestamp out;
	private String type;

	public Timestamps(){
		id = -1;
		employeeID = 0;
		in = null;
		out = null;
		type = "REG";
	}

	public Timestamps(int employeeID){
		id = -1;
		this.employeeID = employeeID;
		in = new Timestamp(0L);
		out = null;
		type = "REG";
	}

	public Timestamps(int employeeID, String type){
		id = -1;
		this.employeeID = employeeID;
		in = new Timestamp(0L);
		out = null;
		this.type = type;
	}

	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public Timestamp getIn() {
		return in;
	}
	public void setIn(Timestamp in) {
		this.in = in;
	}

	public Timestamp getOut() {
		return out;
	}
	public void setOut(Timestamp out) {
		this.out = out;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getID() {
		return employeeID;
	}
	public void setID(int id) {
		this.id = id;
	}

	//timestamp in and out will need to be formatted
	public String toString(){
		String out =  "";
		if(this.out != null){
			out = this.out + "";
		}
		
		return this.id + "|" + this.in + "|" + out  + "|"
		+ this.type + "|" + this.employeeID;
	}
}
