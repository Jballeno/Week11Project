package projects.service;

	

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectsDao;
import projects.entity.Project;
import projects.exception.DbException;

	public class ProjectsService {
		
	private ProjectsDao projectsDAO = new ProjectsDao();

	public Project fetchProjectById(Integer projectId) {
		return projectsDAO.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with project ID=" + projectId + " does not exist."));
	}
	

		
		

	
		

		public List<Project> fetchALLproject() {
			
			return projectsDAO.fetchAllProjects();
		}
			public void deleteProject(Integer projectId) {
			if(!projectsDAO.deleteProject(projectId)) {
				throw new DbException("Project with ID =" + projectId + "does not exist");
			}
			
		}

			public void modifyProjectDetails(Project project) {
				if(!projectsDAO.modifyProjectDetails(project)) {
					throw new DbException("Project with ID=" + project.getProject_id() + " does not exist");			}
				
			}
			public Project addProject(Project project) {
				return projectsDAO.insertProject(project);
			}

			}




