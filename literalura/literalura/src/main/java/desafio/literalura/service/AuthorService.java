package desafio.literalura.service;

import desafio.literalura.exception.ResourceNotFoundException;
import desafio.literalura.model.Author;
import desafio.literalura.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findAuthorByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado no banco de dados: " + name));
    }
}
