package minesweeper;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Game extends Application
{
	private NumberDisplay mineCount = new NumberDisplay();// ���׼�����
	private int numMine;// ����
	private TimeDisplay time = new TimeDisplay();// ��ʱ��
	private Board board;// ����
	private MainGame mainGame;// �������
	private Font font = Font.font("Times New Roman", FontWeight.BOLD, 15);// ������ʽ
	private MenuBar menuBar = new MenuBar();// �˵���
	private VBox mainLayout = new VBox(10);// ��ֱ���пؼ�������װ�ز˵���numberLayout
	private HBox numberLayout = new HBox(780);// ˮƽ���пؼ�������װ�ص��׼������ͼ�ʱ��
	private BorderPane root = new BorderPane();// �����
	private Difficulty level;// �Ѷ�
	private boolean firstgame;
	private boolean firsttime;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		Scene scene = new Scene(root, 1000, 600);// �����³���
		stage.setScene(scene);// ��������������̨��
		initMenu();// �˵���ʼ��
		numberLayout.getChildren().addAll(mineCount, time);// װ�ص��׼������ͼ�ʱ��
		mainLayout.getChildren().addAll(menuBar, numberLayout);// װ��numberLayout�Ͳ˵�
		initgame();// ��ʼ����Ϸ
		root.setTop(mainLayout);// ���ؼ�����������Ϸ�
		stage.setResizable(false);// ������󻯰�ť
		stage.setTitle("ɨ��");// ������̨����
		stage.show();// ��ʾ��̨
	}

	// Ϊ�����������¼�
	private void addevent(MainGame mainGame)
	{
		// �������¼�
		mainGame.setOnMouseClicked(e ->
		{
			// ��������ǵ�һ�α������ʼ��ʱ
			if (mainGame.first && firstgame)
			{
				time.reset();
				time.start();
				firstgame=false;
				firsttime=false;
			}
			if (mainGame.isEnd())// ����Ϸ����
			{
				mainGame.setDisable(true);// ������������Ϊ���ɵ��
				time.stop();// ֹͣ��ʱ
				// �ж�ʤ������ʾ��Ӧ���
				if (mainGame.isWin())
					win();
				else
					lose();
			} else// ����Ϸδ����
			{
				if (e.getButton().equals(MouseButton.SECONDARY))// ���������Ҽ�
					updateMineCount();// ����ʣ������
			}
		});
	}

	// ��ʼ���˵�
	private void initMenu()
	{
		// ����˵���
		Menu game = new Menu("��Ϸ");
		MenuItem easy = new MenuItem("����");
		MenuItem medium = new MenuItem("�м�");
		MenuItem hard = new MenuItem("�߼�");
		MenuItem restart = new MenuItem("���¿�ʼ");
		game.getItems().addAll(easy, medium, hard, restart);
		menuBar.getMenus().add(game);
		// Ϊ�˵���ÿ����ǩ�����Ӧ���¼�
		easy.setOnAction(e ->
		{
			if (level != Difficulty.EASY)
				initgame(Difficulty.EASY);
		});
		medium.setOnAction(e ->
		{
			if (level != Difficulty.MEDIUM)
				initgame(Difficulty.MEDIUM);
		});
		hard.setOnAction(e ->
		{
			if (level != Difficulty.HARD)
				initgame(Difficulty.HARD);
		});
		restart.setOnAction(e -> restartgame(level));
	}

	// ��ʼ��Ĭ����Ϸ
	private void initgame()
	{
		// ��ʼ����������
		firstgame = true;
		firsttime = true;
		time.reset();
		level = Difficulty.EASY;
		board = new Board(level);
		mainGame = new MainGame(board, level);
		addevent(mainGame);
		resetMineCount();// ��ʼ�����׼�����
		root.setCenter(mainGame);// ����Ϸ����������������
	}

	// �����Ѷȳ�ʼ����Ϸ
	private void initgame(Difficulty level)
	{
		firstgame = true;
		time.reset();
		if (!firsttime)
			time.stop();
		this.level = level;
		board = new Board(level);
		mainGame = new MainGame(board, level);
		addevent(mainGame);
		resetMineCount();
		Cell.resetNumSelectedCell();// ����ѡ��Ԫ��������Ϊ0
		root.setCenter(mainGame);
	}

	// ���¿�ʼ��Ϸ
	private void restartgame(Difficulty level)
	{
		if (!firsttime)
			time.stop();
		initgame(level);
	}

	// ��ʼ�����׼�����
	private void resetMineCount()
	{
		numMine = board.getNumMine();// ��ȡ������
		Cell.resetNumFlag();// ���ѱ�ǵ�������Ϊ0
		// ���µ��׼�����
		mineCount.setNumber(numMine);
		mineCount.update();
	}

	// ����ʣ������
	private void updateMineCount()
	{
		numMine = board.getNumMine() - Cell.getNumFlag();
		mineCount.setNumber(numMine);
		mineCount.update();
	}

	// ��ʾʤ������
	private void win()
	{
		mineCount.setNumber(0);
		mineCount.update();
		// ��ʼ����̨��ʤ�����
		Stage stageWin = new Stage();
		FlowPane win = new FlowPane();
		win.setAlignment(Pos.CENTER);
		win.setOrientation(Orientation.VERTICAL);
		win.setVgap(10);
		// ��Ӱ�ť�ͱ�ǩ
		Button restart = new Button("���¿�ʼ");
		Label label = new Label("��Ӯ�ˣ�");
		label.setFont(font);
		Label labeltime = new Label(time.getText());// ��ȡ��ʱ��ʱ��
		win.getChildren().addAll(label, labeltime, restart);// �������ؼ���ӵ������
		Scene sceneWin = new Scene(win, 100, 100);// �����³���
		// ������̨����
		stageWin.setScene(sceneWin);
		stageWin.setTitle("Win");
		stageWin.setResizable(false);
		stageWin.setAlwaysOnTop(true);
		stageWin.show();
		// Ϊ���¿�ʼ��ť�����¼�
		restart.setOnAction(e ->
		{
			restartgame(level);
			stageWin.close();
		});
	}

	// ��ʾʧ�ܽ���
	private void lose()
	{
		// ��ʼ����̨��ʧ�����
		Stage stagelose = new Stage();
		FlowPane lose = new FlowPane();
		lose.setAlignment(Pos.CENTER);
		lose.setOrientation(Orientation.VERTICAL);
		lose.setVgap(10);
		// ��Ӱ�ť�ͱ�ǩ
		Button restart = new Button("���¿�ʼ");
		Label label = new Label("�����ˣ�");
		label.setFont(font);
		Label labeltime = new Label(time.getText());// ��ȡ��ʱ��ʱ��
		lose.getChildren().addAll(label, labeltime, restart);// �������ؼ���ӵ������
		Scene scenelose = new Scene(lose, 100, 100);// �����³���
		// ������̨����
		stagelose.setScene(scenelose);
		stagelose.alwaysOnTopProperty();
		stagelose.setTitle("Lose");
		stagelose.setResizable(false);
		stagelose.setAlwaysOnTop(true);
		stagelose.show();
		// Ϊ���¿�ʼ��ť�����¼�
		restart.setOnAction(e ->
		{
			restartgame(level);
			stagelose.close();
		});
	}
}
