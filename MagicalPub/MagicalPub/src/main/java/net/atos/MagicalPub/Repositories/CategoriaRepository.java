package net.atos.MagicalPub.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.atos.MagicalPub.Models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	List<Categoria> findByNome(String nome);
}