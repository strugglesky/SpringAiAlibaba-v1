package org.example.records;

import lombok.Data;

import java.util.Objects;

@Data
public class Book
{
    //Field
    int id;
    String bookName;

    public Book()
    {
    }

    public Book(int id, String bookName)
    {
        this.id = id;
        this.bookName = bookName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(bookName, book.bookName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, bookName);
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
