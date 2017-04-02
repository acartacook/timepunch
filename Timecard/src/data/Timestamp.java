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
	
	public int findTimestamp(int eID) {
		// TODO Auto-generated method stub
		ObjectInputStream objectinputstream = null;
		int at = 0;
		try {
			FileInputStream streamIn = new FileInputStream("src/timestamp.ser");
		    objectinputstream = new ObjectInputStream(streamIn);
		    Object o = new Object();
		    while((o = objectinputstream.readObject()) != null){
		    	Timestamp t = new Timestamp();
		    	t = (Timestamp) o;
		    	if(t.getOut() == null && t.getEmployeeID() == eID){
		    		return at;
		    	}
		    	at++;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(objectinputstream != null){
		        try {
					objectinputstream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		}
		return -1;
	}
	
	public String updateTimestamp(int eID, Date out ,Date in){
		int loc = findTimestamp(eID);
		int at = 0;
		String clientMsg = "Error";
		ObjectInputStream ois = null;
		Timestamp t = null;
		if(at != -1){
			try {
				FileInputStream streamIn = new FileInputStream("src/timestamp.ser");
			    ois = new ObjectInputStream(streamIn);
			    Object o = new Object();
			    while(at != loc){
			    	o = ((ObjectInput) ois).readObject();
			    	t = (Timestamp) o;
			    }
			    //take t out of timestamp.ser
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    if(ois != null){
			        try {
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    } 
			}
		}
		AppendableObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try{
			Timestamp toAdd = new Timestamp(eID);
			if(t != null){
				toAdd = t;
			}
			if(in != null){
				toAdd.setIn(in);
				clientMsg = "Clocked in";
			}
			if(out != null){
				toAdd.setOut(out);
				clientMsg = "";
			}
		    fout = new FileOutputStream("src/timestamp.ser", true);
		    oos = new AppendableObjectOutputStream(fout);
		    
		    oos.writeObject(toAdd);
		    
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
		    if(oos != null){
		        try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		}
		return clientMsg;
	}
	
	public void printAllTimestamps(){
		ObjectInputStream ois = null;
		Timestamp t = null;
		try {
			FileInputStream streamIn = new FileInputStream("src/timestamp.ser");
		    ois = new ObjectInputStream(streamIn);
		    Object o = new Object();
		    while(true){
		    	o = ois.readObject();
		    	t = (Timestamp) o;
		    	int id = t.getEmployeeID();
		    	Date in = t.getIn();
		    	Date out = t.getOut();
		    	String type = t.getType();
		    	DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
		    	
		    	System.out.println(id + " | " + df.format(in) + " | " + df.format(out) + " | " + type);
		    }
		    //take t out of timestamp.ser
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(ois != null){
		        try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		}
	}

}
