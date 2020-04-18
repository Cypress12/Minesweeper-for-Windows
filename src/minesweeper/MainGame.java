package minesweeper;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

public class MainGame extends GridPane
{
	private ImageView[][] cell;
	private boolean win;// ��Ϸ�Ƿ�ʤ��
	private boolean end;// ��Ϸ�Ƿ����
	public static final int CELL_SIZE = 30;// ��Ԫ���С
	public boolean first = false;// �Ƿ�Ϊ��һ�ε������

	public MainGame(Board board, Difficulty difficulty)
	{
		board.init(difficulty);// ��ʼ������
		// ��ʼ�����
		this.setGridLinesVisible(true);
		this.setAlignment(Pos.CENTER);
		this.setHgap(2);
		this.setVgap(2);
		cell = new ImageView[board.getYSize()][board.getXSize()];
		// ��������״̬��ʾÿ����Ԫ��ͼƬ
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
		assignEvent(board);// Ϊ�������¼�
	}

	private void assignEvent(Board board)
	{
		for (ImageView[] cellRow : this.getCell())
		{
			for (ImageView cell : cellRow)
			{
				cell.setOnMouseClicked(e ->
				{
					if (!first)// ���ǵ�һ�ε����������Ϊ��
						first = true;
					int[] index = getClickedIndex(cell, board);
					int x = index[0];
					int y = index[1];
					if (e.getButton().equals(MouseButton.SECONDARY))// ����Ҽ�
					{
						// ����Ԫ��δ��ѡ�񣬱�ǵ�Ԫ��
						if (!(board.getCell(x, y).isSelected()))
							flag(x, y, board);
					}
					else// ������
					{
						// ����Ԫ��δ����ǣ�չ����Ԫ��
						if (!(board.getCell(x, y).isFlagged()))
						{
							selectCell(x, y, x, y, board);// չ����Ԫ��
						}
					}
					// ������ʤ����������Ϸ����
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
		board.getCell(x, y).flag();// ���µ�Ԫ����״̬
		// ����Ԫ�񱻱�ǣ���ʾ���ͼƬ��������ʾδ���ͼƬ
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
		this.cell[y][x].setImage(board.getCell(x, y).getSelectedImage());// ���ñ�ѡ��ͼƬ
		board.getCell(x, y).select();// ���µ�Ԫ��ѡ��״̬
		// ���������Ԫ�����ף���Ϸʧ��
		if (board.getCell(x, y).getID().equals(CellValue.MINE) && x == firstX && y == firstY)
			lose(board);
		// ���������Ԫ����Χ��û���ף���ݹ�չ�����ܵ�Ԫ��
		else if (board.getCell(x, y).getMineCount() == 0)
			selectSurroundingCell(firstX, firstY, x, y, board);
	}

	private void selectSurroundingCell(int firstX, int firstY, int x, int y, Board board)
	{
		// ������Ԫ�����ܣ�����������Խ���������Ա���ѭ��
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
		displayAll(board);// չ�����е�Ԫ��
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

	// չ�����е�Ԫ��
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
