package projects.service;

	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.LinkedList;
	import java.util.List;
	import java.util.NoSuchElementException;
	import projects.dao.ProjectsDao;
	import projects.entity.Project;
	import projects.exception.DbException;

	public class ProjectsService {
		
	private static final String SCHEMA_FILE = "projects_schema.sql";
	private static final String DATA_FILE = "projects_data.sql";
		
	private ProjectsDao ProjectsDAO = new ProjectsDao();

	public Project fetchProjectById(Integer projectId) {
		return ProjectsDAO.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with project ID=" + projectId + " does not exist."));
	}
		
		public void createAndPopulateTables() {
			loadFromFile(SCHEMA_FILE);
			loadFromFile (DATA_FILE);
			
			
			
		}


		private void loadFromFile(String fileName) {
			String content = readFileContent (fileName);
			List<String> sqlStatements = convertContentToSqlStatements(content);
			
			
			
			
		}

		private List<String> convertContentToSqlStatements(String content) {
			content = removeComments(content);
			content = replaceWhitespaceSequencesWithSingleSpace(content);
			
			return extractLinesFromContent(content);
			
		}

		private List<String> extractLinesFromContent(String content) {
			List<String> lines = new LinkedList<>();
			
			while(!content.isEmpty()) {
				int semicolon = content.indexOf(";");
				if (semicolon == -1) {
					if(!content.isBlank()) {
						lines.add(content);
						
					}
					content = "";
				}
				else {
					lines.add(content.substring(0 , semicolon).trim());
					content = content.substring(semicolon + 1);
				}
			}
			return lines;

			
		}
		
		

		private String replaceWhitespaceSequencesWithSingleSpace(String content) {
			
			return content.replaceAll("\\s+", " ");
		}

		private String removeComments(String content) {
			StringBuilder builder = new StringBuilder(content);
			int commentPos = 0;
			
			while((commentPos = builder.indexOf("-- ", commentPos)) != -1) {
				int eolPos = builder.indexOf("\n", commentPos +1);
				
				if(eolPos == -1) {
					builder.replace(commentPos, builder.length(), "");
					
				}
				else {
					builder.replace(commentPos, eolPos + 1, "");
				}
				
			}
			
			return builder.toString();
		}
		


		private String readFileContent(String fileName) {
			try {
			
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
			return Files.readString(path);
			
		} catch(Exception e) {
			
			throw new DbException(e);
		}
		
		}
		public static void main(String[] args) {
			new ProjectsService().createAndPopulateTables();
		}

		public Project addProject(Project project) {
		
			return ProjectsDAO.insertProject(project);
		}


		public List<Project> fetchALLproject() {
			
			return ProjectsDAO.fetchAllProjects();
		}
			public void deleteProject(Integer projectId) {
			if(!ProjectsDAO.deleteProject(projectId)) {
				throw new DbException("Project with ID =" + projectId + "does not exist");
			}
			
		}

			public void modifyProjectDetails(Project project) {
				if(!ProjectsDAO.modifyProjectDetails(project)) {
					throw new DbException("Project eith ID=" + project.getProject_id() + " does not exist");			}
				
			}

			}




