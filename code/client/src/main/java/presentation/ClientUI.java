package presentation;

import static presentation.UIMessage.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import client.Client;
import client.entity.Article;
import client.entity.Writer;

public class ClientUI extends JFrame{
	
	private Client client;
	private Writer writer;
	
	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JTextField tfUsername;
	private JLabel lblError;
	private JLabel lblMessage;
	private JPanel loginPanel;
	private JPanel writerPanel;
	private JLabel lblTitle;
	private JLabel lblAbstract;
	private JLabel lblWriter;
	private JTextPane textArticle;
	private JComboBox<Article> cbArticle;
	private JPanel readPanel;
	private JTextField tfTitle;
	private JTextField tfAbstract;
	private JTextPane textCreate;
	private JComboBox<Article> cbDelete;
	private JButton btnReadArticles;
	private JButton btnWriter;
	
	public ClientUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				client.handleMessageFromUI(QUIT);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setSize(new Dimension(720, 620));
		setSize(new Dimension(720, 657));
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel choosePanel = new JPanel();
		loginPanel = new JPanel();
		loginPanel.setOpaque(false);
		loginPanel.setVisible(false);
		choosePanel.setBounds(121, 13, 469, 45);
		getContentPane().add(choosePanel);

		readPanel = new JPanel();
		readPanel.setOpaque(false);
		readPanel.setVisible(false);
		readPanel.setBounds(41, 101, 649, 471);
		getContentPane().add(readPanel);
		readPanel.setLayout(null);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(118, 12, 116, 22);
		loginPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(118, 41, 114, 22);
		loginPanel.add(passwordField);
		
		btnWriter = new JButton("Writer Login");
		btnWriter.setBounds(41, 5, 128, 25);
		btnWriter.addActionListener(e->{
			choosePanel.setVisible(false); 
			loginPanel.setVisible(true);
			passwordField.setText("");
			tfUsername.setText("");
		});
		choosePanel.setLayout(null);
		choosePanel.add(btnWriter);
		
		btnReadArticles = new JButton("Read Articles");
		btnReadArticles.setBounds(181, 5, 131, 25);
		btnReadArticles.addActionListener(e->{
			choosePanel.setVisible(false);
			readPanel.setVisible(true);
			lblError.setText("");
			client.handleMessageFromUI(ARTICLE_GET_ALL);
		});
		choosePanel.add(btnReadArticles);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(326, 5, 66, 25);
		choosePanel.add(btnQuit);
		btnQuit.addActionListener(e->{
			client.handleMessageFromUI(QUIT);
		});
		
