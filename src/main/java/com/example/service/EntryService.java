package com.example.service;

import java.util.List;

import com.example.model.Entry;
import com.example.model.People;

public interface EntryService {
	
	public void saveEntry(Entry entry);
	public List<Entry> findBy();
	public void delete();

}
