package controller;

import javafx.fxml.FXMLLoader;

/**
 * SubController abstrac class
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This is meant to act as the base class for the GUI controllers.
 *
 */
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
	 * Required override for all SubControllers extensions to show each pane
	 */
	public abstract void showPane();
	
	protected PrimaryController myController;
	protected FXMLLoader loader;

}
