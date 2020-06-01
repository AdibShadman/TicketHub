package au.edu.utas.asornob.raffledrawingapp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.edu.utas.asornob.raffledrawingapp.Tables.RaffleTable;
import au.edu.utas.asornob.raffledrawingapp.Tables.TicketTable;
import au.edu.utas.asornob.raffledrawingapp.Tables.WinnerTable;

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

        Log.d("winners: ", "" + raffle.getWinners());

        ArrayList<Winner> winners = new ArrayList<Winner>();
        ArrayList<Ticket> tickets = TicketTable.raffleTickets(db, raffle.getId());
        for(int i = 0; i < Math.min(raffle.getWinners(), tickets.size()); i++) {
            boolean found = false;
            int c = 0;
            Winner winner;
            while(!found && c < raffle.getTotalTickets() * 10) {
                int random = (int) (Math.random() * tickets.size());
                if(tickets.get(random) != null) {
                    winner = new Winner();
                    winner.setRaffleId(raffle.getId());
                    winner.setTicketId(tickets.get(random).getId());
                    winner.setPlace(i);
                    winner.setCustomer(TicketTable.ticketOwner(db, tickets.get(random).getId()));
                    winners.add(winner);
                    WinnerTable.insert(db, winner);

                    tickets.set(random, null);

                    found = true;
                    c++;
                }
            }
        }
        RaffleTable.setDrawn(db, raffle.getId(), 1);

        return winners;
    }

    public static ArrayList<Winner> drawMarginWinners(SQLiteDatabase db, Raffle raffle, int margin){
        WinnerTable.deleteRaffleWinners(db, raffle.getId());

        ArrayList<Winner> winners = new ArrayList<Winner>();
        ArrayList<Ticket> tickets = TicketTable.raffleTickets(db, raffle.getId());
        Ticket ticket;
        Winner winner;
        int placeCounter = 0;
        for(int i = 0; i < tickets.size(); i++) {
            ticket = tickets.get(i);
            Log.d("Ticket: ", ticket.getTicketNo() + ", Cust: " + ticket.getCustomer().getName());
            if(ticket.getTicketNo() == margin)
            {
                winner = new Winner();
                winner.setRaffleId(raffle.getId());
                winner.setTicketId(ticket.getId());
                winner.setPlace(placeCounter);
                winner.setCustomer(TicketTable.ticketOwner(db, ticket.getId()));
                WinnerTable.insert(db, winner);

                winners.add(winner);
                //placeCounter++;
                break;
            }
        }
        RaffleTable.setDrawn(db, raffle.getId(), 1);
        return winners;
    }
}
