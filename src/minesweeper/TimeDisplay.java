package minesweeper;

import javafx.animation.Animation;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class TimeDisplay extends Label
{
	Timeline timeline;// 动画
	private Font font = Font.font("Times New Roman", FontWeight.BOLD, 18);// 字体样式
	long time;// 时间

	// 将时间初始化为00：00：00
	public TimeDisplay()
	{
		super("时间：00:00:00");
		this.time = System.currentTimeMillis();// 获取当前系统时间
		this.setFont(font);// 设置计时器样式
	}

	public void start()
	{
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> addSecond()));// 设置动画刷新时间和动作
		timeline.setCycleCount(Animation.INDEFINITE);// 设置动画为无限循环
		timeline.play();// 播放动画
	}

	public void stop()
	{
		timeline.pause();// 暂停动画
	}

	public void reset()
	{
		this.time = System.currentTimeMillis();// 重置时间为当前系统时间
		this.setText("时间：00:00:00");// 重置计数器
	}

	// 设置动画事件，将时间转化为标准格式输出
	private void addSecond()
	{
		String str = String.format("时间：%1$tH:%1$tM:%1$tS", System.currentTimeMillis() - time - 28800000);
		this.setText(str);
	}
}
