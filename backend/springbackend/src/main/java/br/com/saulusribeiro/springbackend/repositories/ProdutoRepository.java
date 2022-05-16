package br.com.saulusribeiro.springbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saulusribeiro.springbackend.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
