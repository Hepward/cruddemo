package com.example.rb.crudddemo.infrastructure;

import com.example.rb.crudddemo.domain.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {
}
