package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by monkey_d_asce on 16-3-26.
 */
@Entity(name = "order")
public class Order
{
    private int orderId;
    private Integer userId;
    private Integer bookId;
    private String address;
    private Date time;

    @Id
    @Column(name = "orderId", nullable = false)
    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "userId", nullable = true)
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    @Basic
    @Column(name = "bookId", nullable = true)
    public Integer getBookId()
    {
        return bookId;
    }

    public void setBookId(Integer bookId)
    {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 45)
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        if (bookId != null ? !bookId.equals(order.bookId) : order.bookId != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (time != null ? !time.equals(order.time) : order.time != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = orderId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
