package desafio.literalura;

import desafio.literalura.exception.CustomException;
import desafio.literalura.exception.ResourceNotFoundException;
import desafio.literalura.model.Book;
import desafio.literalura.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(BookService bookService) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			int option;

			do {
				displayMenu();
				try {
					option = scanner.nextInt();
					scanner.nextLine();

					switch (option) {
						case 1:
							System.out.print("Digite o título do livro: ");
							String title = scanner.nextLine();
							try {
								List<Book> books = bookService.searchBookByTitle(title);
								books.forEach(this::displayBookDetails);
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							} catch (CustomException e) {
								System.out.println("Erro ao buscar livro por título: " + e.getMessage());
							}
							break;
						case 2:
							System.out.print("Digite o ID do livro: ");
							try {
								Long id = scanner.nextLong();
								scanner.nextLine();
								Book book = bookService.searchBookById(id);
								displayBookDetails(book);
							} catch (InputMismatchException e) {
								System.out.println("ID inválido. Por favor, digite um número.");
								scanner.nextLine();
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							} catch (CustomException e) {
								System.out.println("Erro ao buscar livro por ID: " + e.getMessage());
							}
							break;
						case 3:
							System.out.print("Digite o idioma: ");
							String language = scanner.nextLine();
							try {
								List<Book> books = bookService.searchBooksByLanguage(language);
								books.forEach(this::displayBookDetails);
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							} catch (CustomException e) {
								System.out.println("Erro ao buscar livros por idioma: " + e.getMessage());
							}
							break;
						case 4:
							System.out.print("Digite o nome do autor: ");
							String authorName = scanner.nextLine();
							try {
								List<Book> books = bookService.searchBooksByAuthor(authorName);
								books.forEach(this::displayBookDetails);
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							} catch (CustomException e) {
								System.out.println("Erro ao buscar livros por autor: " + e.getMessage());
							}
							break;
						case 5:
							System.out.print("Digite o tópico: ");
							String topic = scanner.nextLine();
							try {
								List<Book> books = bookService.searchBooksByTopic(topic);
								books.forEach(this::displayBookDetails);
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							} catch (CustomException e) {
								System.out.println("Erro ao buscar livros por tópico: " + e.getMessage());
							}
							break;
						case 6:
							System.out.println("Saindo...");
							break;
						default:
							System.out.println("Opção inválida. Por favor, tente novamente.");
					}
				} catch (InputMismatchException e) {
					System.out.println("Entrada inválida. Por favor, digite um número para a opção.");
					scanner.nextLine();
					option = 0;
				} catch (Exception e) {
					System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
					option = 0;
				}
				System.out.println("\nPressione Enter para continuar...");
				scanner.nextLine();
			} while (option != 6);

			scanner.close();
		};
	}

	private void displayMenu() {
		System.out.println("\n--- Menu LiterAlura ---");
		System.out.println("1. Buscar livro pelo título");
		System.out.println("2. Buscar livro por ID");
		System.out.println("3. Buscar livros por idioma");
		System.out.println("4. Buscar livros por autor");
		System.out.println("5. Buscar livros por tópicos");
		System.out.println("6. Sair");
		System.out.print("Escolha uma opção: ");
	}

	private void displayBookDetails(Book book) {
		System.out.println("\n--- Detalhes do Livro ---");
		System.out.println("ID (Gutendex): " + book.getGutendexId());
		System.out.println("Título: " + book.getTitle());
		System.out.println("Autores: " + book.getAuthorsAsString());
		System.out.println("Idiomas: " + book.getLanguagesAsString());
		System.out.println("Tópicos (Resumos): " + String.join(", ", book.getSummaries()));
		System.out.println("--------------------------");
	}
}

