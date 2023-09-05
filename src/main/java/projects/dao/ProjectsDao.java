package projects.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import projects.entity.Category;
import java.util.Objects;
import java.util.Optional;
import projects.entity.Materials;
import projects.entity.Project;
import projects.entity.Step;
import projects.exception.DbException;
import provided.util.DaoBase;

/**
 * @author Jose
 *
 */





public class ProjectsDao extends DaoBase {
	
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "project";
	private static final String STEPS_TABLE = "step";
	private static final String CATEGORY_TABLE = "category";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	
	public List<Project> fetchAllProjects() {
		String sql = "SELECT * FROM " + PROJECT_TABLE + " ORDER BY project_name";
		
		try(Connection conn = DbConnection.getConnection()) {
			startTransaction(conn);
			
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			try(ResultSet rs = stmt.executeQuery()) {
				List<Project> projects = new LinkedList<>();
				
			while(rs.next()) {
				projects.add(extract(rs, Project.class));
				
			}
			return projects;
			}
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
		}
		catch (SQLException e) {
			throw new DbException(e);		
			}
	}
	
	public Optional<Project> fetchProjectById(Integer projectId) {
		String sql = "SELECT * FROM " + PROJECT_TABLE + " WHERE project_id = ?";
		
		try(Connection conn = DbConnection.getConnection()) {
			startTransaction(conn);
		
		try {
			Project project = null;
	
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				setParameter(stmt, 1, projectId, Integer.class);
				
				try(ResultSet rs = stmt.executeQuery()) {
					if(rs.next()) {
						project = extract(rs, Project.class);
					}
				}
			}
		
			if(Objects.nonNull(project)) {
				project.getMaterial().addAll(fetchMaterialsForProject(conn, projectId));
				project.getSteps().addAll(fetchStepsForProject(conn, projectId));
				project.getCategories().addAll(fetchCategoriesForProject(conn, projectId));
			
			}
			commitTransaction(conn);
			return Optional.ofNullable(project);
			
		}
			catch(Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		
		} 
		catch (SQLException e) {
			throw new DbException(e);
		}
	}







	private List<Category> fetchCategoriesForProject(Connection conn, Integer projectId) throws SQLException {
		
		String sql = ""
				
				+ "SELECT c.* FROM " + CATEGORY_TABLE + " c "
				+ "JOIN " + PROJECT_CATEGORY_TABLE + " pc USING (category_id) "
				+ "WHERE project_id = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParameter(stmt, 1, projectId, Integer.class);
			
			try(ResultSet rs = stmt.executeQuery()) {
				List<Category> categories= new LinkedList<>();
				
				while(rs.next()) {
					categories.add(extract(rs, Category.class));
				
				}
				
				return categories;
			}
		}
		
	}

	private List<Step> fetchStepsForProject(Connection conn, Integer projectId)
		throws SQLException {
			String sql = "SELECT * FROM " + STEPS_TABLE + " WHERE project_id = ?";
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				setParameter(stmt, 1, projectId, Integer.class);
				
				try(ResultSet rs = stmt.executeQuery()) {
					List<Step> steps = new LinkedList<>();
					
					while(rs.next()) {
						steps.add(extract(rs, Step.class));
						
					}
					
					return steps;
					
				}
			}
			
			
			
		}
		
		
		
		


	private List<Materials> fetchMaterialsForProject(Connection conn, Integer projectId)
	throws SQLException {
		String sql = "SELECT * FROM " + MATERIAL_TABLE + " WHERE project_id = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParameter(stmt, 1, projectId, Integer.class);
			
			try(ResultSet rs = stmt.executeQuery()) {
				List<Materials> materials = new LinkedList<>();
				
				while(rs.next()) {
					materials.add(extract(rs, Materials.class));
				}
				
				return materials;
			}
		}
	}

	public Project insertProject(Project project) {
		String sql = ""
			+ "INSERT INTO " + PROJECT_TABLE + " " 
			+ "(project_name, notes, difficulty, estimated_hours, actual_hours) "
			+ "VALUES"
			+ "(?, ?, ?, ?, ?)";
		
		try(Connection conn = DbConnection.getConnection()) {
			startTransaction(conn);
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParameter(stmt, 1, project.getProjectsName(), String.class);
			setParameter(stmt, 2, project.getNotes(), String.class);
			setParameter(stmt, 3, project.getDifficulty(), Integer.class);
			setParameter(stmt, 4, project.getEstimatedHours(), BigDecimal.class);
			setParameter(stmt, 5, project.getActualHours(), BigDecimal.class);
			
			stmt.executeUpdate();
			Integer projectId = getLastInsertId(conn, PROJECT_TABLE);
			
			commitTransaction(conn);
			
			project.setProject_id(projectId);
			return project;
			
			
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
		} catch (Exception e) {
			throw new DbException(e);
			
		}
	}
	




public void executeBatch(List<String> sqlBatch) {
	try(Connection conn= DbConnection.getConnection()) {
		startTransaction(conn);
		
		try(Statement stmt = conn.createStatement()) {
			for(String sql : sqlBatch) {
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			commitTransaction(conn);
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
	
	} catch (SQLException e) {
	throw new DbException(e);
	
		
	}
	
}

public boolean deleteProject(Integer projectId) {
	String sql = "DELETE FROM " + PROJECT_TABLE + " WHERE project_id = ?";
	
	try(Connection conn = DbConnection.getConnection()) {
		startTransaction(conn);
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParameter(stmt, 1, projectId, Integer.class);
			
			boolean deleted = stmt.executeUpdate() == 1;
			
			commitTransaction(conn);
			return deleted;
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
	}
	catch(SQLException e) {
		throw new DbException(e);
	}
	
}

public boolean modifyProjectDetails(Project project) {
String sql = ""
	+ "UPDATE " + PROJECT_TABLE + " SET "
	+ "project_name = ?, "
	+ "estimated_hours = ?, "
	+ "actual_hours = ?, "
	+ "difficulty = ? "
	+ "notes = ? "
	+ "WHERE project_id = ?";
	
	try(Connection conn = DbConnection.getConnection()) {
		startTransaction(conn);
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParameter(stmt, 1 , project.getProjectsName(), String.class);
			setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
			setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
			setParameter(stmt, 4, project.getDifficulty(), Integer.class);
			setParameter(stmt, 5, project.getNotes(), String.class);
			setParameter(stmt, 6, project.getProject_id(), Integer.class);
			
			boolean modified = stmt.executeUpdate() == 1;
			commitTransaction(conn);
			
			return modified;
			
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
	}
	catch(SQLException e) {
		throw new DbException(e);
	}
	
	
}




}
