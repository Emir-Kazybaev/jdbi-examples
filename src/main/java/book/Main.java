package book;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:testdb");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            BookDao dao = handle.attach(BookDao.class);
            dao.createTable();
            dao.insert(new Book("9780571334650","Sally Rooney",
                    "Normal People", Book.Format.PAPERBACK, "FABER & FABER",
                    LocalDate.of(2020,2,22),288,true));
            dao.insert(new Book("9781840220766","Sir Arthur Conan Doyle",
                    "The Complete Stories of Sherlock Holmes", Book.Format.HARDBACK,"Wordsworth Editions Ltd",
                    LocalDate.of(2008,3,8),1408,true));
            dao.insert(new Book("9780486821511","Immanuel Kant",
                    "Critique of Pure Reason", Book.Format.PAPERBACK,"Dover Publications Inc.",
                    LocalDate.of(2019,5,29),512,false));
            Book book1 = dao.find("9781840220766").get();
            dao.findAll().stream().forEach(System.out::println);
            dao.deleteBook(book1);
            System.out.println("The Complete Stories of Sherlock Holmes is deleted from database");
            dao.findAll().stream().forEach(System.out::println);
        }
    }

}
