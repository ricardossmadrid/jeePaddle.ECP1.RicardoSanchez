package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.CreateTrainingWrapper;
import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Training;

@Controller
public class TrainingController {
	
	private TrainingDao trainingDao;
	
	private CourtDao courtDao;
	
	private UserDao userDao;
	
	private ReserveDao reserveDao;
	
	private ReserveController reserveController;
	
	@Autowired
	public void setTrainingDao(TrainingDao trainingDao) {
		this.trainingDao = trainingDao;
	}
	
	@Autowired
	public void setCourtDao(CourtDao courtDao) {
		this.courtDao = courtDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Autowired
	public void setReserveDao(ReserveDao reserveDao) {
		this.reserveDao = reserveDao;
	}
	
	@Autowired
	public void setReserveController(ReserveController reserveController) {
		this.reserveController = reserveController;
	}

	public boolean createTraining(String activeUser, CreateTrainingWrapper trainingWrapper) {
		
		Training training = new Training(courtDao.findOne(trainingWrapper.getCourtId()), userDao.findByUsernameOrEmail(activeUser), trainingWrapper.getStartDate(), trainingWrapper.getEndingDate());
		
		Calendar startDate = trainingWrapper.getStartDate(),
				endingDate = trainingWrapper.getEndingDate();
		int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK),
				hour = startDate.get(Calendar.HOUR_OF_DAY);
		
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		
		boolean underhandedReservation = false;
		while (startDate.before(endingDate) && !underhandedReservation) {
			if (!reserveController.reserveCourt(trainingWrapper.getCourtId(), startDate, activeUser)) {
				underhandedReservation = true;
				break;
			}
			startDate.set(Calendar.WEEK_OF_YEAR, startDate.get(Calendar.WEEK_OF_YEAR) + 1);
			startDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
			startDate.set(Calendar.HOUR_OF_DAY, hour);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
		}
		if (!underhandedReservation) {
			this.trainingDao.save(training);
		}
		
		return !underhandedReservation;
	}

	public boolean deleteTraining(int trainingId) {
		boolean deleted = false;
		if (this.exist(trainingId)) {
			Training training = trainingDao.findOne(trainingId);
			trainingDao.deleteById(trainingId);

			Calendar startDate = training.getStartDate(),
					endingDate = training.getEndingDate();
			int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK),
					hour = startDate.get(Calendar.HOUR_OF_DAY);

			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			
			while (startDate.before(endingDate)) {
				reserveDao.delete(reserveDao.findByCourtAndDate(training.getCourt(), startDate));
				startDate.set(Calendar.WEEK_OF_YEAR, startDate.getWeekYear() + 1);
				startDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
				startDate.set(Calendar.HOUR_OF_DAY, hour);
				startDate.set(Calendar.MINUTE, 0);
				startDate.set(Calendar.SECOND, 0);
			}
			deleted = true;	
		}
		
		return deleted;
	}

	public boolean exist(int trainingId) {
		return trainingDao.findOne(trainingId) != null;
	}

	public boolean deletePlayerTraining(int trainingId, String playerName) {
		Training training = trainingDao.findOne(trainingId);
		return training.removePlayer(userDao.findByUsernameOrEmail(playerName));
	}

	public boolean registerForTraining(String activeUser, int trainingId) {
		Training training = trainingDao.findOne(trainingId);
		return training.addPlayer(userDao.findByUsernameOrEmail(activeUser));
	}

	public List<TrainingWrapper> showTrainings() {
		List<Training> trainings = trainingDao.findAll();
		List<TrainingWrapper> trainingsWrapper = new ArrayList<>();
		for (int i = 0; i < trainings.size(); i++){
			List<String> players = new ArrayList<>();
			for (int j = 0; j < trainings.get(i).getPlayers().size(); j++){
				players.add(trainings.get(i).getPlayers().get(j).getUsername());
			}
			trainingsWrapper.add(new TrainingWrapper(trainings.get(i).getId(), trainings.get(i).getTrainer().getUsername(), players, trainings.get(i).getCourt().getId(), trainings.get(i).getStartDate(), trainings.get(i).getEndingDate()));
		}
		return trainingsWrapper;
	}

}
