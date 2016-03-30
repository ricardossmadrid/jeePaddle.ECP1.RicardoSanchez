package business.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.FullTrainingException;
import business.api.exceptions.InvalidCourtReserveException;
import business.api.exceptions.InvalidDateException;
import business.api.exceptions.NotFoundCourtIdException;
import business.api.exceptions.NotFoundPlayerInTrainingException;
import business.api.exceptions.NotFoundTrainingIdException;
import business.api.exceptions.NotFoundUserNameException;
import business.controllers.CourtController;
import business.controllers.TrainingController;
import business.controllers.UserController;
import business.wrapper.CreateTrainingWrapper;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {

	private TrainingController trainingController;
	
	@Autowired
    public void setTrainingController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }
	
	private CourtController courtController;
	
	@Autowired
    public void setCourtController(CourtController courtController) {
        this.courtController = courtController;
    }
	
	private UserController userController;
	
	@Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }
	
	private void validateDay(Calendar day) throws InvalidDateException {
        Calendar calendarDay = Calendar.getInstance();
        calendarDay.add(Calendar.DAY_OF_YEAR, -1);
        if (calendarDay.after(day)) {
            throw new InvalidDateException("La fecha no puede ser un día pasado");
        }
    }
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('TRAINER')")
    public void createTraining(@AuthenticationPrincipal User activeUser, @RequestBody CreateTrainingWrapper trainingWrapper) throws NotFoundCourtIdException, InvalidDateException, InvalidCourtReserveException {
		if (!courtController.exist(trainingWrapper.getCourtId())) {
			throw new NotFoundCourtIdException("No existe la pista: " + trainingWrapper.getCourtId());
		}
		if (trainingWrapper.getStartDate().after(trainingWrapper.getEndingDate())) {
			throw new InvalidDateException("La fecha de inicio: " + trainingWrapper.getStartDate() + " es más tarde que la de fin: " + trainingWrapper.getStartDate());
		}
		validateDay(trainingWrapper.getStartDate());
		if (!trainingController.createTraining(activeUser.getUsername(), trainingWrapper)) {
			throw new InvalidCourtReserveException("La pista " + trainingWrapper.getCourtId() + " está ya reservada para alguna de las fechas seleccionadas");
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('TRAINER')")
	public void deleteTraining(@RequestBody(required = true) int trainingId) throws NotFoundTrainingIdException {
		if (!trainingController.deleteTraining(trainingId)){
			throw new NotFoundTrainingIdException("No se ha encontrado el entrenamiento: " + trainingId);
		}
	}
	
	@RequestMapping(value = Uris.ID + Uris.USERS, method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('TRAINER')")
	public void deletePlayerTraining(@RequestBody(required = true) String playerName, @PathVariable int trainingId) throws NotFoundUserNameException, NotFoundTrainingIdException, NotFoundPlayerInTrainingException {
		if (!userController.exist(playerName)) {
			throw new NotFoundUserNameException("Usuario: " + playerName + " no encontrado");
		}
		if (!trainingController.exist(trainingId)) {
			throw new NotFoundTrainingIdException("No se ha encontrado el entrenamiento: " + trainingId);
		}
		if (!trainingController.deletePlayerTraining(trainingId, playerName)) {
			throw new NotFoundPlayerInTrainingException("Usuario: " + playerName + " no existe en el entrenamiento: " + trainingId);
		}
	}
	
	@RequestMapping(value = Uris.ID + Uris.USERS, method = RequestMethod.POST)
	@PreAuthorize("hasRole('PLAYER')")
	public void registerForTraining(@AuthenticationPrincipal User activeUser, @PathVariable int trainingId) throws NotFoundTrainingIdException, FullTrainingException {
		if (!trainingController.exist(trainingId)) {
			throw new NotFoundTrainingIdException("No se ha encontrado el entrenamiento: " + trainingId);
		}
		if (!trainingController.registerForTraining(activeUser.getUsername(), trainingId)) {
			throw new FullTrainingException("No pueden entrar más jugadores en el entreniamiento: " + trainingId);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('PLAYER')")
	public List<TrainingWrapper> showTrainings() {
		return trainingController.showTrainings();
	}

}
