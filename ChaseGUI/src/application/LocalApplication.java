package application;


import javafx.application.Application;
import javafx.stage.Stage;
import controller.LoginPanelController;
import controller.MainPanelController;
import controller.PrimaryController;


/**
 * 
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This is the entry for the program.
 *
 */
public class LocalApplication extends Application{

	/**
	 * Override start fuction to start the app
	 */
	@Override
	public void start(Stage primaryStage){
		
		PrimaryController mainController = new PrimaryController();
		mainController.addPane("login", new LoginPanelController());
		mainController.addPane("main", new MainPanelController());
		mainController.setPane("login");

	}
	
	 public static void main(String[] args) {
	        Application.launch(LocalApplication.class,args);
	    }

	
}
