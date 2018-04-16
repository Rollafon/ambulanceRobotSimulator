package ch.makery.address.view;

import ch.makery.address.Main;

public class MapOverviewController {
	// Reference to the main application.
	private Main main;
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(Main main) {
		this.main = main;
	}
}