		loginPanel.setBounds(121, 67, 518, 76);
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(31, 44, 75, 16);
		loginPanel.add(lblPassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(31, 15, 75, 16);
		loginPanel.add(lblUsername);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(e->{
			client.handleMessageFromUI(LOGIN);
			client.handleMessageFromUI(ARTICLE_GET_ALL);
		});
		btnLogIn.setBounds(275, 27, 97, 25);
		loginPanel.add(btnLogIn);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(396, 27, 97, 25);
		btnBack.addActionListener(e->{
			lblError.setText("");
			choosePanel.setVisible(true); 
			loginPanel.setVisible(false);
		});
		loginPanel.add(btnBack);
		
		JButton btnBack2 = new JButton("Back");
		btnBack2.setBounds(545, 433, 92, 25);
		btnBack2.addActionListener(e->{
			choosePanel.setVisible(true);
			readPanel.setVisible(false);
		});
		readPanel.add(btnBack2);
		
		lblTitle = new JLabel("");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitle.setBounds(31, 49, 339, 25);
		readPanel.add(lblTitle);
		
		lblWriter = new JLabel("");
		lblWriter.setBounds(31, 87, 295, 16);
		readPanel.add(lblWriter);
		
		lblAbstract = new JLabel("");
		lblAbstract.setBounds(31, 116, 598, 16);
		readPanel.add(lblAbstract);
		
		textArticle = new JTextPane();
		textArticle.setBounds(12, 145, 625, 275);
		readPanel.add(textArticle);
		
		JLabel lblRead = new JLabel("Select article to read");
		lblRead.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRead.setBounds(20, 0, 241, 16);
		readPanel.add(lblRead);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(26, 568, 649, 29);
		getContentPane().add(lblError);
		
		lblMessage = new JLabel("");
		lblMessage.setBounds(41, 600, 634, 16);
		getContentPane().add(lblMessage);
		
		writerPanel = new JPanel();
		writerPanel.setBounds(40, 100, 649, 351);
		getContentPane().add(writerPanel);
		writerPanel.setVisible(false);
		writerPanel.setLayout(null);
		
		JButton btnLogout = new JButton("Log Out");
		btnLogout.setBounds(537, 313, 100, 25);
		btnLogout.addActionListener(e->{
			writerPanel.setVisible(false);
			loginPanel.setVisible(true);
		});
		writerPanel.add(btnLogout);
		
		JButton btnDelete = new JButton("Delete Article");
		btnDelete.setBounds(479, 13, 158, 25);
		btnDelete.addActionListener(e-> {
			client.handleMessageFromUI(ARTICLE_DELETE);
		});
		writerPanel.add(btnDelete);
		
		JLabel lblCreate = new JLabel("Create Article:");
		lblCreate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCreate.setBounds(12, 17, 158, 16);
		writerPanel.add(lblCreate);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(95, 46, 323, 22);
		writerPanel.add(tfTitle);
		tfTitle.setColumns(10);
		
		JLabel lblTitle2 = new JLabel("Title:");
		lblTitle2.setBounds(22, 49, 98, 16);
		writerPanel.add(lblTitle2);
		
		JLabel lblAbstract2 = new JLabel("Abstract:");
		lblAbstract2.setBounds(12, 84, 56, 16);
		writerPanel.add(lblAbstract2);
		
		tfAbstract = new JTextField();
		tfAbstract.setBounds(95, 81, 323, 22);
		writerPanel.add(tfAbstract);
		tfAbstract.setColumns(10);
		
		JLabel lblContent = new JLabel("Content:");
		lblContent.setBounds(12, 124, 56, 16);
		writerPanel.add(lblContent);
		
		textCreate = new JTextPane();
		textCreate.setBounds(12, 153, 482, 185);
		writerPanel.add(textCreate);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(530, 250, 100, 50);
		btnCreate.addActionListener(e-> {
			client.handleMessageFromUI(ARTICLE_CREATE);
		});
		writerPanel.add(btnCreate);
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void displayServerMessage(String message) {
		lblMessage.setText(message);
	}
	
	public void displayArticle(Article article) {
		lblTitle.setText(article.getTitle());
		lblAbstract.setText("Abstract: " + article.getAbstractt());
		lblWriter.setText("An article by " + article.getWriter().toString());
		textArticle.setText(article.getBody());
	}
	
	public void displayError(String message) {
		lblError.setText(message);
	}
	
	public void updateArticleList(List<Article> articles) {
		if (cbArticle!=null) readPanel.remove(cbArticle);
		cbArticle = new JComboBox<Article>(new Vector<Article>(articles));
		cbArticle.setBounds(31, 14, 309, 22);
		cbArticle.addActionListener(e->{
			@SuppressWarnings("unchecked")
			JComboBox<Article> cb = (JComboBox<Article>)e.getSource();
			Article article = (Article)cb.getSelectedItem();
	        displayArticle(article);
		});
		readPanel.add(cbArticle);
		readPanel.repaint();
		
		if (cbDelete!=null) writerPanel.remove(cbDelete);
		cbDelete = new JComboBox<Article>(new Vector<Article>(articles));
		cbDelete.setBounds(300, 10, 166, 22);
		writerPanel.add(cbDelete);
		writerPanel.repaint();
	}
	
	public void logInWriter(Writer writer) {
		loginPanel.setVisible(false);
		writerPanel.setVisible(true);
		this.writer = writer;
	}
	
	public Article getArticle() {
		Article article = new Article(tfTitle.getText(), tfAbstract.getText(), writer, textCreate.getText());
		tfTitle.setText("");
		tfAbstract.setText("");
		textCreate.setText("");
		return article;
	}
	
	public String getUsername() {
		return tfUsername.getText();
	}
	
	public String getPass() {
		return String.valueOf(passwordField.getPassword());
	}

	public Article getSelectedArticle() {
		return (Article)cbDelete.getSelectedItem();
	}

	public void disableButtons() {
		btnWriter.setEnabled(false);
		btnReadArticles.setEnabled(false);
	}
}
