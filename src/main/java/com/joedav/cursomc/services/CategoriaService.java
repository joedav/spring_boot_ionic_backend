package com.joedav.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joedav.cursomc.domain.Categoria;
import com.joedav.cursomc.repositories.CategoriaRepository;
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
}
