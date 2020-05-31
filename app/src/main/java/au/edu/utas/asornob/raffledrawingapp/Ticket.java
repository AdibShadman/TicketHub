package au.edu.utas.asornob.raffledrawingapp;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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

   /* public static ArrayList<ArrayList<Ticket>> customerTicketsFromCustomer(SQLiteDatabase db, String text)
    {
      ArrayList<Customer> customers = CustomerTable.selectByNameFilter(db, text);
      ArrayList<ArrayList<Ticket>> customerTickets = new ArrayList<ArrayList<Ticket>>();
      for (int i = 0 ; i < customers.size(); i++)
      {
        customerTickets.add(TicketTable.selectByNameFilter(db, customers.get(i).getId()));
      }
      return customerTickets;
    }*/

  public static ArrayList<Ticket> tickets(SQLiteDatabase db, String text, int raffleId)
  {
    ArrayList<Customer> customers = CustomerTable.selectByNameFilter(db, text);
    ArrayList<Ticket> customerTickets = new ArrayList<Ticket>();
    for (int i = 0 ; i < customers.size(); i++)
    {
      customerTickets.addAll(TicketTable.selectByNameFilter(db, customers.get(i).getId(), raffleId));
    }
    return customerTickets;
  }
}
