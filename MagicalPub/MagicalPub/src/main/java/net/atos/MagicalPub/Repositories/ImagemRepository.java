package net.atos.MagicalPub.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.atos.MagicalPub.Models.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {

	Imagem findByProdutoId(Long id);

}