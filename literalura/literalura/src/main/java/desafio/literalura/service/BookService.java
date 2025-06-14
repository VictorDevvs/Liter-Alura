package desafio.literalura.service;

import desafio.literalura.dto.GutendexBookResponse;
import desafio.literalura.dto.GutendexResult;
import desafio.literalura.exception.ResourceNotFoundException;
import desafio.literalura.model.Author;
import desafio.literalura.model.Book;
import desafio.literalura.repository.AuthorRepository;
import desafio.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final WebClient webClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(WebClient.Builder webClientBuilder, BookRepository bookRepository, AuthorRepository authorRepository,
                       WebClient webClient) {
        this.webClient = webClient;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public List<Book> searchBookByTitle(String title) {
        Optional<Book> existingBook = bookRepository.findByTitleContainingIgnoreCase(title);
        if (existingBook.isPresent()) {
            System.out.println("Livro encontrado no banco de dados (por título):");
            return List.of(existingBook.get());
        } else {
            System.out.println("Buscando livro por título na API Gutendex...");
            GutendexBookResponse response = webClient.get()
                    .uri("?search={title}", title)
                    .retrieve()
                    .bodyToMono(GutendexBookResponse.class)
                    .block();

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                List<Book> books = response.getResults().stream()
                        .map(this::saveBookFromGutendexResult)
                        .collect(Collectors.toList());
                System.out.println("Livro(s) encontrado(s) e salvo(s) no banco de dados.");
                return books;
            } else {
                throw new ResourceNotFoundException("Nenhum livro encontrado com o título: " + title);
            }
        }
    }

    @Transactional
    public Book searchBookById(Long id) {
        Optional<Book> existingBook = bookRepository.findByGutendexId(id);
        if (existingBook.isPresent()) {
            System.out.println("Livro encontrado no banco de dados (por ID):");
            return existingBook.get();
        } else {
            System.out.println("Buscando livro por ID na API Gutendex...");
            GutendexResult result = webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(GutendexResult.class)
                    .block();

            if (result != null) {
                Book book = saveBookFromGutendexResult(result);
                System.out.println("Livro encontrado e salvo no banco de dados.");
                return book;
            } else {
                throw new ResourceNotFoundException("Nenhum livro encontrado com o ID: " + id);
            }
        }
    }

    @Transactional
    public List<Book> searchBooksByLanguage(String language) {
        List<Book> existingBooks = bookRepository.findByLanguagesContainingIgnoreCase(language);
        if (!existingBooks.isEmpty()) {
            System.out.println("Livros encontrados no banco de dados (por idioma):");
            return existingBooks;
        } else {
            System.out.println("Buscando livros por idioma na API Gutendex...");
            GutendexBookResponse response = webClient.get()
                    .uri("?search={language}", language)
                    .retrieve()
                    .bodyToMono(GutendexBookResponse.class)
                    .block();

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                List<Book> books = response.getResults().stream()
                        .map(this::saveBookFromGutendexResult)
                        .collect(Collectors.toList());
                System.out.println("Livros encontrados e salvos no banco de dados.");
                return books;
            } else {
                throw new ResourceNotFoundException("Nenhum livro encontrado com o idioma: " + language);
            }
        }
    }

    @Transactional
    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> existingBooks = bookRepository.findByAuthors_NameContainingIgnoreCase(authorName);
        if (!existingBooks.isEmpty()) {
            System.out.println("Livros encontrados no banco de dados (por autor):");
            return existingBooks;
        } else {
            System.out.println("Buscando livros por autor na API Gutendex...");
            GutendexBookResponse response = webClient.get()
                    .uri("?search={authorName}", authorName)
                    .retrieve()
                    .bodyToMono(GutendexBookResponse.class)
                    .block();

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                List<Book> books = response.getResults().stream()
                        .map(this::saveBookFromGutendexResult)
                        .collect(Collectors.toList());
                System.out.println("Livros encontrados e salvos no banco de dados.");
                return books;
            } else {
                throw new ResourceNotFoundException("Nenhum livro encontrado com o autor: " + authorName);
            }
        }
    }

    @Transactional
    public List<Book> searchBooksByTopic(String topic) {
        List<Book> existingBooks = bookRepository.findBySummariesContainingIgnoreCase(topic);
        if (!existingBooks.isEmpty()) {
            System.out.println("Livros encontrados no banco de dados (por tópico):");
            return existingBooks;
        } else {
            System.out.println("Buscando livros por tópico na API Gutendex...");
            GutendexBookResponse response = webClient.get()
                    .uri("?search={topic}", topic)
                    .retrieve()
                    .bodyToMono(GutendexBookResponse.class)
                    .block();

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                List<Book> books = response.getResults().stream()
                        .map(this::saveBookFromGutendexResult)
                        .collect(Collectors.toList());
                System.out.println("Livros encontrados e salvos no banco de dados.");
                return books;
            } else {
                throw new ResourceNotFoundException("Nenhum livro encontrado com o tópico: " + topic);
            }
        }
    }

    private Book saveBookFromGutendexResult(GutendexResult result) {
        Book book = new Book();
        book.setGutendexId(result.getId());
        book.setTitle(result.getTitle());
        book.setLanguages(new java.util.HashSet<>(result.getLanguages()));
        book.setSummaries(new java.util.HashSet<>(result.getSubjects())); // Usando subjects da API como summaries

        Set<Author> authors = result.getAuthors().stream()
                .map(gutendexAuthor -> {
                    Optional<Author> existingAuthor = authorRepository.findByNameContainingIgnoreCase(gutendexAuthor.getName());
                    Author author;
                    if (existingAuthor.isPresent()) {
                        author = existingAuthor.get();
                    } else {
                        author = new Author(gutendexAuthor.getName(), gutendexAuthor.getBirth_year(), gutendexAuthor.getDeath_year());
                        authorRepository.save(author);
                    }
                    return author;
                })
                .collect(Collectors.toSet());
        book.setAuthors(authors);
        return bookRepository.save(book);
    }
}
