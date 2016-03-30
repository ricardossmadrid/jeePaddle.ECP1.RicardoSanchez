package api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.CreateTrainingWrapper;
import business.wrapper.TrainingWrapper;

public class TrainingResourceFunctionalTesting {

    RestService restService = new RestService();
    
    private String tokenTrainer;
    private String tokenPlayer;
    
    @Before
    public void initUserTokensAndCourts() {
    	restService.createCourt("5");
    	tokenTrainer = restService.loginTrainer();
    	tokenPlayer = restService.registerAndLoginPlayer();
    }

    @Test
    public void testCreateAndDeleteTraining() {
    	CreateTrainingWrapper createTrainingWrapper = createMockTrainingWrapper(5, Calendar.WEDNESDAY);
		
		List<TrainingWrapper> trainingsList = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "").clazz(TrainingWrapper[].class).get().build());
		int numberOfTrainings = trainingsList.size();
		
		new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(createTrainingWrapper).post().build();
		try {
			new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "").body(createTrainingWrapper).post().build();
		} catch (HttpClientErrorException httpError) {
			assertEquals(httpError.getStatusCode(), HttpStatus.FORBIDDEN);
		}
		trainingsList = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "").clazz(TrainingWrapper[].class).get().build());
		int trainingId = trainingsList.get(trainingsList.size() - 1).getId();
		assertEquals(numberOfTrainings + 1, trainingsList.size());

		new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(trainingId).delete().build();
		try {
			new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "").body(trainingId).delete().build();
		} catch (HttpClientErrorException httpError) {
			assertEquals(httpError.getStatusCode(), HttpStatus.FORBIDDEN);
		}
		trainingsList = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "").clazz(TrainingWrapper[].class).get().build());
		assertEquals(numberOfTrainings, trainingsList.size());
    }

	private CreateTrainingWrapper createMockTrainingWrapper(int courtId, int dayOfWeek) {

    	Calendar startDate = Calendar.getInstance();
    	startDate.set(Calendar.WEEK_OF_YEAR, startDate.get(Calendar.WEEK_OF_YEAR) + 1);
    	startDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    	startDate.set(Calendar.HOUR_OF_DAY, 11);
    	startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		
		Calendar endingDate = (Calendar) startDate.clone();
		endingDate.set(Calendar.WEEK_OF_YEAR, startDate.get(Calendar.WEEK_OF_YEAR) + 4);
		endingDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		endingDate.set(Calendar.HOUR_OF_DAY, 12);
		endingDate.set(Calendar.MINUTE, 0);
		endingDate.set(Calendar.SECOND, 0);
		
		return new CreateTrainingWrapper(courtId, startDate, endingDate);
	}
	
	@After
    public void deleteAll() {
        restService.deleteAll();
    }
    
}
