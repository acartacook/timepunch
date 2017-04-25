package data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Department implements Serializable{
	private String name;
	private int id;
	private int managerID;
	private String location;
	private String class;

	public Department(){
		name = "";
		id = 0;
		managerID = 0;
		location = "Valdosta";
	}

	public Department(String name, int id, int managerID){
		this.name = name;
		this.id = id;
		this.managerID = managerID;
		this.location = "Valdosta";
		this.class = "Production";
	}

	public Department(String name, int id, int managerID, String location, String class){
		this.name = name;
		this.id = id;
		this.managerID = managerID;
		this.location = location;
		this.class = class;
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
	public String getLocation() {
		return managerID;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getClass() {
		return class;
	}
	public void setClass(String class) {
		this.class = class;
	}
	public String toString(){
		return this.id + "|" + this.name + "|" + this.managerID  + "|"
		+ this.location + "|" + this.class;
	}
}
