package desafio.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResult {
    private Long id;
    private String title;
    private List<GutendexAuthor> authors;
    @JsonAlias("subject")
    private List<String> subjects;
    private List<String> languages;
    @JsonAlias("bookshelves")
    private List<String> bookshelves;
}
