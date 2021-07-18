package com.example.rb.crudddemo.context;

import com.example.rb.crudddemo.domain.Quote;
import java.util.Objects;

public final class QuoteDto {
    public Long id;
    public String content;
    public String author;

    public static QuoteDto from(Quote quote) {
        QuoteDto dto = new QuoteDto();
        dto.id = quote.getId();
        dto.content = quote.getContent();
        dto.author = quote.getAuthor();
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuoteDto quoteDto = (QuoteDto) o;
        return Objects.equals(id, quoteDto.id) &&
                Objects.equals(content, quoteDto.content) &&
                Objects.equals(author, quoteDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, author);
    }
}
