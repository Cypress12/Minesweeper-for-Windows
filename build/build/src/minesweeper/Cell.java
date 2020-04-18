package minesweeper;

import javafx.scene.image.Image;
import javafx.scene.control.Label;

public class Cell extends Label
{
	private CellValue id;// 单元格状态
	private int mineCount;// 周围地雷数
	private boolean isSelected = false;// 是否被选择
	private boolean isFlagged = false;// 是否被标记
	private static int numFlag = 0;// 被标记的单元格数
	private static int numSelectedCell = 0;// 被选择的单元格数
	// 添加图片资源
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

	// 默认构造单元格，将其设置为空并定义样式
	public Cell()
	{
		this(CellValue.EMPTY);
		this.setStyle("-fx-border-color: red; -fx-border-width: 2");
	}

	// 根据状态设置单元格
	public Cell(CellValue id)
	{
		this.id = id;
	}

	// 将单元格标记为有雷
	public void setMine()
	{
		id = CellValue.MINE;
	}

	// 返回单元格状态
	public CellValue getID()
	{
		return id;
	}

	// 选择单元格
	public void select()
	{
		isSelected = true;// 单元格被选择
		// 如果单元格被标记则取消标记
		if (isFlagged)
			flag();
		numSelectedCell++;// 被选择的单元格数加1
	}

	// 标记单元格
	public void flag()
	{
		isFlagged = !isFlagged;// 更改单元格选择状态
		// 若单元格被标记则标记数加1，否则减1
		if (this.isFlagged())
			numFlag++;
		else
			numFlag--;
	}

	// 返回被选择状态
	public boolean isSelected()
	{
		return isSelected;
	}

	// 返回被标记状态
	public boolean isFlagged()
	{
		return isFlagged;
	}

	// 设置单元格周围地雷数
	public void setMineCount(int mineCount)
	{
		this.mineCount = mineCount;
	}

	// 返回周围地雷数
	public int getMineCount()
	{
		return mineCount;
	}

	// 返回未选择图片
	public Image getUnselectedImage()
	{
		return unselected;
	}

	// 返回标记图片
	public Image getFlagImage()
	{
		return flag;
	}

	// 根据周围地雷数返回相应图片
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

	// 返回地雷图片
	public Image getMineImage()
	{
		return mine;
	}

	// 返回被选择单元格数
	public static int getNumSelectedCell()
	{
		return numSelectedCell;
	}

	// 返回被标记单元格数
	public static int getNumFlag()
	{
		return numFlag;
	}

	// 将被标记单元格数重置为0
	public static void resetNumFlag()
	{
		numFlag = 0;
	}
	// 将被选择地雷数重置为0
	public static void resetNumSelectedCell()
	{
		numSelectedCell=0;
	}
}
