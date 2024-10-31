package com.bnppf.books.domain.repositories;

import com.bnppf.books.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
