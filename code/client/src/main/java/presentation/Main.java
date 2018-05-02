package presentation;

import java.io.IOException;

import client.Client;

public class Main {

	public static void main(String[] args) {
		ClientUI ui = new ClientUI();
		ui.setVisible(true);
		try {
			Client client = new Client(ui);
			client.openConnection();
		} catch (IOException e) {
			ui.displayError("Unable to connect to server.");
			ui.disableButtons();
		}
	}
}
