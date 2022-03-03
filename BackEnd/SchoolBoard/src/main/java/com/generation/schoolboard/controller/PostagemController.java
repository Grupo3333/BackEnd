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

import com.generation.schoolboard.model.Postagem;
import com.generation.schoolboard.repository.PostagemRepository;
import com.generation.schoolboard.repository.TemaRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity <List<Postagem>> getAll()
	{
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Postagem> getById(@PathVariable Long id)
	{
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity <List<Postagem>> getByTitulo(@PathVariable String titulo)
	{
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	// busca por palavras chave no texto
	@GetMapping("/texto/{descricao}")
	public ResponseEntity <List<Postagem>> getByDescricao(@PathVariable String descricao)
	{
		return ResponseEntity.ok(postagemRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	//adicionar requisito do usuário
	
	@PostMapping
	public ResponseEntity <Postagem> postPostagem(@Valid @RequestBody Postagem postagem)
	{
		if (temaRepository.existsById(postagem.getTema().getId()))
		{
			return ResponseEntity.status(HttpStatus.CREATED).
					body(postagemRepository.save(postagem));
		}
		else
		{
			return ResponseEntity.badRequest().build();
		}
	}
	
	//adicionar requisito do usuário
	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem)
	{
		if (temaRepository.existsById(postagem.getTema().getId()))
				return postagemRepository.findById(postagem.getId())
						.map(resposta -> ResponseEntity.ok()
								.body(postagemRepository.save(postagem)))
						.orElse(ResponseEntity.notFound().build());

		return ResponseEntity.badRequest().build();	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable Long id)
	{
		return postagemRepository.findById(id)
				.map(resposta ->
				{
					postagemRepository.deleteById(id);
					return ResponseEntity.noContent().build();
				}).orElse(ResponseEntity.notFound().build());
	}
}
