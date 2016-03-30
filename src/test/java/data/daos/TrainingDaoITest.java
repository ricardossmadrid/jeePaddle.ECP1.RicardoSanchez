package data.daos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Training;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {
	
	@Autowired
	private DaosService daosService;
	
	@Autowired
	private TrainingDao trainingDao;
	
	@Test
	public void testAddRemovePlayerTraining() {
		
		Training training = trainingDao.findAll().get(0);
		assertEquals(training.getPlayers().size(), 0);

		User u1 = (User) daosService.getMap().get("u1");
		training.addPlayer(u1);
		trainingDao.save(training);
		
		training = trainingDao.findAll().get(0);
		assertEquals(training.getPlayers().size(), 1);
		
		training.removePlayer(u1);
		trainingDao.save(training);
		
		training = trainingDao.findAll().get(0);
		assertEquals(training.getPlayers().size(), 0);
	}
	
	@Test
	public void testDeleteTraining() {
		long numberOfTrainings = trainingDao.count();
		Training training = trainingDao.findAll().get(0);
		trainingDao.deleteById(training.getId());
		assertEquals(numberOfTrainings - 1, trainingDao.count());
		trainingDao.save(training);
	}

}
