package net.atos.MagicalPub.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.atos.MagicalPub.Exceptions.ItemNotFoundException;
import net.atos.MagicalPub.Models.Imagem;
import net.atos.MagicalPub.Models.Produto;
import net.atos.MagicalPub.Services.ImagemService;
import net.atos.MagicalPub.Services.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService service;

	@Autowired
	ImagemService imagemService;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		List<Produto> listaProdutosResponse = new ArrayList<Produto>();

		for (Produto produto : service.getAll()) {
			listaProdutosResponse.add(produto);
		}

		return new ResponseEntity<List<Produto>>(listaProdutosResponse, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) throws ItemNotFoundException {
		Produto produtoResponse = (service.getById(id));

		return new ResponseEntity<Produto>(produtoResponse, HttpStatus.OK);
	}

	@GetMapping("busca")
	public ResponseEntity<Produto> getByName(@RequestParam String nome) throws ItemNotFoundException {
		Produto produtoResponse = (service.getByName(nome));

		return new ResponseEntity<Produto>(produtoResponse, HttpStatus.OK);
	}

	@GetMapping("/{produtoId}/image")
	public ResponseEntity<byte[]> getImagem(@PathVariable Long produtoId) {
		Imagem imagem = imagemService.getImagem(produtoId);
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		return new ResponseEntity<>(imagem.getData(), header, HttpStatus.OK);
	}

	
	@PostMapping
	public ResponseEntity<String> create(@RequestParam MultipartFile file, @RequestPart Produto produto)
			throws ItemNotFoundException, IOException {
		service.create(produto, file);

		return new ResponseEntity<String>("Produto cadastrado com sucesso", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Produto produto)
			throws ItemNotFoundException {
		service.update(id, produto);

		return new ResponseEntity<String>("Produto editado com sucesso", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws ItemNotFoundException {
		service.delete(id);

		return new ResponseEntity<String>("Produto deletado com sucesso", HttpStatus.OK);
	}
}
