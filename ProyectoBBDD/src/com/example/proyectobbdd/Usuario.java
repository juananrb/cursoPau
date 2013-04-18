package com.example.proyectobbdd;

public class Usuario {
	private String name;
	private String number;
	private String skypeid;
	private String address;
	private int id;

	public Usuario(String name, String number, String skypeid, String address) {
		this.name = name;
		this.number = number;
		this.skypeid = skypeid;
		this.address = address;		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSkypeid() {
		return skypeid;
	}

	public void setSkypeid(String skypeid) {
		this.skypeid = skypeid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
}
