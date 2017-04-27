package data;

public class Department{
	private String name;
	private int id;
	private int managerID;
	private String location;
	private String classy;

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
		this.classy = "Production";
	}

	public Department(String name, int id, int managerID, String location, String classy){
		this.name = name;
		this.id = id;
		this.managerID = managerID;
		this.location = location;
		this.classy = classy;
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
	public int getLocation() {
		return managerID;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getclassy() {
		return classy;
	}
	public void setclassy(String classy) {
		this.classy = classy;
	}
	public String toString(){
		return this.id + "|" + this.name + "|" + this.managerID  + "|"
		+ this.location + "|" + this.classy;
	}
}
