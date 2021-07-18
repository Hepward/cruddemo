package com.example.rb.crudddemo.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = QuoteEndpoint.class)
class QuoteEndpointTest {

    @MockBean
    private QuoteService quoteService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllQuotes_expectList() throws Exception {
        QuoteDto e1 = new QuoteDto();
        e1.id = 1l;
        e1.author = "me";
        e1.content = "hello";
        QuoteDto e2 = new QuoteDto();
        e2.id = 2l;
        e2.author = "meee";
        e2.content = "hellooooo";
        List<QuoteDto> list = List.of(e1, e2);
        when(quoteService.getAllQuotes()).thenReturn(list);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/quotes"));

        resultActions.andExpect(status().isOk())
                     .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    void getSingleQuote_expectSingleQuote() throws Exception {
        QuoteDto e1 = new QuoteDto();
        e1.id = 1l;
        e1.author = "me";
        e1.content = "hello";
        when(quoteService.getQuoteById(1l)).thenReturn(e1);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/quotes/{id}", 1l));

        resultActions.andExpect(status().isOk())
                     .andExpect(content().json(objectMapper.writeValueAsString(e1)));
    }

    @Test
    void saveQuote_expectSingleQuote() throws Exception {
        QuoteDto e1 = new QuoteDto();
        e1.id = 1l;
        e1.author = "me";
        e1.content = "hello";

        when(quoteService.add(any())).thenReturn(e1);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/quotes")
                                                                            .content(objectMapper.writeValueAsString(e1))
                                                                            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                     .andExpect(content().json(objectMapper.writeValueAsString(e1)));
    }

    @Test
    void updateQuote_expectSingleQuote() throws Exception {
        QuoteDto e1 = new QuoteDto();
        e1.id = 1l;
        e1.author = "me";
        e1.content = "hello";

        when(quoteService.updateQuote(any(), any())).thenReturn(e1);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/quotes/{id}", 1l)
                                                                            .content(objectMapper.writeValueAsString(e1))
                                                                            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                     .andExpect(content().json(objectMapper.writeValueAsString(e1)));
    }

    @Test
    void deleteQuote_expectNoContent() throws Exception {
        doNothing().when(quoteService).deleteQuote(anyLong());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/quotes/{id}", 1l));

        resultActions.andExpect(status().isNoContent());
    }
}
