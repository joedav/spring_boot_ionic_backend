package com.joedav.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joedav.cursomc.domain.Categoria;
import com.joedav.cursomc.dto.CategoriaDTO;
import com.joedav.cursomc.repositories.CategoriaRepository;
import com.joedav.cursomc.services.exceptions.DataIntegrityException;
import com.joedav.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	// essa dependencia irá ser automaticamente instanciada pelo spring
	@Autowired
	private CategoriaRepository repo;

	// método para procurar por uma categoria via id
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		// retorna o objeto se encontrar, caso contrário retorna uma exception personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", tipo: " + Categoria.class.getName()));
	}
	
	// método para inserir uma categoria
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	// método par alterar
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	// método para deletar uma categoria
	public void delete(Integer id) {
		// procura pelo id
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException de) {
			throw new DataIntegrityException("Não é possível excluir a categoria por que ela possui produtos!");
		}
	}
	
	// método para buscar por todas as categorias
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	// pagina de categorias
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	// método para converter a categoria em uma categoria DTO
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
