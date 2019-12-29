package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Entry;

@Repository("entryRepository")
public interface EntryRepository extends JpaRepository<Entry, Integer>{
	Entry findById(int Integer);
	List<Entry> findBy();

}
