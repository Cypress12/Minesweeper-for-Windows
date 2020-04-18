package minesweeper;

import java.util.Random;

public class Board
{
	private Cell[][] cells;// ����
	private Random random = new Random();
	private int numMine;// ������
	// �����Ѷȹ�������
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
		initEmptyCell();// ��ʼ������
		numMine = initNumMine(difficulty);// ���õ�����
		initMine();// ��ʼ������
		initMineCount();// ��ʼ������Ԫ����Χ������
	}

	// ��ʼ������
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

	// �����Ѷȷ��ص�����
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

	// ������õ���
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

	// ��ʼ����Χ������
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

	// ������Ԫ����Χ�����ص������������������±�Խ������Դ˴�ѭ��
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

	// ���ص�Ԫ������
	public int getBoardSize()
	{
		return getYSize() * this.getXSize();
	}

	// ���������Ŀ�
	public int getXSize()
	{
		return cells[0].length;
	}

	// ���������ĳ�
	public int getYSize()
	{
		return cells.length;
	}

	// ���ص�����
	public int getNumMine()
	{
		return numMine;
	}
}
