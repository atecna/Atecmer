package model;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity tickets
 *
 * @author : Gilles Andrieu [Atecna](http://www.atecna.fr/)
 * @version : 1.0
 */

@XmlRootElement
public class Tickets implements Serializable {
    // Properties
    private Long tickets_id;
    private String ticket_number;
    private String duration;
    private Date start_time;
    private Date end_time;
    private String billed;
    private String description;
    private Long users_id;

    // Constructor
    public Tickets() { }

    public Tickets(String ticket_number, String duration, Date start_time, Date end_time, String billed, String description) {
        this.ticket_number = ticket_number;
        this.duration = duration;
        this.start_time = start_time;
        this.end_time = end_time;
        this.billed = billed;
        this.description = description;
    }

    public Tickets(Long tickets_id, String ticket_number, String duration, Date start_time, Date end_time, String billed, String description, Long users_id) {
        this.tickets_id = tickets_id;
        this.ticket_number = ticket_number;
        this.duration = duration;
        this.start_time = start_time;
        this.end_time = end_time;
        this.billed = billed;
        this.description = description;
        this.users_id = users_id;
    }

    // Getters / Setters

    public Long getTickets_id() { return tickets_id; }

    public void setTickets_id(Long tickets_id) { this.tickets_id = tickets_id; }

    public String getTicket_number() { return ticket_number; }

    public void setTicket_number(String ticket_number) { this.ticket_number = ticket_number; }

    public String getDuration() { return duration; }

    public void setDuration(String duration) { this.duration = duration; }

    public Date getStart_time() { return start_time; }

    public void setStart_time(Date start_time) { this.start_time = start_time; }

    public Date getEnd_time() { return end_time; }

    public void setEnd_time(Date end_time) { this.end_time = end_time; }

    public String getBilled() { return billed; }

    public void setBilled(String billed) { this.billed = billed; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Long getUsers_id() { return users_id; }

    public void setUsers_id(Long users_id) { this.users_id = users_id; }

    @Override
    public String toString() {
        return "Tickets{" +
                "tickets_id=" + tickets_id +
                ", ticket_number='" + ticket_number + '\'' +
                ", duration='" + duration + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", billed=" + billed +
                ", description='" + description + '\'' +
                ", users_id=" + users_id +
                '}';
    }
}
