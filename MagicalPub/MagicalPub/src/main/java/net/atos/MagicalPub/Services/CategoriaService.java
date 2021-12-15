package net.atos.MagicalPub.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.atos.MagicalPub.Exceptions.CategoryReferencedByProductException;
import net.atos.MagicalPub.Exceptions.ItemNotFoundException;
import net.atos.MagicalPub.Models.Categoria;
import net.atos.MagicalPub.Models.Produto;
import net.atos.MagicalPub.Repositories.CategoriaRepository;

@Service
public class CategoriaService {

	
	@Autowired
	CategoriaRepository categoriaRepository;


	public List<Categoria> getAll() {
		return categoriaRepository.findAll();
	}

	@Transactional
	public List<Produto> getCategoryProducts(Long id) throws ItemNotFoundException {
		Categoria category = this.getById(id);

		List<Produto> listaProdutosResponse = new ArrayList<Produto>();

		for (Produto produto : category.getProdutosDaCategoria()) {
			listaProdutosResponse.add(produto);
		}

		return listaProdutosResponse;
	}

	public Categoria getById(Long id) throws ItemNotFoundException {
		Optional<Categoria> categoria = categoriaRepository.findById(id);

		if (categoria.isEmpty()) {
			throw new ItemNotFoundException("Não existe categoria com esse Id.");
		}

		return categoria.get();
	}

	public Categoria getByName(String nome) throws ItemNotFoundException {
		List<Categoria> categoria = categoriaRepository.findByNome(nome);

		if (categoria.isEmpty()) {
			throw new ItemNotFoundException("Não existe categoria com esse nome.");
		}

		// cada categoria tem um nome único
		return categoria.get(0);
	}

	public Categoria create(Categoria categoria) throws ItemNotFoundException {
		Categoria entity = categoria;

		return categoriaRepository.save(entity);
	}

	public Categoria update(Long id, Categoria categoria) throws ItemNotFoundException {
		Categoria entity = this.getById(id);

		if (categoria.getNome() != null) {
			entity.setNome(categoria.getNome());
		}

		if (categoria.getDescricao() != null) {
			entity.setDescricao(categoria.getDescricao());
		}
		return categoriaRepository.save(entity);
	}

	public void delete(Long id) throws ItemNotFoundException, CategoryReferencedByProductException {
		Categoria categoria = this.getById(id);

		if (!categoria.getProdutosDaCategoria().isEmpty()) {
			throw new CategoryReferencedByProductException(
					"Categorias referenciadas por algum produto não podem ser deletadas.");
		}

		categoriaRepository.deleteById(id);
	}
}

