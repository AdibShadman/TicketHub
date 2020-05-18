package au.edu.utas.asornob.raffledrawingapp;

import java.util.Date;

public class Raffle
{



  private int id;
  private String Name;
  private String Description;
  private int totalTickets;
  private double ticketPrice;
  private Date startDate;
  private Date endDate;
  private String raffleType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

     public String getRaffleType(){ return raffleType; }

     public void setRaffleType(String raffleType) { this.raffleType = raffleType; }



}
