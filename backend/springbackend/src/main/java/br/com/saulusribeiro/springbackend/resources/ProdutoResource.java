package br.com.saulusribeiro.springbackend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.saulusribeiro.springbackend.domain.Produto;
import br.com.saulusribeiro.springbackend.dto.ProdutoDTO;
import br.com.saulusribeiro.springbackend.resources.utils.URL;
import br.com.saulusribeiro.springbackend.services.ProdutoService;


@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {

		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}

	/*	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue ="") String nome,
			@RequestParam(value = "categorias", defaultValue ="") String categorias,
			@RequestParam(value = "page", defaultValue ="0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue ="24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue ="nome") String orderBy,
			@RequestParam(value = "direction", defaultValue ="ASC") String direction ) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeInt(categorias);
	//	Page<Produto> lista = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
				
				
		// Msssete : Percorrer a lista utilizando o recurso do JAVA 8 Stream, e converte uma lista para outra lista
		
	//	Page<ProdutoDTO> listaDTO = lista.map(obj -> new  ProdutoDTO(obj));
	//	return ResponseEntity.ok().body(listaDTO);
	}  */

}
