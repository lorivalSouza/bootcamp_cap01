package com.devlps.bootcamp_cap01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devlps.bootcamp_cap01.dto.ClientDTO;
import com.devlps.bootcamp_cap01.entities.Client;
import com.devlps.bootcamp_cap01.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		
		return list.map(x -> new ClientDTO(x));
	}

}
