package assignment;

import java.util.Date;
public class Stage implements Comparable<Stage> {
private int prevStage;
private int stageNum;
private Date date;
private String obVal;


public Stage(String obVal,int stageNum,int prevStage,Date date){
	this.obVal=obVal;
	this.stageNum=stageNum;
	this.prevStage=prevStage;
	this.date=date;
}
public String getObVal() {
	return obVal;
}

public int getStage() {
	return stageNum;
}
public int getPrevStage() {
	return prevStage;
}
public Date getDate() {
	return date;
}
@Override
public int compareTo(Stage c) {
	
	if(this.getDate().compareTo(c.getDate()) >0) {
		return 1;
	}
	if(this.getDate().compareTo(c.getDate()) ==0) {
		return 0;
	}	
	return -1;
}









}
