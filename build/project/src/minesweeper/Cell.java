package minesweeper;

import javafx.scene.image.Image;
import javafx.scene.control.Label;

public class Cell extends Label
{
	private CellValue id;// ��Ԫ��״̬
	private int mineCount;// ��Χ������
	private boolean isSelected = false;// �Ƿ�ѡ��
	private boolean isFlagged = false;// �Ƿ񱻱��
	private static int numFlag = 0;// ����ǵĵ�Ԫ����
	private static int numSelectedCell = 0;// ��ѡ��ĵ�Ԫ����
	// ���ͼƬ��Դ
	private static Image unselected = new Image("res/unselected.jpg");
	private static Image mine = new Image("res/mine.jpg");
	private static Image flag = new Image("res/flag.jpg");
	private static Image zero = new Image("res/zero.jpg");
	private static Image one = new Image("res/one.jpg");
	private static Image two = new Image("res/two.jpg");
	private static Image three = new Image("res/three.jpg");
	private static Image four = new Image("res/four.jpg");
	private static Image five = new Image("res/five.jpg");
	private static Image six = new Image("res/six.jpg");
	private static Image seven = new Image("res/seven.jpg");
	private static Image eight = new Image("res/eight.jpg");

	// Ĭ�Ϲ��쵥Ԫ�񣬽�������Ϊ�ղ�������ʽ
	public Cell()
	{
		this(CellValue.EMPTY);
		this.setStyle("-fx-border-color: red; -fx-border-width: 2");
	}

	// ����״̬���õ�Ԫ��
	public Cell(CellValue id)
	{
		this.id = id;
	}

	// ����Ԫ����Ϊ����
	public void setMine()
	{
		id = CellValue.MINE;
	}

	// ���ص�Ԫ��״̬
	public CellValue getID()
	{
		return id;
	}

	// ѡ��Ԫ��
	public void select()
	{
		isSelected = true;// ��Ԫ��ѡ��
		// �����Ԫ�񱻱����ȡ�����
		if (isFlagged)
			flag();
		numSelectedCell++;// ��ѡ��ĵ�Ԫ������1
	}

	// ��ǵ�Ԫ��
	public void flag()
	{
		isFlagged = !isFlagged;// ���ĵ�Ԫ��ѡ��״̬
		// ����Ԫ�񱻱����������1�������1
		if (this.isFlagged())
			numFlag++;
		else
			numFlag--;
	}

	// ���ر�ѡ��״̬
	public boolean isSelected()
	{
		return isSelected;
	}

	// ���ر����״̬
	public boolean isFlagged()
	{
		return isFlagged;
	}

	// ���õ�Ԫ����Χ������
	public void setMineCount(int mineCount)
	{
		this.mineCount = mineCount;
	}

	// ������Χ������
	public int getMineCount()
	{
		return mineCount;
	}

	// ����δѡ��ͼƬ
	public Image getUnselectedImage()
	{
		return unselected;
	}

	// ���ر��ͼƬ
	public Image getFlagImage()
	{
		return flag;
	}

	// ������Χ������������ӦͼƬ
	public Image getSelectedImage()
	{
		if (id.equals(CellValue.MINE))
			return getMineImage();
		switch (mineCount)
		{
		case 0:
			return zero;
		case 1:
			return one;
		case 2:
			return two;
		case 3:
			return three;
		case 4:
			return four;
		case 5:
			return five;
		case 6:
			return six;
		case 7:
			return seven;
		case 8:
			return eight;
		default:
			return null;
		}
	}

	// ���ص���ͼƬ
	public Image getMineImage()
	{
		return mine;
	}

	// ���ر�ѡ��Ԫ����
	public static int getNumSelectedCell()
	{
		return numSelectedCell;
	}

	// ���ر���ǵ�Ԫ����
	public static int getNumFlag()
	{
		return numFlag;
	}

	// ������ǵ�Ԫ��������Ϊ0
	public static void resetNumFlag()
	{
		numFlag = 0;
	}
	// ����ѡ�����������Ϊ0
	public static void resetNumSelectedCell()
	{
		numSelectedCell=0;
	}
}
