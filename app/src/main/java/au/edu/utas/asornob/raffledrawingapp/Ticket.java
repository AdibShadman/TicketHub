package au.edu.utas.asornob.raffledrawingapp;

import java.util.Date;

public class Ticket
{
  private int id;
  private double price;
  private Date purchaseTime;
  private Customer customer;
  private int raffleId;
  private int ticketNo;

    public int getId() {return id; }

    public void setId(int id) {this.id = id;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer){this.customer = customer;}

    public int getRaffleId() {return raffleId;}

    public void setRaffleId(int raffleId) {this.raffleId = raffleId;}

    public int getTicketNo() {return ticketNo;}

    public void setTicketNo(int ticketno) {this.ticketNo = ticketno;}

    public void incrementTicketNo() {ticketNo ++;}
}
