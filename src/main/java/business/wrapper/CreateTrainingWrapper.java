package business.wrapper;

import java.util.Calendar;

public class CreateTrainingWrapper {
	
	private int courtId;
	private Calendar startDate;
	private Calendar endingDate;
	
	public CreateTrainingWrapper() {
	}

	public CreateTrainingWrapper(int courtId, Calendar startDate, Calendar endingDate) {
		this.courtId = courtId;
		this.startDate = startDate;
		this.endingDate = endingDate;
	}

	public int getCourtId() {
		return courtId;
	}

	public void setCourtId(int courtId) {
		this.courtId = courtId;
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
		return "TrainingWrapper [courtId=" + courtId + ", startDate=" + startDate + ", endingDate=" + endingDate + "]";
	}
	
	

}
