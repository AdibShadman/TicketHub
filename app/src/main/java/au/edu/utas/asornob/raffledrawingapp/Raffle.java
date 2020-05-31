package au.edu.utas.asornob.raffledrawingapp;

import android.net.Uri;

import java.util.Date;

public class Raffle
{



  private int id;
  private String Name;
  private String Description;
  private int totalTickets;
  private double ticketPrice;
  private Date startDate;

  private String raffleType;
  private Uri photo;
  private int lastTicket;



    private int winners;


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



     public String getRaffleType(){ return raffleType; }

     public void setRaffleType(String raffleType) { this.raffleType = raffleType; }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public int getLastTicket() {
        return lastTicket;
    }

    public void setLastTicket(int lastTicket) {
        this.lastTicket = lastTicket;
    }

    public int getWinners() {
    return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }
}
