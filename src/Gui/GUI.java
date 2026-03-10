package Gui;

import Player.Player;
import Server.ReadThread;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application {

	public static final int size = 20;
	public static final int scene_height = size * 20 + 100;
	public static final int scene_width = size * 20 + 200;

	public static Image image_floor;
	public static Image image_wall;
	public static Image hero_right, hero_left, hero_up, hero_down;

	public static Player me;
	public static List<Player> players = new ArrayList<>();

	private static GUI instance;

	private Label[][] fields;
	private TextArea scoreList;

	private final String[] board = {
			"wwwwwwwwwwwwwwwwwwww",
			"w        ww        w",
			"w w  w  www w  w  ww",
			"w w  w   ww w  w  ww",
			"w  w               w",
			"w w w w w w w  w  ww",
			"w w     www w  w  ww",
			"w w     w w w  w  ww",
			"w   w w  w  w  w   w",
			"w     w  w  w  w   w",
			"w ww ww        w  ww",
			"w  w w    w    w  ww",
			"w        ww w  w  ww",
			"w         w w  w  ww",
			"w        w     w  ww",
			"w  w              ww",
			"w  w www  w w  ww ww",
			"w w      ww w     ww",
			"w   w   ww  w      w",
			"wwwwwwwwwwwwwwwwwwww"
	};

	@Override
	public void start(Stage primaryStage) {
		try {
			instance = this;

			Socket clientSocket = new Socket("10.10.138.165", 6789);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String firstMessage = inFromServer.readLine();
			int playerId = -1;

			if (firstMessage != null && firstMessage.startsWith("PLAYER:")) {
				playerId = Integer.parseInt(firstMessage.substring(7));
			}

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			Text mazeLabel = new Text("Maze:");
			mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			Text scoreLabel = new Text("Score:");
			scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			scoreList = new TextArea();
			scoreList.setEditable(false);

			GridPane boardGrid = new GridPane();

			image_wall = new Image(getClass().getResourceAsStream("/Image/wall4.png"), size, size, false, false);
			image_floor = new Image(getClass().getResourceAsStream("/Image/floor1.png"), size, size, false, false);

			hero_right = new Image(getClass().getResourceAsStream("/Image/heroRight.png"), size, size, false, false);
			hero_left = new Image(getClass().getResourceAsStream("/Image/heroLeft.png"), size, size, false, false);
			hero_up = new Image(getClass().getResourceAsStream("/Image/heroUp.png"), size, size, false, false);
			hero_down = new Image(getClass().getResourceAsStream("/Image/heroDown.png"), size, size, false, false);

			fields = new Label[20][20];
			for (int j = 0; j < 20; j++) {
				for (int i = 0; i < 20; i++) {
					switch (board[j].charAt(i)) {
						case 'w':
							fields[i][j] = new Label("", new ImageView(image_wall));
							break;
						case ' ':
							fields[i][j] = new Label("", new ImageView(image_floor));
							break;
						default:
							throw new Exception("Illegal field value: " + board[j].charAt(i));
					}
					boardGrid.add(fields[i][j], i, j);
				}
			}

			grid.add(mazeLabel, 0, 0);
			grid.add(scoreLabel, 1, 0);
			grid.add(boardGrid, 0, 1);
			grid.add(scoreList, 1, 1);

			setupPlayers(playerId);
			scoreList.setText(getScoreList());

			Scene scene = new Scene(grid, scene_width, scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			ReadThread rt = new ReadThread(clientSocket);
			rt.start();

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				try {
					switch (event.getCode()) {
						case UP:
							outToServer.writeBytes("MOVE:UP\n");
							break;
						case DOWN:
							outToServer.writeBytes("MOVE:DOWN\n");
							break;
						case LEFT:
							outToServer.writeBytes("MOVE:LEFT\n");
							break;
						case RIGHT:
							outToServer.writeBytes("MOVE:RIGHT\n");
							break;
						default:
							break;
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupPlayers(int playerId) {
		players.clear();

		Player p0 = new Player("Spiller1", 9, 4, "up");
		Player p1 = new Player("Spiller2", 14, 15, "up");
		Player p2 = new Player("Spiller3", 9, 10, "up");

		players.add(p0);
		players.add(p1);
		players.add(p2);

		//fields[9][4].setGraphic(new ImageView(hero_up));
		//fields[14][15].setGraphic(new ImageView(hero_up));
		//fields[9][10].setGraphic(new ImageView(hero_up));

		if (playerId >= 0 && playerId < players.size()) {
			me = players.get(playerId);
			if(playerId==0) {
				fields[9][4].setGraphic(new ImageView(hero_up));
			}else if(playerId==1){
				fields[9][4].setGraphic(new ImageView(hero_up));
			} else {
				fields[9][4].setGraphic(new ImageView(hero_up));
			}
		} else {
			System.out.println("For mange clients connected");
		}
	}

	public static void applyMoveFromServer(int playerId, String direction) {
		if (instance == null) return;
		if (playerId < 0 || playerId >= players.size()) return;

		int dx = 0;
		int dy = 0;
		String dir = direction.toLowerCase();

		switch (direction.toUpperCase()) {
			case "UP":
				dy = -1;
				break;
			case "DOWN":
				dy = 1;
				break;
			case "LEFT":
				dx = -1;
				break;
			case "RIGHT":
				dx = 1;
				break;
			default:
				return;
		}

		instance.movePlayer(players.get(playerId), dx, dy, dir);
	}

	private void movePlayer(Player player, int delta_x, int delta_y, String direction) {
		player.setDirection(direction);

		int x = player.getXpos();
		int y = player.getYpos();

		if (board[y + delta_y].charAt(x + delta_x) == 'w') {
			player.addPoints(-1);
		} else {
			Player p = getPlayerAt(x + delta_x, y + delta_y);

			if (p != null && p != player) {
				player.addPoints(10);
				p.addPoints(-10);
			} else {
				player.addPoints(1);

				fields[x][y].setGraphic(new ImageView(image_floor));

				x += delta_x;
				y += delta_y;

				if (direction.equals("right")) {
					fields[x][y].setGraphic(new ImageView(hero_right));
				}
				if (direction.equals("left")) {
					fields[x][y].setGraphic(new ImageView(hero_left));
				}
				if (direction.equals("up")) {
					fields[x][y].setGraphic(new ImageView(hero_up));
				}
				if (direction.equals("down")) {
					fields[x][y].setGraphic(new ImageView(hero_down));
				}

				player.setXpos(x);
				player.setYpos(y);
			}
		}

		scoreList.setText(getScoreList());
	}

	public String getScoreList() {
		StringBuilder b = new StringBuilder(100);
		for (Player p : players) {
			b.append(p).append("\r\n");
		}
		return b.toString();
	}

	public Player getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getXpos() == x && p.getYpos() == y) {
				return p;
			}
		}
		return null;
	}
}