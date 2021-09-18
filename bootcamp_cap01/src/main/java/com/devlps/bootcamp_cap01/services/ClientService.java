package com.devlps.bootcamp_cap01.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlps.bootcamp_cap01.dto.ClientDTO;
import com.devlps.bootcamp_cap01.entities.Client;
import com.devlps.bootcamp_cap01.repositories.ClientRepository;
import com.devlps.bootcamp_cap01.services.exceptions.DatabaseException;
import com.devlps.bootcamp_cap01.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		return new ClientDTO(entity );
	}

	@Transactional
	public ClientDTO update(ClientDTO dto, Long id) {
		try {
			Client entity = repository.getOne(id);
			
			copyDtoToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			return new ClientDTO(entity );
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Client id " + id + " not found");
		}
		
	}


	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
		entity.setCpf(dto.getCpf());		
	}

	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
			}catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException("Id " + id + " not found ");
			}catch (DataIntegrityViolationException e) {
				throw new DatabaseException("Integrity violation");
			}
		
	}
}
