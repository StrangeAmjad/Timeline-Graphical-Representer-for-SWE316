package assignment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class JavaTimeline extends Pane {
    private ArrayList<Double> pos = new ArrayList();
    private ArrayList<Double> posDays = new ArrayList();
    private HashMap<Double,ArrayList<Double>> map = new HashMap();
    private HashMap<Integer,Double> mapMonths = new HashMap();
	private LocalDate start;
	private LocalDate end;
	private Project dataSet;
	final private int offset=1;
    final private int minSpaceBetweenDays=12;
    private int axisLength;
    @SuppressWarnings({ "deprecation", "deprecation" })
	public JavaTimeline(int width, Project dataSet) {	
    	this.dataSet=dataSet;
        this.axisLength=width;
        this.setTranslateX(2);
        this.setTranslateY(200);
        int monthsToDraw = calcMonths();
        double distanceBetweenMonths = (axisLength / (monthsToDraw+1))+minSpaceBetweenDays*31  ; 
        double distanceBetweenDays= (distanceBetweenMonths/31);        
        drawTimeLine(monthsToDraw,distanceBetweenMonths,distanceBetweenDays);
        drawDuration();
        drawNotations();

    }
    private int calcMonths() {
    	  start = LocalDate.of(dataSet.getStages().get(0).getDate().getYear()+1900,
    			dataSet.getStages().get(0).getDate().getMonth()+1,
    			dataSet.getStages().get(0).getDate().getDate());
    	  end = LocalDate.of(dataSet.getStages().get(dataSet.getStages().size()-1).getDate().getYear()+1900,
    				dataSet.getStages().get(dataSet.getStages().size()-1).getDate().getMonth()+1,
    				dataSet.getStages().get(dataSet.getStages().size()-1).getDate().getDate());
    	  int monthsToDraw=1;
          int startMonth;
          if(start.getYear()==end.getYear()) {
          	monthsToDraw =end.getMonth().getValue()-start.getMonth().getValue();
          }
          else {
          	startMonth = start.getMonthValue();

          	while(startMonth<12) {
          		startMonth++;
          		monthsToDraw=monthsToDraw+1;
          	}
          	for(int i=0; i<end.getMonthValue();i++) {
          		monthsToDraw++;
          	}
          }
          return monthsToDraw;
    }
    private void drawTimeLine(int monthsToDraw,double distanceBetweenMonths,double distanceBetweenDays) {
        Line horizontalAxis = new Line(offset, offset * 3, (axisLength+(monthsToDraw+1)*(minSpaceBetweenDays*31)), offset * 3);
        horizontalAxis.setStroke(Color.LIGHTGREY);
        horizontalAxis.setStrokeWidth(5);
        this.getChildren().add(horizontalAxis);
        int year = start.getYear();
       
        for (int i = 0; i <= monthsToDraw; i++) {
            int currentMonth = dataSet.getStages().get(0).getDate().getMonth()+1+ i;
            int monthNoSub= dataSet.getStages().get(0).getDate().getMonth()+1+ i;
            if(currentMonth>12) {
            	currentMonth=currentMonth-12;	
            }

            double yearLineX = (offset * 3) + (i * distanceBetweenMonths);
            pos.add(yearLineX);
            Line yearLine = new Line(yearLineX, offset * 2, yearLineX, offset * 4);
            yearLine.setStroke(Color.BLACK);
            yearLine.setStrokeWidth(3);
            for(int j=0; j<31; j++) {
                double dayLineX = yearLineX +(offset * 3) + (j * distanceBetweenDays);
                posDays.add(dayLineX);
                Line dayLine = new Line(dayLineX, offset * 2, dayLineX, offset * 4);
                dayLine.setStroke(Color.YELLOW);
                dayLine.setStrokeWidth(2);
                this.getChildren().add(dayLine);
            }
            mapMonths.put(monthNoSub, yearLineX);
            map.put(yearLineX, posDays);
            
            this.getChildren().add(yearLine);
            
                Label monthLabel = new Label(String.valueOf(currentMonth)+"-"+year+"");
                monthLabel.setLayoutX(yearLineX-5);
                monthLabel.setLayoutY(offset * 5);
                monthLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 8px;");
                this.getChildren().add(monthLabel);

            if(currentMonth==12) {
            	year++;
            }
        }
    }
    private void drawNotations() {
    	Stage prevStage =null;
        int count=1;
        int month=dataSet.getStages().get(0).getDate().getMonth()+1;
        int years=0;
        boolean awarded = false;
        int reWorkAfter = 0;
        int reWorkBefore = 0;
        for(Stage stage: dataSet.getStages()) {
           String notation = stage.getStage()+"";
          
           if (prevStage !=null) {
        	  if(stage.getDate().getYear() != prevStage.getDate().getYear()) {
        		  years++;
        	  }
        	  
        	  if(stage.getDate().getMonth() != prevStage.getDate().getMonth() && stage.getDate().getYear() == prevStage.getDate().getYear()) {
        		  month+=stage.getDate().getMonth()-prevStage.getDate().getMonth();
        	  }
        	 else if(stage.getDate().getMonth() == prevStage.getDate().getMonth() && stage.getDate().getYear() == prevStage.getDate().getYear()) {
        		  if(prevStage.getDate().getDate()==stage.getDate().getDate()) {
        			  count++;
        		  }
        		  else {
        			  count=1;
        		  }
        	  }

        	  else if(years>0) {
        	  month+=12-prevStage.getDate().getMonth() +(stage.getDate().getMonth());
        	  }

              else {
        			  
        		  }
           }
           if(prevStage!= null && (stage.getDate().getMonth() != prevStage.getDate().getMonth() ||stage.getDate().getYear() != prevStage.getDate().getYear()) ) {
        	   count=1;
           }
           
            if (notation != null && !notation.isEmpty()) {
                Line notationLine = new Line(mapMonths.get(month)+map.get(mapMonths.get(month)).get(stage.getDate().getDate()-1), 
                		offset * 5-3, 
                		mapMonths.get(month)+map.get(mapMonths.get(month)).get(stage.getDate().getDate()-1), 
                		offset * 9-3);
                if(stage.getStage() >= 5) {
                	awarded = true;
                }
                if(stage.getPrevStage()>stage.getStage()) {	
               if(!awarded) {
               	reWorkBefore++;
               	
                }
                else {
                	reWorkAfter++;
                }
                notationLine.setStroke(Color.RED);
                }
                else {
                notationLine.setStroke(Color.GREEN);
                }
                notationLine.setStrokeWidth(3);
                Label stageNum = new Label(stage.getStage()+""); 
                stageNum.setLayoutX(mapMonths.get(month)+map.get(mapMonths.get(month)).get(stage.getDate().getDate()-1));
                stageNum.setLayoutY(-10*count);
                stageNum.setTranslateY(-2.2);
                stageNum.setTranslateX(0.5);
                stageNum.setStyle("-fx-font-size: 8px; -fx-text-alignment: left; ");
                notationLine.setLayoutY(-10*count);
                notationLine.setTranslateX(-3);
                
                this.getChildren().add(notationLine);
                this.getChildren().add(stageNum);

                Label notationLabel = new Label(stage.getDate().getDate()+"/"+(stage.getDate().getMonth()+1)+"/"+(stage.getDate().getYear()+1900));
                notationLabel.setLayoutX(mapMonths.get(month)+map.get(mapMonths.get(month)).get(stage.getDate().getDate()-1));
                notationLabel.setLayoutY(20);
                notationLabel.setTranslateX(2);
                //notationLabel.setPrefWidth(notationLabelWidth);
                //notationLabel.setPrefHeight(40);
                notationLabel.getTransforms().add(new Rotate(90, 0,0, 0, Rotate.Z_AXIS));
                
                notationLabel.setStyle("-fx-font-size: 10px; -fx-text-alignment: left;");
               
                if(prevStage!=null && prevStage.getDate().getMonth()==stage.getDate().getMonth()) {
                	if(prevStage.getDate().getDate()==stage.getDate().getDate()) {
                		notationLabel.setDisable(true);
                		continue;
                	}
                	notationLabel.setLayoutX(mapMonths.get(month)+map.get(mapMonths.get(month)).get(stage.getDate().getDate()-1));
                	
                }
                this.getChildren().add(notationLabel);
                prevStage=stage;               
            } 
        }
        Label reworks = new Label("Reworks after award: "+reWorkAfter+"\nReworks before award: "+reWorkBefore);
        reworks.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        reworks.setTranslateY(-200);
        this.getChildren().add(reworks);
    }
    private void drawDuration() {
    	long difference = dataSet.getStages().get(dataSet.getStages().size()-1).getDate().getTime()-dataSet.getStages().get(0).getDate().getTime();
    	long differenceInDays = (difference/ (1000 * 60 * 60 * 24)) % 365;
    	Label duration = new Label("Duration: "+differenceInDays+" days");
    	duration.setTranslateY(-150);
    	duration.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
    	
    	Stage fStage= dataSet.getStages().get(0);
    	Stage lStage = dataSet.getStages().get(dataSet.getStages().size()-1);
    	int fMonth = fStage.getDate().getMonth()+1;
    	int lMonth = fStage.getDate().getMonth()+1+calcMonths();
    	double lCord;
    	double fCord = mapMonths.get(fMonth)+map.get(mapMonths.get(fMonth)).get(fStage.getDate().getDate()-1);
    	if(fStage.getDate().getYear()!= lStage.getDate().getYear()) {
    		lCord = mapMonths.get(lMonth-1)+map.get(mapMonths.get(lMonth-1)).get(lStage.getDate().getDate()-1);;
    	}
    	else {
    		 lCord = mapMonths.get(lMonth)+map.get(mapMonths.get(lMonth)).get(lStage.getDate().getDate()-1);;
    	}
    	
    	duration.setTranslateX(mapMonths.get(fMonth)+map.get(mapMonths.get(fMonth)).get(fStage.getDate().getDate()-1));
    	Line line= new Line(fCord,offset * 5-3,lCord,offset * 5-3); 
    	line.setTranslateY(-100);
    	line.setStroke(Color.RED);
    	this.getChildren().add(line);
    	this.getChildren().add(duration);
    }
}