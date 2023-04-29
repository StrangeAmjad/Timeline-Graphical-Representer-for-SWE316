package assignment;

import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class application extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws IOException {
		Project[] projects = ProjectCollection.start("Projects.xls"
				,"Stages.xls"
				,"Stages_Detailed.xls");

		TableView<Project> tv= new TableView<Project>();
		TableColumn numberCol = new TableColumn("#");
		TableColumn<Project, String> column2 = new TableColumn<>("Project-ID");
		column2.setCellValueFactory(new PropertyValueFactory<>("projectID"));
		TableColumn<Project, String> column3 = new TableColumn<>("Stage");
		column3.setCellValueFactory(new PropertyValueFactory<>("currentStage"));
		tv.getColumns().add(numberCol);
		tv.getColumns().add(column2);
		tv.getColumns().add(column3);
		tv.getItems().addAll(projects);

		Pane tml = new JavaTimeline(600,projects[0]);
		ScrollPane sp = new ScrollPane(tml);	
		sp.pannableProperty().set(true);
		sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);	
		sp.setMaxSize(1000,400);
		sp.setTranslateY(300);
		TextField pNum = new TextField(projects[0].getProjectID());
		HBox hbox = new HBox(tv,pNum,sp);
		hbox.setSpacing(50);		
		Scene scene = new Scene(hbox);

		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);

		primaryStage.show();

		numberCol.setCellFactory( new Callback<TableColumn, TableCell>()
		{
		    @Override
		    public TableCell call( TableColumn p )
		    {
		        return new TableCell()
		        {
		            @Override
		            public void updateItem( Object item, boolean empty )
		            {
		                super.updateItem( item, empty );
		                setGraphic( null );
		                setText( empty ? null : getIndex() + 1 + "" );
		            }
		        };
		    }
		});


        tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				Project x= (Project)arg0.getValue();
				hbox.getChildren().remove(2);
				sp.setContent(new JavaTimeline(600,x));
				hbox.getChildren().add(2,sp);
				pNum.setText(x.getProjectID());
			}
       	
        });


}


}
