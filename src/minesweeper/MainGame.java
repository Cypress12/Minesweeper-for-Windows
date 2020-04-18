package minesweeper;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

public class MainGame extends GridPane
{
	private ImageView[][] cell;
	private boolean win;// 游戏是否胜利
	private boolean end;// 游戏是否结束
	public static final int CELL_SIZE = 30;// 单元格大小
	public boolean first = false;// 是否为第一次点击雷区

	public MainGame(Board board, Difficulty difficulty)
	{
		board.init(difficulty);// 初始化雷区
		// 初始化面板
		this.setGridLinesVisible(true);
		this.setAlignment(Pos.CENTER);
		this.setHgap(2);
		this.setVgap(2);
		cell = new ImageView[board.getYSize()][board.getXSize()];
		// 根据雷区状态显示每个单元格图片
		for (int i = 0; i < board.getYSize(); i++)
		{
			for (int j = 0; j < board.getXSize(); j++)
			{
				cell[i][j] = new ImageView(board.getCell(j, i).getUnselectedImage());
				cell[i][j].setFitHeight(CELL_SIZE);
				cell[i][j].setFitWidth(CELL_SIZE);
				GridPane.setRowIndex(cell[i][j], i + 1);
				GridPane.setColumnIndex(cell[i][j], j + 1);
				this.getChildren().add(cell[i][j]);
			}
		}
		assignEvent(board);// 为面板添加事件
	}

	private void assignEvent(Board board)
	{
		for (ImageView[] cellRow : this.getCell())
		{
			for (ImageView cell : cellRow)
			{
				cell.setOnMouseClicked(e ->
				{
					if (!first)// 若是第一次点击雷区则置为真
						first = true;
					int[] index = getClickedIndex(cell, board);
					int x = index[0];
					int y = index[1];
					if (e.getButton().equals(MouseButton.SECONDARY))// 点击右键
					{
						// 若单元格未被选择，标记单元格
						if (!(board.getCell(x, y).isSelected()))
							flag(x, y, board);
					}
					else// 点击左键
					{
						// 若单元格未被标记，展开单元格
						if (!(board.getCell(x, y).isFlagged()))
						{
							selectCell(x, y, x, y, board);// 展开单元格
						}
					}
					// 若满足胜利条件则游戏结束
					if (board.getBoardSize() - board.getNumMine() == Cell.getNumSelectedCell()
							&&board.getNumMine()==Cell.getNumFlag())
					{
						win();
					}
				});
			}
		}
	}

	private void flag(int x, int y, Board board)
	{
		board.getCell(x, y).flag();// 更新单元格标记状态
		// 若单元格被标记，显示标记图片，否则显示未标记图片
		if (board.getCell(x, y).isFlagged())
		{
			cell[y][x].setImage(board.getCell(x, y).getFlagImage());
		}
		else
		{
			cell[y][x].setImage(board.getCell(x, y).getUnselectedImage());
		}
	}

	private void selectCell(int firstX, int firstY, int x, int y, Board board)
	{
		this.cell[y][x].setImage(board.getCell(x, y).getSelectedImage());// 设置被选择图片
		board.getCell(x, y).select();// 更新单元格选择状态
		// 若被点击单元格有雷，游戏失败
		if (board.getCell(x, y).getID().equals(CellValue.MINE) && x == firstX && y == firstY)
			lose(board);
		// 若被点击单元格周围都没有雷，则递归展开四周单元格
		else if (board.getCell(x, y).getMineCount() == 0)
			selectSurroundingCell(firstX, firstY, x, y, board);
	}

	private void selectSurroundingCell(int firstX, int firstY, int x, int y, Board board)
	{
		// 遍历单元格四周，若发生数组越界错误则忽略本次循环
		for (int i = (y - 1); i <= (y + 1); i++)
		{
			for (int j = (x - 1); j <= (x + 1); j++)
			{
				try
				{
					if (board.getCell(j, i).isSelected())
					{
						continue;
					}
					if (i == y && j == x)
					{
						continue;
					}
					selectCell(firstX, firstY, j, i, board);
				}
				catch (IndexOutOfBoundsException ex)
				{
					continue;
				}
			}
		}
	}

	private int[] getClickedIndex(ImageView cell, Board board)
	{
		int[] index = new int[2];
		for (int i = 0; i < board.getYSize(); i++)
		{
			for (int j = 0; j < board.getXSize(); j++)
			{
				if (cell.equals(this.cell[i][j]))
				{
					index[0] = j;
					index[1] = i;
				}
			}
		}
		return index;
	}

	private void win()
	{
		end = true;
		win = true;
	}

	private void lose(Board board)
	{
		displayAll(board);// 展开所有单元格
		end = true;
		win = false;
	}

	public boolean isWin()
	{
		return win;
	}

	public boolean isEnd()
	{
		return end;
	}

	// 展开所有单元格
	private void displayAll(Board board)
	{
		for (int i = 0; i < board.getYSize(); i++)
		{
			for (int j = 0; j < board.getXSize(); j++)
			{
				if (!(board.getCell(j, i).isSelected()))
					this.cell[i][j].setImage(board.getCell(j, i).getSelectedImage());
			}
		}
	}

	public ImageView getCell(int x, int y)
	{
		return cell[y][x];
	}

	public ImageView[][] getCell()
	{
		return cell;
	}
}
