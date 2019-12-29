package com.example.service;

import java.util.List;

import com.example.model.People;

public interface PeopleService {
	public People findUserByNic(String nic);
	public void savePeople(People people);
	public List<People> findBy();
	public void delete();
}


