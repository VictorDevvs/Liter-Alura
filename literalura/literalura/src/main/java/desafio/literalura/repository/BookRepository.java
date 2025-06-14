package desafio.literalura.repository;

import desafio.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findByGutendexId(Long gutendexId);
    List<Book> findByLanguagesContainingIgnoreCase(String language);
    List<Book> findByAuthors_NameContainingIgnoreCase(String authorName);
    List<Book> findBySummariesContainingIgnoreCase(String topic);
}
