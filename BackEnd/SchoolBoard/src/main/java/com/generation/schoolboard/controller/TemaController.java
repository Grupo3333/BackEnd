package com.generation.schoolboard.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.schoolboard.model.Tema;
import com.generation.schoolboard.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	@Autowired
	public TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));

	}

	@PutMapping
	public ResponseEntity<Tema> putTema(@Valid @RequestBody Tema tema) {
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.ok().body(temaRepository.save(tema)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTema(@PathVariable Long id) {
		return temaRepository.findById(id).map(resposta -> {
			temaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/tema/{tema}")
	public ResponseEntity<List<Tema>> getAllByTema(@PathVariable String tema) {
		return ResponseEntity.ok(temaRepository.findAllByTemaContainingIgnoreCase(tema));
	}

	@GetMapping("/nivel/{nivel}")
	public ResponseEntity<List<Tema>> getAllByNivel(@PathVariable String nivel) {
		return ResponseEntity.ok(temaRepository.findAllByNivelContainingIgnoreCase(nivel));
		
	}
	
	@GetMapping("/tema/{tema}/nivel/{nivel}")
	public ResponseEntity<List<Tema>> getAllByTemaAndNivel(@PathVariable String tema, @PathVariable String nivel) {
		return ResponseEntity.ok(temaRepository.findAllByTemaContainingIgnoreCaseAndNivelContainingIgnoreCase(tema, nivel));
		
	}
	

}
