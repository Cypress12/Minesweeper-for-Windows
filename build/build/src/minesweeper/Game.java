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
	private NumberDisplay mineCount = new NumberDisplay();// 地雷计数器
	private int numMine;// 雷数
	private TimeDisplay time = new TimeDisplay();// 计时器
	private Board board;// 雷区
	private MainGame mainGame;// 雷区面板
	private Font font = Font.font("Times New Roman", FontWeight.BOLD, 15);// 字体样式
	private MenuBar menuBar = new MenuBar();// 菜单栏
	private VBox mainLayout = new VBox(10);// 竖直排列控件，用来装载菜单和numberLayout
	private HBox numberLayout = new HBox(780);// 水平排列控件，用来装载地雷计数器和计时器
	private BorderPane root = new BorderPane();// 主面板
	private Difficulty level;// 难度
	private boolean firstgame;
	private boolean firsttime;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		Scene scene = new Scene(root, 1000, 600);// 创建新场景
		stage.setScene(scene);// 将场景放置在舞台中
		initMenu();// 菜单初始化
		numberLayout.getChildren().addAll(mineCount, time);// 装载地雷计数器和计时器
		mainLayout.getChildren().addAll(menuBar, numberLayout);// 装载numberLayout和菜单
		initgame();// 初始化游戏
		root.setTop(mainLayout);// 将控件置于主面板上方
		stage.setResizable(false);// 禁用最大化按钮
		stage.setTitle("扫雷");// 设置舞台标题
		stage.show();// 显示舞台
	}

	// 为雷区面板添加事件
	private void addevent(MainGame mainGame)
	{
		// 添加鼠标事件
		mainGame.setOnMouseClicked(e ->
		{
			// 如果雷区是第一次被点击则开始计时
			if (mainGame.first && firstgame)
			{
				time.reset();
				time.start();
				firstgame=false;
				firsttime=false;
			}
			if (mainGame.isEnd())// 若游戏结束
			{
				mainGame.setDisable(true);// 将主界面设置为不可点击
				time.stop();// 停止计时
				// 判断胜负并显示相应结果
				if (mainGame.isWin())
					win();
				else
					lose();
			} else// 若游戏未结束
			{
				if (e.getButton().equals(MouseButton.SECONDARY))// 若点击鼠标右键
					updateMineCount();// 更新剩余雷数
			}
		});
	}

	// 初始化菜单
	private void initMenu()
	{
		// 定义菜单栏
		Menu game = new Menu("游戏");
		MenuItem easy = new MenuItem("初级");
		MenuItem medium = new MenuItem("中级");
		MenuItem hard = new MenuItem("高级");
		MenuItem restart = new MenuItem("重新开始");
		game.getItems().addAll(easy, medium, hard, restart);
		menuBar.getMenus().add(game);
		// 为菜单的每个标签添加相应的事件
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

	// 初始化默认游戏
	private void initgame()
	{
		// 初始化各个参数
		firstgame = true;
		firsttime = true;
		time.reset();
		level = Difficulty.EASY;
		board = new Board(level);
		mainGame = new MainGame(board, level);
		addevent(mainGame);
		resetMineCount();// 初始化地雷计数器
		root.setCenter(mainGame);// 将游戏面板置于主面板中央
	}

	// 根据难度初始化游戏
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
		Cell.resetNumSelectedCell();// 将被选择单元格数重置为0
		root.setCenter(mainGame);
	}

	// 重新开始游戏
	private void restartgame(Difficulty level)
	{
		if (!firsttime)
			time.stop();
		initgame(level);
	}

	// 初始化地雷计数器
	private void resetMineCount()
	{
		numMine = board.getNumMine();// 获取地雷数
		Cell.resetNumFlag();// 将已标记地雷数置为0
		// 更新地雷计数器
		mineCount.setNumber(numMine);
		mineCount.update();
	}

	// 更新剩余雷数
	private void updateMineCount()
	{
		numMine = board.getNumMine() - Cell.getNumFlag();
		mineCount.setNumber(numMine);
		mineCount.update();
	}

	// 显示胜利界面
	private void win()
	{
		mineCount.setNumber(0);
		mineCount.update();
		// 初始化舞台和胜利面板
		Stage stageWin = new Stage();
		FlowPane win = new FlowPane();
		win.setAlignment(Pos.CENTER);
		win.setOrientation(Orientation.VERTICAL);
		win.setVgap(10);
		// 添加按钮和标签
		Button restart = new Button("重新开始");
		Label label = new Label("你赢了！");
		label.setFont(font);
		Label labeltime = new Label(time.getText());// 获取计时器时间
		win.getChildren().addAll(label, labeltime, restart);// 将各个控件添加到面板中
		Scene sceneWin = new Scene(win, 100, 100);// 创建新场景
		// 设置舞台参数
		stageWin.setScene(sceneWin);
		stageWin.setTitle("Win");
		stageWin.setResizable(false);
		stageWin.setAlwaysOnTop(true);
		stageWin.show();
		// 为重新开始按钮设置事件
		restart.setOnAction(e ->
		{
			restartgame(level);
			stageWin.close();
		});
	}

	// 显示失败界面
	private void lose()
	{
		// 初始化舞台和失败面板
		Stage stagelose = new Stage();
		FlowPane lose = new FlowPane();
		lose.setAlignment(Pos.CENTER);
		lose.setOrientation(Orientation.VERTICAL);
		lose.setVgap(10);
		// 添加按钮和标签
		Button restart = new Button("重新开始");
		Label label = new Label("你输了！");
		label.setFont(font);
		Label labeltime = new Label(time.getText());// 获取计时器时间
		lose.getChildren().addAll(label, labeltime, restart);// 将各个控件添加到面板中
		Scene scenelose = new Scene(lose, 100, 100);// 创建新场景
		// 设置舞台参数
		stagelose.setScene(scenelose);
		stagelose.alwaysOnTopProperty();
		stagelose.setTitle("Lose");
		stagelose.setResizable(false);
		stagelose.setAlwaysOnTop(true);
		stagelose.show();
		// 为重新开始按钮设置事件
		restart.setOnAction(e ->
		{
			restartgame(level);
			stagelose.close();
		});
	}
}
