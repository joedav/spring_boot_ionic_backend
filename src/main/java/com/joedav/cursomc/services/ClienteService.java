package com.joedav.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joedav.cursomc.domain.Cliente;
import com.joedav.cursomc.dto.ClienteDTO;
import com.joedav.cursomc.repositories.ClienteRepository;
import com.joedav.cursomc.services.exceptions.DataIntegrityException;
import com.joedav.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	// método para inserir cliente
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	// método para procurar todos os clientes
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	// buscar por id
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		// retorna o objeto se encontrar, caso contrário retorna uma exception
		// personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + " Tipo: " + Cliente.class.getName()));
	}

	// metodo para alterar cliente
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	// deletar cliente
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException de) {
			throw new DataIntegrityException("Não é possível excluir o cliente pois possui vínculos!");
		}
	}

	// métod para converter Cliente em cliente dto
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), null);
	}

	// pagina de categorias
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	// metodo para salvar os dados da atualização do cliente
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpfOuCnpj(obj.getCpfOuCnpj());
	}
}
