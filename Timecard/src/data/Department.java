package data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Department implements Serializable{
	private String name;
	private int id;
	private int managerID;
	
	public Department(){
		name = "";
		id = 0;
		managerID = 0;
	}
	
	public Department(String name, int id, int managerID){
		this.name = name;
		this.id = id;
		this.managerID = managerID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	
	
}
