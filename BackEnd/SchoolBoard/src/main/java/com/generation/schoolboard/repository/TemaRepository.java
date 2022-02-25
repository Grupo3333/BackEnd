package com.generation.schoolboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.schoolboard.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long>{
	public List<Tema>findAllByTemaContainingIgnoreCase(String tema);
	public List<Tema>findAllByNivelContainingIgnoreCase(String nivel);
	public List<Tema>findAllByTemaContainingIgnoreCaseAndNivelContainingIgnoreCase(String tema, String nivel);

}
