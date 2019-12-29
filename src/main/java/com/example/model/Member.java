package com.example.model;

public class Member {
	// name id nic cell picture 
	private String Name;
	private String id;
	private double nic;
	private double cell; 
	
	
	
	public Member(String name, String id, double nic, double cell) {
		super();
		Name = name;
		this.id = id;
		this.nic = nic;
		this.cell = cell;
	}
	public Member(String name, String id) {
		super();
		Name = name;
		this.id = id;
	}
	public double getNic() {
		return nic;
	}
	public void setNic(double nic) {
		this.nic = nic;
	}
	public double getCell() {
		return cell;
	}
	public void setCell(double cell) {
		this.cell = cell;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//for debug only
	/*@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Id : " + id + "Name : "+ Name;
	}*/
	
	
	

}
