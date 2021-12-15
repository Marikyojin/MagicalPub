package net.atos.MagicalPub.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.atos.MagicalPub.Exceptions.CategoryReferencedByProductException;
import net.atos.MagicalPub.Exceptions.ItemNotFoundException;

@RestControllerAdvice
public class ExceptionsController {

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<String> handleItemNotFoundException(ItemNotFoundException exception) {
		return ResponseEntity.notFound().header("x-erro-msg", exception.getMessage()).build();
	}

	@ExceptionHandler(CategoryReferencedByProductException.class)
	public ResponseEntity<String> handleCategoryReferencedByProductException(
			CategoryReferencedByProductException exception) {
		return ResponseEntity.notFound().header("x-erro-msg", exception.getMessage()).build();
	}
}
