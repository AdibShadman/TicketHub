package au.edu.utas.asornob.raffledrawingapp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Winner {

    private int id;
    private int raffleId;
    private int ticketId;
    private int place;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(int raffleId) {
        this.raffleId = raffleId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public static ArrayList<Winner> drawWinners(SQLiteDatabase db, Raffle raffle){
        WinnerTable.deleteRaffleWinners(db, raffle.getId());

        ArrayList<Winner> winners = new ArrayList<Winner>();
        ArrayList<Ticket> tickets = TicketTable.raffleTickets(db, raffle.getId());
        for(int i = 0; i < raffle.getWinners(); i++) {
            boolean found = false;
            int c = 0;
            while(!found && c < 800) {
                int random = (int) (Math.random() * tickets.size());
                if(tickets.get(random) != null) {
                    Winner winner = new Winner();
                    winner.setRaffleId(raffle.getId());
                    winner.setTicketId(tickets.get(random).getId());
                    winner.setPlace(i);
                    winner.setCustomer(TicketTable.ticketOwner(db, random));
                    winners.add(winner);
                    WinnerTable.insert(db, winner);

                    tickets.set(random, null);

                    found = true;
                    c++;
                }
            }
        }

        return winners;
    }
}
