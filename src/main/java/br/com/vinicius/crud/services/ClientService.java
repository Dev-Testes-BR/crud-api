package br.com.vinicius.crud.services;

import br.com.vinicius.crud.dtos.ClientDTO;
import br.com.vinicius.crud.entities.Client;
import br.com.vinicius.crud.repositories.ClientRepository;
import br.com.vinicius.crud.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client result = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
        return new ClientDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> pageClients = repository.findAll(pageable);
        return pageClients.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO insert (ClientDTO dto){
        Client entity = new Client();
        copyDtoToEntity(entity, dto);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        try {
            Client result = repository.getReferenceById(id);
            copyDtoToEntity(result, dto);
            result = repository.save(result);
            return new ClientDTO(result);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        Client result = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
        repository.deleteById(id);
    }

    private void copyDtoToEntity(Client entity, ClientDTO dto){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
