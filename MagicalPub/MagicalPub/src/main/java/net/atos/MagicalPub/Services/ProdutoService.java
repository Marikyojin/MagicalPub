package net.atos.MagicalPub.Services;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.atos.MagicalPub.Exceptions.ItemNotFoundException;
import net.atos.MagicalPub.Models.Produto;
import net.atos.MagicalPub.Repositories.ProdutoRepository;

@Service
public class ProdutoService {

	
	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	ImagemService imagemService;

	public List<Produto> getAll() {
		return produtoRepository.findAll();
	}

	public Produto getById(Long id) throws ItemNotFoundException {
		Optional<Produto> produto = produtoRepository.findById(id);

		if (produto.isEmpty()) {
			throw new ItemNotFoundException("Não existe produto com esse Id.");
		}

		return produto.get();
	}

	public Produto getByName(String nome) throws ItemNotFoundException {
		List<Produto> produto = produtoRepository.findByNome(nome);

		if (produto.isEmpty()) {
			throw new ItemNotFoundException("Não existe produto com esse nome.");
		}

		return produto.get(0);
	}

	public Produto getImagem(Produto produto) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/produto/{produtoId}/image")
				.buildAndExpand(produto.getId()).toUri();
		produto.setUrlImagem(uri.toString());
		return produto;
	}

	public Produto create(Produto produto, MultipartFile multipartFile)
			throws ItemNotFoundException, IOException {
		Produto entity = produto;

		Produto repository = produtoRepository.save(entity);

		imagemService.create(repository, multipartFile);

		return produtoRepository.save(getImagem(repository));
	}


	public Produto update(Long id, Produto produto) throws ItemNotFoundException {
		Produto entity = this.getById(id);

		if (produto.getNome() != null) {
			entity.setNome(produto.getNome());
		}

		if (produto.getDescricao() != null) {
			entity.setDescricao(produto.getDescricao());
		}

		if (produto.getCategoria() != null) {
			entity.setCategoria(categoriaService.getById(produto.getCategoria().getId()));
		}

		return produtoRepository.save(entity);
	}

	public void delete(Long id) throws ItemNotFoundException {
		this.getById(id);

		produtoRepository.deleteById(id);
	}
}
