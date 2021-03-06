package br.com.saulusribeiro.springbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.saulusribeiro.springbackend.domain.Categoria;
import br.com.saulusribeiro.springbackend.dto.CategoriaDTO;
import br.com.saulusribeiro.springbackend.repositories.CategoriaRepository;
import br.com.saulusribeiro.springbackend.services.exceptions.DataIntegrityException;
import br.com.saulusribeiro.springbackend.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);

		// Utilizando uma função Lambda do Java 8
		// Para retornar o objeto ou a exceção para ser tratada por um Handler

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	public List<Categoria> findAll() {
		return repo.findAll();
	}


	public Categoria insert(Categoria obj) {

		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());

		// atualize os dados de newObj com base no argumento obj
		updateData(newObj, obj);
		// o mesmo metodo save usado no insert, quando o id é diferente de nulo ele
		// atualiza o objeto
		return repo.save(obj);

	}
	private void updateData(Categoria newObj, Categoria obj) {

		newObj.setNome(obj.getNome());
		
	}
	public void delete(Integer id) {
		try {
			find(id);
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
	
		}
	}
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest= 
			PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
			return repo.findAll(pageRequest);
		
		}
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(),objDTO.getNome());
	}
}
	
