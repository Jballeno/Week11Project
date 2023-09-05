package projects.entity;

import java.math.BigDecimal;

import provided.entity.EntityBase;

public class Materials extends EntityBase {
	private Integer materialId;
	private Integer projectID;
	private String materialName;
	private Integer numRequired;
	private BigDecimal cost;
	
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		b.append("ID=").append(materialId).append(":  ");
		b.append(toFraction(cost));
		
				
		return b.toString();
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public Integer getProjectID() {
		return projectID;
	}
	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Integer getNumRequired() {
		return numRequired;
	}
	public void setNumRequired(Integer numRequired) {
		this.numRequired = numRequired;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	

}


