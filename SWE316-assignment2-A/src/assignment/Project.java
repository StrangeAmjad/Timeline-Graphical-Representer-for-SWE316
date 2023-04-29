package assignment;

import java.util.ArrayList;
public class Project {
	private String projectID;
	private ArrayList<Stage> stages;
	private int currentStage;
	private String nodeID;
	
	
	public Project(String nodeID,String projectID,int stage){
	this.projectID=projectID;
	this.nodeID=nodeID;
	this.currentStage=stage;

	}
	public Project() {
		
	}
	public String getProjectID(){
		return projectID;
	}
	public ArrayList<Stage> getStages(){
		return stages;
	}
	public int getCurrentStage(){
		return currentStage;
	}
	public String getNodeID() {
		return nodeID;
	}
	public void setStages(ArrayList<Stage> stages){
		this.stages = stages;
	}
	
}
