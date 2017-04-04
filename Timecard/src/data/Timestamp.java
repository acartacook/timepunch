package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.AppendableObjectOutputStream;

public class Timestamp implements Serializable{
	private static final long serialVersionUID = 2680367223675773747L;
	private int employeeID;
	private Date in;
	private Date out;
	private String type;
	
	public Timestamp(){
		employeeID = 0;
		in = null;
		out = null;
		type = "REG";
	}
	
	public Timestamp(int employeeID){
		this.employeeID = employeeID;
		in = new Date();
		out = null;
		type = "REG";
	}
	
	public Timestamp(int employeeID, String type){
		this.employeeID = employeeID;
		in = new Date();
		out = null;
		this.type = type;
	}
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	public Date getIn() {
		return in;
	}
	public void setIn(Date in) {
		this.in = in;
	}
	
	public Date getOut() {
		return out;
	}
	public void setOut(Date out) {
		this.out = out;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
