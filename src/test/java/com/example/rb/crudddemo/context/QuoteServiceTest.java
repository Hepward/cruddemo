package com.example.rb.crudddemo.context;

import com.example.rb.crudddemo.domain.Quote;
import com.example.rb.crudddemo.infrastructure.QuoteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

//in real system i would use more real-life data and method naming
class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void getAllQuotes_expectWhatRepoReturned(){
        Quote e1 = new Quote(1l, "ja", "cze");
        Quote e2 = new Quote(2l, "on", "cze2");
        List<Quote> e11 = List.of(e1, e2);

        when(quoteRepository.findAll()).thenReturn(e11);
        List<QuoteDto> allQuotes = quoteService.getAllQuotes();

        List<QuoteDto> mappedResult = e11.stream().map(QuoteDto::from).collect(Collectors.toList());
        assertThat(allQuotes).containsAll(mappedResult);
    }

    @Test
    void getQuoteById_expectWhatRepoReturned(){
        Quote e1 = new Quote(1l, "ja", "cze");

        when(quoteRepository.findById(1l)).thenReturn(Optional.of(e1));
        QuoteDto quote = quoteService.getQuoteById(1l);

        assertThat(quote).isEqualTo(QuoteDto.from(e1));
    }

    @Test
    void getQuoteById_notFound_expectException(){
        when(quoteRepository.findById(1l)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> quoteService.getQuoteById(1l))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("No quote with such id");
    }

    @Test
    void add_expectReturnedAddedObject(){
        QuoteDto e1 = new QuoteDto();
        e1.content = "asd";
        e1.author = "aaa";
        Quote saved = new Quote(1l, "asd", "aaa");

        when(quoteRepository.save(any())).thenReturn(saved);
        QuoteDto quote = quoteService.add(e1);

        assertThat(quote).isEqualTo(QuoteDto.from(saved));
    }

    @Test
    void add_savingProblem_expectException(){
        QuoteDto e1 = new QuoteDto();
        e1.content = "asd";
        e1.author = "aaa";

        when(quoteRepository.save(any())).thenReturn(null);
        assertThatThrownBy(() -> quoteService.add(e1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Saving problem");
    }

    @Test
    void update_expectReturnedAddedObject(){
        QuoteDto e1 = new QuoteDto();
        e1.content = "updated";
        e1.author = "updated";
        Quote fromDb = new Quote(1l, "asd", "aaa");
        Quote updated = new Quote(1l, "updated", "updated");

        when(quoteRepository.findById(1l)).thenReturn(Optional.of(fromDb));
        when(quoteRepository.save(any())).thenReturn(updated);

        QuoteDto quote = quoteService.updateQuote(1l, e1);

        assertThat(quote).isEqualTo(QuoteDto.from(updated));
    }

    @Test
    void update_savingProblem_expectException(){
        QuoteDto e1 = new QuoteDto();
        e1.content = "updated";
        e1.author = "updated";
        Quote fromDb = new Quote(1l, "asd", "aaa");

        when(quoteRepository.findById(1l)).thenReturn(Optional.of(fromDb));

        when(quoteRepository.save(any())).thenReturn(null);
        assertThatThrownBy(() -> quoteService.updateQuote(1l, e1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Saving problem");
    }

    @Test
    void delete_expectRepoInvoked(){
        quoteService.deleteQuote(1l);
        verify(quoteRepository, times(1)).deleteById(1l);
        verifyNoMoreInteractions(quoteRepository);
    }
}
