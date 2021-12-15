package net.atos.MagicalPub.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import net.atos.MagicalPub.Exceptions.CategoryReferencedByProductException;
import net.atos.MagicalPub.Exceptions.ItemNotFoundException;
import net.atos.MagicalPub.Models.Categoria;
import net.atos.MagicalPub.Models.Produto;
import net.atos.MagicalPub.Services.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaService service;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		List<Categoria> listaCategoriasResponse = new ArrayList<Categoria>();

		for (Categoria categoria : service.getAll()) {
			listaCategoriasResponse.add(categoria);
		}

		return new ResponseEntity<List<Categoria>>(listaCategoriasResponse, HttpStatus.OK);
	}

	@GetMapping("/{id}/produtos")
	public ResponseEntity<List<Produto>> getCategoryProducts(@PathVariable Long id)
			throws ItemNotFoundException {
		List<Produto> listaProdutos = service.getCategoryProducts(id);

		return new ResponseEntity<List<Produto>>(listaProdutos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) throws ItemNotFoundException {
		Categoria categoriaResponse = (service.getById(id));

		return new ResponseEntity<Categoria>(categoriaResponse, HttpStatus.OK);
	}

	@GetMapping("busca")
	public ResponseEntity<Categoria> getByName(@RequestParam String nome) throws ItemNotFoundException {
		Categoria categoriaResponse = (service.getByName(nome));

		return new ResponseEntity<Categoria>(categoriaResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody Categoria categoria) throws ItemNotFoundException {
		service.create(categoria);

		return new ResponseEntity<String>("Categoria cadastrada com sucesso", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Categoria categoria)
			throws ItemNotFoundException {
		service.update(id, categoria);

		return new ResponseEntity<String>("Categoria editada com sucesso", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id)
			throws ItemNotFoundException, CategoryReferencedByProductException {
		service.delete(id);

		return new ResponseEntity<String>("Categoria deletada com sucesso", HttpStatus.OK);
	}
}
