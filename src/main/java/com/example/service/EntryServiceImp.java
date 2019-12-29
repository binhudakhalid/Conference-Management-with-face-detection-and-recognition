package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Entry;
import com.example.repository.EntryRepository;

@Service("entryService")
public class EntryServiceImp implements EntryService {

	@Autowired
	private EntryRepository entryRepository;

	@Override
	public void saveEntry(Entry entry) {
		// TODO Auto-generated method stub
		entryRepository.save(entry);
	}

	@Override
	public List<Entry> findBy() {
		// TODO Auto-generated method stub
		return entryRepository.findAll();
	}

	@Override
	public void delete() {
		entryRepository.deleteAllInBatch();
		
	}
}
