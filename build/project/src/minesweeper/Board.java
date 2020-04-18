package minesweeper;

import java.util.Random;

public class Board
{
	private Cell[][] cells;// 雷区
	private Random random = new Random();
	private int numMine;// 地雷数
	// 根据难度构造雷区
	public Board(Difficulty level)
	{
		if (level == Difficulty.EASY)
			cells = new Cell[9][9];
		else if (level == Difficulty.MEDIUM)
			cells = new Cell[16][16];
		else
			cells = new Cell[16][30];
	}

	public void init(Difficulty difficulty)
	{
		initEmptyCell();// 初始化雷区
		numMine = initNumMine(difficulty);// 设置地雷数
		initMine();// 初始化地雷
		initMineCount();// 初始化各单元格周围地雷数
	}

	// 初始化雷区
	private void initEmptyCell()
	{
		for (int i = 0; i < cells.length; i++)
		{
			for (int j = 0; j < cells[0].length; j++)
			{
				cells[i][j] = new Cell();
			}
		}
	}

	// 根据难度返回地雷数
	private int initNumMine(Difficulty difficulty)
	{
		switch (difficulty)
		{
		case EASY:
			return 10;
		case MEDIUM:
			return 40;
		case HARD:
			return 99;
		default:
			return 0;
		}
	}

	// 随机设置地雷
	private void initMine()
	{
		for (int i = 0; i < numMine; i++)
		{
			while (true)
			{
				Cell randomCell = cells[random.nextInt(cells.length)][random.nextInt(cells[0].length)];

				if (!(randomCell.getID().equals(CellValue.MINE)))
				{
					randomCell.setMine();
					break;
				}
			}
		}
	}

	// 初始化周围地雷数
	private void initMineCount()
	{
		for (int i = 0; i < cells.length; i++)
		{
			for (int j = 0; j < cells[0].length; j++)
			{
				if (cells[i][j].getID().equals(CellValue.MINE))
					continue;
				int mineCount = 0;
				mineCount = getMineCount(j, i);
				cells[i][j].setMineCount(mineCount);
			}
		}
	}

	public Cell getCell(int x, int y)
	{
		return cells[y][x];
	}

	public Cell[][] getCell()
	{
		return cells;
	}

	// 遍历单元格周围，返回地雷数。若发生数组下标越界则忽略此次循环
	private int getMineCount(int x, int y)
	{
		int mineCount = 0;
		for (int i = (y - 1); i <= (y + 1); i++)
		{
			for (int j = (x - 1); j <= (x + 1); j++)
			{
				if (i == y && j == x)
					continue;
				try
				{
					if (cells[i][j].getID().equals(CellValue.MINE))
						mineCount++;
				}
				catch (IndexOutOfBoundsException ex)
				{
					continue;
				}

			}
		}
		return mineCount;
	}

	// 返回单元格总数
	public int getBoardSize()
	{
		return getYSize() * this.getXSize();
	}

	// 返回雷区的宽
	public int getXSize()
	{
		return cells[0].length;
	}

	// 返回雷区的长
	public int getYSize()
	{
		return cells.length;
	}

	// 返回地雷数
	public int getNumMine()
	{
		return numMine;
	}
}
