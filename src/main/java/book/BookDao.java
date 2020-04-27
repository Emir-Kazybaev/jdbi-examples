package book;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RegisterBeanMapper(Book.class)
public interface BookDao {

    @SqlUpdate("""
        CREATE TABLE book (
            isbn13 VARCHAR PRIMARY KEY,
            author VARCHAR NOT NULL,
            title VARCHAR NOT NULL,
            format VARCHAR NOT NULL,
            publisher VARCHAR NOT NULL,
            publicationDate DATE NOT NULL,
            pages INTEGER NOT NULL,
            available VARCHAR NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO book VALUES(:isbn13, :author ,:title ,:format , :publisher ,:publicationDate ,:pages, :available")
    void insert(@Bind("isbn13") String isbn13, @Bind("author") String author, @Bind("title") String title, @Bind("format") Book.Format format,
                @Bind("publisher") String publisher, @Bind("publicationDate")LocalDate publicationDate,
                @Bind("pages") int pages,@Bind("available") Boolean available);

    @SqlUpdate("INSERT INTO book VALUES (:isbn13, :author ,:title,:format, :publisher ,:publicationDate,:pages,:available)")
    void insert(@BindBean Book book);

    @SqlQuery("SELECT * FROM book WHERE isbn13 = :isbn13")
    Optional<Book> find(@Bind("isbn13") String isbn13);

    @SqlQuery("SELECT * FROM book ORDER BY publicationDate")
    List<Book> findAll();

    @SqlUpdate("DELETE FROM book WHERE isbn13 = :isbn13")
    void deleteBook(@BindBean Book book);

}