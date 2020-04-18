package minesweeper;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NumberDisplay extends Label
{
	private int number;
	private Font font = Font.font("Times New Roman", FontWeight.BOLD, 18);// ������ʽ

	// ��������ʼ��Ϊ0
	public NumberDisplay()
	{
		super("ʣ������:" + 0 + "");
		this.setFont(font);
	}

	// ���õ�����
	public void setNumber(int number)
	{
		this.number = number;
	}

	// ��ȡ������
	public int getNumber()
	{
		return number;
	}

	// ���µ�����
	public void update()
	{
		this.setText("ʣ������:" + this.number + "");
	}
}
