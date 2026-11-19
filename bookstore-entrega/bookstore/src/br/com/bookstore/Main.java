package br.com.bookstore;

import br.com.bookstore.dao.LivroDAO;
import br.com.bookstore.dao.LivroDAOSqlite;
import br.com.bookstore.model.Livro;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // DIP: o Main depende da interface, não da implementação SQLite
        LivroDAO dao = new LivroDAOSqlite();

        System.out.println("=== 1. Salvando 3 livros ===");
        Livro l1 = new Livro("Clean Code", "Robert C. Martin", 89.90);
        Livro l2 = new Livro("Design Patterns", "Erich Gamma", 119.90);
        Livro l3 = new Livro("Refactoring", "Martin Fowler", 99.50);
        dao.salvar(l1);
        dao.salvar(l2);
        dao.salvar(l3);
        System.out.println("IDs gerados: " + l1.getId() + ", " + l2.getId() + ", " + l3.getId());

        System.out.println("\n=== 2. Buscando livro de ID 2 ===");
        dao.buscarPorId(2L).ifPresentOrElse(
                l -> System.out.println("Encontrado: " + l),
                () -> System.out.println("Aviso: livro com ID 2 não encontrado.")
        );

        System.out.println("\n=== 3. Buscando ID inexistente (999) ===");
        dao.buscarPorId(999L).ifPresentOrElse(
                l -> System.out.println("Encontrado: " + l),
                () -> System.out.println("Aviso: nenhum livro com ID 999 (sem NullPointerException).")
        );

        System.out.println("\n=== 4. Listando todos ===");
        List<Livro> todos = dao.listarTodos();
        System.out.printf("%-4s | %-20s | %-20s | %10s%n", "ID", "TÍTULO", "AUTOR", "PREÇO");
        System.out.println("-----+----------------------+----------------------+-----------");
        for (Livro l : todos) {
            System.out.printf("%-4d | %-20s | %-20s | R$ %7.2f%n",
                    l.getId(), l.getTitulo(), l.getAutor(), l.getPreco());
        }
    }
}
