package business.wrapper;

import java.util.Calendar;
import java.util.List;

public class TrainingWrapper {
	
	private int id;
	private String trainer;
	private List<String> players;
	private int court;
	private Calendar startDate;
	private Calendar endingDate;
	
	public TrainingWrapper() {
	}

	public TrainingWrapper(int id, String trainer, List<String> players, int court, Calendar startDate, Calendar endingDate) {
		super();
		this.id = id;
		this.trainer = trainer;
		this.players = players;
		this.court = court;
		this.startDate = startDate;
		this.endingDate = endingDate;
	}
	
	public int getId() {
		return this.id;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public int getCourt() {
		return court;
	}

	public void setCourt(int court) {
		this.court = court;
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
	public String toString() {
		return "TrainingWrapper [trainer=" + trainer + ", players=" + players + ", court=" + court + ", startDate="
				+ startDate + ", endingDate=" + endingDate + "]";
	}

}
