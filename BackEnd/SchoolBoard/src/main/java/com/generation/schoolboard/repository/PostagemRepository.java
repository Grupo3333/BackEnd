package com.generation.schoolboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.schoolboard.model.Postagem;

public interface PostagemRepository extends JpaRepository <Postagem, Long> {
	
	public List <Postagem> findAllByTituloContainingIgnoreCase (String titulo);
	public List <Postagem> findAllByDescricaoContainingIgnoreCase (String descricao);

}
