package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.People;
import com.example.model.User;
import com.example.repository.PeopleRepository;


@Service("peopleService")
public class peopleServiceImpl implements PeopleService {

	@Autowired
	private PeopleRepository peopleRepository;
	
	
	@Override
	public void savePeople(People people) {
		// TODO Auto-generated method stub
		peopleRepository.save(people);
	}

	
	
	@Override
	public People findUserByNic(String nic) {
		// TODO Auto-generated method stub
		return peopleRepository.findByNic(nic);
	}



	@Override
	public List<People> findBy() {
		// TODO Auto-generated method stub
		return peopleRepository.findAll();
	}



	@Override
	public void delete() {
		peopleRepository.deleteAllInBatch();
		
	}



	
	

}
