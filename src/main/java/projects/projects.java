package projects;

import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectsService;



public class projects {
	private Scanner scanner = new Scanner(System.in);
	private ProjectsService projectsService = new ProjectsService();
	private Project curProject;
	
	private List<String> operations = List.of(
			
			"1) Add a project",
			"2) List projects",
			"3) select a project ",
			"4) Update project details",
			"5) Delete a project"
	);
	
	public static void main(String[] args) {
		new projects().displayMenu();
		
	}
	
		

	private void displayMenu() {
		// TODO Auto-generated method stub
		boolean done = false;
		
		while(!done) {
			int operation = getOperation();
			
			try {
		
			switch(operation) {
			case -1:
				done = exitMenu();
				break;
				
			case 1:
				createProject();
				break;
				
			case 2:
				listProject();
				break;
			
			case 3:
				selectProject();
				break;
			
			case 4:
				updateProject();
				break;
			
			case 5:
				deleteProject();
				break;
				
				default:
					System.out.println("\n" + operation + " is not valid. Try again.");
					
					
			
			}
			
		} 
			catch (Exception e) {
				System.out.println("\nError " + e.toString() + " Try again.");
		}
		}
	}
	
	private void updateProject() {
		if(Objects.isNull(curProject)) {
			System.out.println("\nPlease select a project.");
			return;
		}
		
		String projectName =
				getStringInput("Enter the project name [" + curProject.getProjectsName() + "]");
		
		BigDecimal estimatedHours =
				getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
		
		BigDecimal actualHours =
				getDecimalInput("Enter the actual hours + [" + curProject.getActualHours() + "]");
		
		Integer difficulty =
			getIntInput("Enter the project difficulty(1-5) [" + curProject.getDifficulty() + "]");
		
		String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
		
		Project project = new Project();
		
		project.setProject_id(curProject.getProject_id());
		project.setProjectsName(Objects.isNull(projectName) ? curProject.getProjectsName() : projectName);
		
		project.setEstimatedHours(
				Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);	

		project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
		project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
		project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
		projectsService.modifyProjectDetails(project);
		
		curProject = projectsService.fetchProjectById(curProject.getProject_id());
		
	}



	private void deleteProject() {
		listProject();
		
		Integer projectId = getIntInput("Enter the ID of the project to delete");
		
		
		
			projectsService.deleteProject(projectId);
			System.out.println("project " + projectId + " was deleted successfully.");
			if(Objects.nonNull(curProject) && curProject.getProject_id().equals(projectId)) {

			curProject = null;
		}
		
	}


	
	


	private void selectProject() {
		listProject();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		
		curProject = null;
		
		curProject = projectsService.fetchProjectById(projectId);


if(Objects.isNull(curProject)) {
	System.out.println("\n you are not working with a project.");
}

	}

	




	private void listProject() {
		List<Project> projects = projectsService.fetchALLproject();
		
		System.out.println("\nProject:");
		
		projects.forEach(project -> System.out.println("   " + project.getProject_id() + ": " + project.getProjectsName()));
		
		
	}



	private BigDecimal getDecimalInput(String prompt) {
		//Need to create to complete
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
	
		return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
			
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number. ");
		}
		}






	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");

		Project project = new Project();

		project.setProjectsName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectsService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	// end createProject
		
	}



	private boolean exitMenu() {
	System.out.println("\nExisiting the menu.");
		return true;
	}



	private int getOperation() {
	printOperations();
	
	Integer op = getIntInput("\nEnter an operation number (press Enter to quit");
	
	return Objects.isNull(op) ? -1 : op;
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		}	
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}





	
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String line = scanner.nextLine();
		
		return line.isBlank() ? null : line.trim();
	}
	




	private void printOperations() {
		System.out.println();
		System.out.println("Heres what you can do:");
		
		
		operations.forEach(op -> System.out.println("     " + op));
		
		if (Objects.isNull(curProject)) {
			System.out.println("\nYou do not have an active project.");
		} else {
			System.out.println("\n You are viewing: " + curProject);
		}
	}
	
}


