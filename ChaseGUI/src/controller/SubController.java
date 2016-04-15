package controller;

import javafx.fxml.FXMLLoader;

public abstract class SubController {
	
	/**
	 * Sets the parent controller for current object 
	 * @param controller
	 */
	public void setParent(PrimaryController controller)
	{
		myController = controller;
	}

	/**
	 * Required override for all BasePane extensions to show each pane
	 */
	public abstract void showPane();
	
	protected PrimaryController myController;
	protected FXMLLoader loader;

}
