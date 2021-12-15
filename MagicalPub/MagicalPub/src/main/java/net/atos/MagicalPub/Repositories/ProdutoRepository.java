package net.atos.MagicalPub.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.atos.MagicalPub.Models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByNome(String nome);
}
