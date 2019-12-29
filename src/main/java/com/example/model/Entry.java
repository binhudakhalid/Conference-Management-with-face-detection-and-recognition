package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "entry")
public class Entry {


	public Entry(){
		
	}


		


		/*public Entry(int id, String name, String date, String nic) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.nic = nic;
	}*/


	public Entry(String name, String date) {
		super();
		this.name = name;
		this.date = date;
	}





		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "user_id")
		private int id;
		
		
		@Column(name = "fullname")
		@NotEmpty(message = "*Please provide your name")
		private String name;
		
		
		@Column(name = "date")
		@NotEmpty
		private String date;
		
		
		

		@Column(name = "nic",nullable = true)
		private String nic;


		


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getDate() {
			return date;
		}


		public void setDate(String date) {
			this.date = date;
		}


		public String getNic() {
			return nic;
		}


		public void setNic(String nic) {
			this.nic = nic;
		}
		
		
		

}
