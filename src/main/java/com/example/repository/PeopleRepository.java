package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.People;
import com.example.model.User;

@Repository("peopleRepository")
public interface PeopleRepository extends JpaRepository<People, Integer>{
	People findById(int Integer);
	People findByNic(String nic);
	List<People> findBy();

}
