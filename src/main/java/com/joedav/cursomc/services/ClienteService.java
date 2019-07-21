package com.joedav.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.joedav.cursomc.domain.Cidade;
import com.joedav.cursomc.domain.Cliente;
import com.joedav.cursomc.domain.Endereco;
import com.joedav.cursomc.domain.enums.TipoCliente;
import com.joedav.cursomc.dto.ClienteDTO;
import com.joedav.cursomc.dto.ClienteNewDTO;
import com.joedav.cursomc.repositories.ClienteRepository;
import com.joedav.cursomc.repositories.EnderecoRepository;
import com.joedav.cursomc.services.exceptions.DataIntegrityException;
import com.joedav.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;

	// método para inserir cliente
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepo.saveAll((obj.getEnderecos()));
		return obj;
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
	@Transactional // para salvar tanto o cliente quanto o outros vinculos
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

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2()!= null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!= null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
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
