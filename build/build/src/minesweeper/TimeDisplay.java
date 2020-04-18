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
	Timeline timeline;// ����
	private Font font = Font.font("Times New Roman", FontWeight.BOLD, 18);// ������ʽ
	long time;// ʱ��

	// ��ʱ���ʼ��Ϊ00��00��00
	public TimeDisplay()
	{
		super("ʱ�䣺00:00:00");
		this.time = System.currentTimeMillis();// ��ȡ��ǰϵͳʱ��
		this.setFont(font);// ���ü�ʱ����ʽ
	}

	public void start()
	{
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> addSecond()));// ���ö���ˢ��ʱ��Ͷ���
		timeline.setCycleCount(Animation.INDEFINITE);// ���ö���Ϊ����ѭ��
		timeline.play();// ���Ŷ���
	}

	public void stop()
	{
		timeline.pause();// ��ͣ����
	}

	public void reset()
	{
		this.time = System.currentTimeMillis();// ����ʱ��Ϊ��ǰϵͳʱ��
		this.setText("ʱ�䣺00:00:00");// ���ü�����
	}

	// ���ö����¼�����ʱ��ת��Ϊ��׼��ʽ���
	private void addSecond()
	{
		String str = String.format("ʱ�䣺%1$tH:%1$tM:%1$tS", System.currentTimeMillis() - time - 28800000);
		this.setText(str);
	}
}
