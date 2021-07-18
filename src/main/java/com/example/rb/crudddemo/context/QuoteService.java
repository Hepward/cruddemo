package com.example.rb.crudddemo.context;

import com.example.rb.crudddemo.domain.Quote;
import com.example.rb.crudddemo.infrastructure.QuoteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<QuoteDto> getAllQuotes() {
        List<QuoteDto> quotes = new ArrayList<>();
        quoteRepository.findAll().forEach(quote -> {
            quotes.add(QuoteDto.from(quote));
        });
        return quotes;
    }

    public QuoteDto getQuoteById(Long id) {
        return quoteRepository.findById(id).map(QuoteDto::from)
                              .orElseThrow(() -> new RuntimeException("No quote with such id"));
    }

    public QuoteDto add(QuoteDto todo) {
        Quote quote = new Quote(null, todo.author, todo.content);
        return Optional.ofNullable(quoteRepository.save(quote))
                       .map(QuoteDto::from)
                       .orElseThrow(() -> new RuntimeException("Saving problem"));
    }

    public QuoteDto updateQuote(Long id, QuoteDto quote) {
        Quote quoteFromDb = quoteRepository.findById(id).get();
        quoteFromDb.setAuthor(quote.author);
        quoteFromDb.setContent(quote.content);
        return Optional.ofNullable(quoteRepository.save(quoteFromDb))
                .map(QuoteDto::from)
                .orElseThrow(() -> new RuntimeException("Saving problem"));
    }

    public void deleteQuote(Long quoteId) {
        quoteRepository.deleteById(quoteId);
    }
}
