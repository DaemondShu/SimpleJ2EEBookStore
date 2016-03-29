package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by monkey_d_asce on 16-3-26.
 */
@Entity(name = "book")
public class Book
{
    private int bookId;
    private String name;
    private String type;
    private Double price;

    public Book()
    {

    }


    public Book(String name, String type, Double price)
    {
        //this.BOOK_ID = BOOK_ID;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    @Id
    @Column(name = "bookId", nullable = false)
    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 45)
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bookId != book.bookId) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (type != null ? !type.equals(book.type) : book.type != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = bookId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
