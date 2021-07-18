package com.example.rb.crudddemo.context;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/quotes")
class QuoteEndpoint {

    private final QuoteService quoteService;

    public QuoteEndpoint(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ResponseEntity<List<QuoteDto>> getQuotes() {
        List<QuoteDto> quotes = quoteService.getAllQuotes();
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDto> getSingleQuote(@PathVariable Long id) {
        QuoteDto quote = quoteService.getQuoteById(id);
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuoteDto> saveQuote(@RequestBody QuoteDto quote) {
        QuoteDto savedQuote = quoteService.add(quote);
        return new ResponseEntity<>(savedQuote, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<QuoteDto> updateQuote(@PathVariable("id") Long id, @RequestBody QuoteDto todo) {
        QuoteDto updatedQuote = quoteService.updateQuote(id, todo);
        return new ResponseEntity<>(updatedQuote, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
