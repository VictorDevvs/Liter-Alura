package desafio.literalura.repository;

import desafio.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameContainingIgnoreCase(String name);
}
