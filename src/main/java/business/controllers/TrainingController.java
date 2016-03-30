package business.controllers;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import business.wrapper.CreateTrainingWrapper;
import business.wrapper.TrainingWrapper;

@Controller
public class TrainingController {

	public void createTraining(User activeUser, CreateTrainingWrapper trainingWrapper) {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteTraining(int trainingId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean exist(int trainingId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletePlayerTraining(int trainingId, String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean registerForTraining(User activeUser, int trainingId) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<TrainingWrapper> showTrainings() {
		// TODO Auto-generated method stub
		return null;
	}

}
