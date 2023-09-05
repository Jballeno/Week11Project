package projects.entity;

import java.math.BigDecimal; 
import java.util.List;
import java.util.LinkedList;

public class Project {
	private Integer project_id;
	private String projectsName;
	private BigDecimal estimatedHours;
	private BigDecimal actualHours;
	private Integer difficulty;
	private String notes;

	private List<Materials> material = new LinkedList<>();
	private List<Step> steps = new LinkedList<>();
	private List<Category> categories = new LinkedList<>();

	@Override
	public String toString() {
		
	 String project = "";
	 
	 project += "\n    ID=" + project_id;
	 project += "\n    projectName" + projectsName;
	 project += "\n    notes=" + notes;
	 project += "\n    estimatedTime=" + estimatedHours;
	 project += "\n    difficulty=" + difficulty;
	 project += "\n    actualTime=" + actualHours; 
	 project += "\n    material:";

	 
	 for(Materials material : material) {
		 project += "\n     " + material;
			 
	 }
	 project += "\n    Steps:";
	 
	 for(Step step :steps) {
		 project += "\n     " + step;
		 
	 }
	 
	project += "\n    Categories:";
	 
	 for(Category category : categories) {
		 project += "\n     " + category;
		 
	 }
	 
	 return project;
		
	}

	public Integer getProject_id() {
		return project_id;
	}

	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}

	public String getProjectsName() {
		return projectsName;
	}

	public void setProjectsName(String projectsName) {
		this.projectsName = projectsName;
	}

	public BigDecimal getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public BigDecimal getActualHours() {
		return actualHours;
	}

	public void setActualHours(BigDecimal actualHours) {
		this.actualHours = actualHours;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Materials> getMaterial() {
		return material;
	}

	public void setMaterial(List<Materials> material) {
		this.material = material;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}





		
	}

