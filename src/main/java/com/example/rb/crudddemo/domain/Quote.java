package com.example.rb.crudddemo.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Quote {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column
    private String author;
    @Column
    private String content;

    public Quote() {
    }

    public Quote(Long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        return id.equals(quote.id) &&
                Objects.equals(author, quote.author) &&
                Objects.equals(content, quote.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, content);
    }
}
