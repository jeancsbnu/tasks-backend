package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@InjectMocks
	private TaskController controller;

	@Mock
	private TaskRepo taskRepo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testNaoDeveSalvarTarefaSemDescricao(){
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		
		try {
			controller.save(task);
			
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void testDeveSalvarTarefaSemData() {
		Task task = new Task();
		task.setTask("descrição");
			
		try {
			controller.save(task);
			
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void testNaoDeveSalvarTarefaComDataPassada() {
		Task task = new Task();
		task.setTask("Descrição");
		task.setDueDate(LocalDate.of(2010, 01, 01));
			
		try {
			controller.save(task);
			
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void testDeveSalvarTarefaComSucesso() throws ValidationException {
		Task task = new Task();
		task.setTask("Descrição");
		task.setDueDate(LocalDate.now());
			
		controller.save(task);
		
		Mockito.verify(taskRepo).save(task);
	}
}
