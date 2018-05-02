package client;

import static client.ServerMessage.*;
import static presentation.UIMessage.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import client.entity.Article;
import client.util.ArticleAdapter;
import client.util.WriterAdapter;
import presentation.ClientUI;
import presentation.UIMessage;

public class Client implements Runnable {
	ClientUI clientUI; 

	final public static int DEFAULT_PORT = 5555;
	
	private Socket clientSocket;
	private ObjectOutputStream outputFromClient;
	private ObjectInputStream inputFromServer;
	private Thread clientReader;
	private boolean readyToStop= false;
	private String host;
	private int port;

	public Client(String host, int port, ClientUI clientUI) throws IOException {
		this.host = host;
		this.port = port;
		this.clientUI = clientUI;
		clientUI.setClient(this);
	}
	
	public Client(ClientUI ui) throws IOException {
		this.host = "localhost";
		this.port = DEFAULT_PORT;
		this.clientUI = ui;
		clientUI.setClient(this);
	}

	public boolean isConnected() {
		return clientReader!=null && clientReader.isAlive();
	}

	public void openConnection() throws IOException {
		if(isConnected()) {
			return;
		}
		try {
			clientSocket= new Socket(host, port);
			outputFromClient = new ObjectOutputStream(clientSocket.getOutputStream());
			inputFromServer = new ObjectInputStream(clientSocket.getInputStream());
		}
		catch (IOException ex) {
			try {
				closeAll();
			}
			catch (Exception exc) {}
			throw ex; 
		}
		clientReader = new Thread(this); 
		readyToStop = false;
		clientReader.start();  
	}

	public void handleMessageFromServer(ServerMessage messageType, String content) {
		if (messageType.equals(SERVER_CLOSED)) {
			clientUI.displayServerMessage(content);
			return;
		}
		if (messageType.equals(SERVER_STOPPED_LISTENING)) {
			clientUI.displayServerMessage(content);
			return;
		}
		if (messageType.equals(SERVER_STARTED_LISTENING)) {
			clientUI.displayServerMessage(content);
			return;
		}
		if(messageType.equals(GET_ARTICLE)) {
			ArticleAdapter adapter = new ArticleAdapter();
			try {
				clientUI.displayArticle(adapter.getArticle(content));
			} catch (Exception e) {
				clientUI.displayError("Error retrieving article: " + e);
			}
			return;
		}
		if(messageType.equals(GET_ALL_ARTICLES)) {
			ArticleAdapter adapter = new ArticleAdapter();
			try {
				clientUI.updateArticleList(adapter.getArticleList(content));
			} catch (Exception e) {
				clientUI.displayError("Error retrieving all articles: " + e);
			}
			return;
		}
		if(messageType.equals(REFRESH_ARTICLES)) {
			try {
				sendToServer(GET_ALL_ARTICLES,"");
			} catch (Exception e) {
				clientUI.displayError("Error retrieving articles: " + e);
			}
			return;
		}
		if (messageType.equals(INVALID_USERNAME)) {
			clientUI.displayError(content);
			return;
		}
		if (messageType.equals(INVALID_PASSWORD)) {
			clientUI.displayError(content);
			return;
		}
		if (messageType.equals(PASSWORD)) {
			String pass = clientUI.getPass();
			try {
				sendToServer(PASSWORD, pass);
			} catch (Exception e) {
				clientUI.displayError("Error logging in: " + e);
			}
			return;
		}
		if (messageType.equals(WRITER)) {
			WriterAdapter adapter = new WriterAdapter();
			try {
				clientUI.logInWriter(adapter.getWriter(content));
			} catch (Exception e) {
				clientUI.displayError("Error retrieving writer account: " + e);
			}
			return;
		}
		if (messageType.equals(SERVER_INFO)) {
			clientUI.displayServerMessage(content);
			return;
		}
	}

	public void handleMessageFromUI(UIMessage message) {
		try {
			if (message.equals(QUIT)) {
				if (isConnected()) {
					sendToServer(CLIENT_DISCONNECT, "Quit application");
					try {
						closeConnection();
					} catch(IOException e) {}
				}
				System.exit(0);
				return;
			}
			if (message.equals(LOGOUT)) {
				try {
					sendToServer(CLIENT_DISCONNECT, "Log off");
					closeConnection();
				} catch (Throwable t) {
					clientUI.displayError("Could not close connection: " + t);
				}
				return;
			}
			if (message.equals(ARTICLE_GET_ALL)) {
				sendToServer(GET_ALL_ARTICLES,"");
				return;
			}
			
			if (message.equals(ARTICLE_CREATE)) {
				Article article = clientUI.getArticle();
				ArticleAdapter adapter = new ArticleAdapter();
				sendToServer(CREATE_ARTICLE, adapter.getJson(article));
			}
			
			if (message.equals(ARTICLE_DELETE)) {
				Article article = clientUI.getSelectedArticle();
				ArticleAdapter adapter = new ArticleAdapter();
				sendToServer(DELETE_ARTICLE, adapter.getJson(article));
			}
			
			if (message.equals(LOGIN)) {
				sendToServer(USERNAME, clientUI.getUsername());
			}
		}
		catch(Exception e)
		{
			clientUI.displayError("Could not send message to server: " + e);
		}
	}


	
	final public void sendToServer(ServerMessage msg, String content) throws Exception {
		if (clientSocket == null) {
			throw new SocketException("socket does not exist.");
		}
		if (outputFromClient == null) {
			throw new Exception("Output stream does not exits.");
		}
		outputFromClient.writeObject(ServerMessage.toJson(msg));
		outputFromClient.writeObject(content);
	}

	final public void closeConnection() throws IOException {
		readyToStop= true;
		closeAll();
	}
	
	public void run() {
		ServerMessage msg;
		String content;
		try {
			while(!readyToStop) {
				msg = ServerMessage.toMessage(inputFromServer.readObject().toString());
				content = inputFromServer.readObject().toString();
				handleMessageFromServer(msg, content);
			}
		} catch (Exception exception) {
			if(!readyToStop) {
				try {
					closeAll();
				}
				catch (Exception ex) {}
			}
		} finally {
			clientReader = null;
		}
	}

	private void closeAll() throws IOException {
		try {
			if (clientSocket != null)
				clientSocket.close();
			if (outputFromClient != null)
				outputFromClient.close();
			if (inputFromServer != null)
				inputFromServer.close();
		} finally {
			outputFromClient = null;
			inputFromServer = null;
			clientSocket = null;
		}
	}

}
