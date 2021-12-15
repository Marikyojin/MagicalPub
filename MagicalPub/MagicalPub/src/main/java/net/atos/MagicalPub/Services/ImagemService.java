package net.atos.MagicalPub.Services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.atos.MagicalPub.Models.Imagem;
import net.atos.MagicalPub.Models.Produto;
import net.atos.MagicalPub.Repositories.ImagemRepository;

@Service
public class ImagemService {

	
	@Autowired
	ImagemRepository repository;

	@Transactional
	public Imagem create(Produto produto, MultipartFile multipartFile) throws IOException {
		Imagem imageProduto = new Imagem();
		imageProduto.setProduto(produto);
		imageProduto.setData(multipartFile.getBytes());
		imageProduto.setMimeType(multipartFile.getContentType());
		imageProduto.setNome(produto.getNome().replace(" ", "-") + "-img");
		return repository.save(imageProduto);
	}

	@Transactional
	public Imagem getImagem(Long id) {
		Imagem image = repository.findByProdutoId(id);
		return image;
	}
}


