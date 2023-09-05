package projects.entity;

public class Step {
	private Integer steplId;
	private Integer projectlId;
	private Integer stepOrder;
	private String steptext;
	
	
	public Integer getSteplId() {
		return steplId;
	}
	public void setSteplId(Integer steplId) {
		this.steplId = steplId;
	}
	public Integer getProjectlId() {
		return projectlId;
	}
	public void setProjectlId(Integer projectlId) {
		this.projectlId = projectlId;
	}
	public Integer getStepOrder() {
		return stepOrder;
	}
	public void setStepOrder(Integer stepOrder) {
		this.stepOrder = stepOrder;
	}
	public String getSteptext() {
		return steptext;
	}
	public void setSteptext(String steptext) {
		this.steptext = steptext;
	}
	@Override
	public String toString() {
		return "ID=" + steplId + ", steptext=" + steptext;
	}
}
