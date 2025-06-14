package desafio.literalura.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@ToString(exclude = "authors")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private Set<String> languages = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_summaries", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "summary", columnDefinition = "TEXT")
    private Set<String> summaries = new HashSet<>();

    private Long gutendexId;

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public String getAuthorsAsString() {
        return authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));
    }

    public String getLanguagesAsString() {
        return String.join(", ", languages);
    }
}
