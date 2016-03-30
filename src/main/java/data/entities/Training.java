package data.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Training {
	
	public static final int CAPACITY_CLASS = 4;
	
	@Id
    @GeneratedValue
    private int id;
	
	@ManyToOne
    @JoinColumn
    private Court court;
	
	@ManyToMany(fetch = FetchType.EAGER)
    private List<User> players;
	
	@ManyToOne
    @JoinColumn
    private User trainer;
	
	private Calendar startDate;
	
	private Calendar endingDate;
	
	public Training(Court court, User trainer, Calendar startDate, Calendar endingDate){
		this.court = court;
		this.trainer = trainer;
		this.startDate = startDate;
		this.endingDate = endingDate;
		this.players = new ArrayList<User>();
	}
	
	public Training() {
	}
	
	public boolean addPlayer(User player) {
		boolean added = false;
		if (this.players.size() < CAPACITY_CLASS) {
			this.players.add(player);
			added = true;
		}
		return added;
	}
	
	public boolean removePlayer(User player) {
		boolean removed = false;
		int userIndex = this.players.indexOf(player);
		if (userIndex != -1) {
			this.players.remove(userIndex);
			removed = true;
		}
		return removed;
	}

	public int getId() {
		return id;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public User getTrainer() {
		return trainer;
	}

	public void setTrainer(User trainer) {
		this.trainer = trainer;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Calendar endingDate) {
		this.endingDate = endingDate;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return this.id == ((Training) obj).id;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", court=" + court + ", players=" + players + ", trainer=" + trainer
				+ ", startDate=" + startDate + ", endingDate=" + endingDate + "]";
	}
	
}
