package minesweeper;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NumberDisplay extends Label
{
	private int number;
	private Font font = Font.font("Times New Roman", FontWeight.BOLD, 18);// 字体样式

	// 将雷数初始化为0
	public NumberDisplay()
	{
		super("剩余雷数:" + 0 + "");
		this.setFont(font);
	}

	// 设置地雷数
	public void setNumber(int number)
	{
		this.number = number;
	}

	// 获取地雷数
	public int getNumber()
	{
		return number;
	}

	// 更新地雷数
	public void update()
	{
		this.setText("剩余雷数:" + this.number + "");
	}
}
