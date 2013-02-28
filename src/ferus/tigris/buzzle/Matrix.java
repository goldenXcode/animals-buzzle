package ferus.tigris.buzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ferus.tigris.buzzle.personages.AbstractBehavior;
import ferus.tigris.buzzle.personages.NullBehavior;

import android.graphics.Point;

public class Matrix {
	private int columns = 6;
	private int rows = 6;
	private Vector< Vector<AbstractBehavior> > marks;

	public Matrix(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		initMatrix();
		fillMatrixByNullElements();
	}

	
	public Matrix() {
		initMatrix();
		fillMatrixByNullElements();
	}


	protected void fillMatrixByNullElements() {
		for(int x = 0; x < columns; x++) {
			for(int y = 0; y < rows; y++) {
				AbstractBehavior mark = new NullBehavior();
				this.marks.elementAt(x).setElementAt(mark, y);
			}
		}
	}


	protected void initMatrix() {
		marks = new Vector< Vector<AbstractBehavior> >();
		marks.setSize(columns);
		for(int i = 0; i < columns; i++) {
			Vector<AbstractBehavior> column = new Vector<AbstractBehavior>();
			column.setSize(rows);
			marks.setElementAt(column, i);
		}
	}

	public List<AbstractBehavior> getMarks() {
		List<AbstractBehavior> list = new ArrayList<AbstractBehavior>();
		/*		for(int y = 0; y < rows; y++)
			for(int x = 0; x < columns; x++) {*/
		for(int x = columns - 1; x >= 0; x--) {
			for(int y = rows - 1; y >= 0; y--) {
				if(!list.contains(getMark(x, y))) {
					list.add(getMark(x, y));
				}
			}
		}
		return list;
	}

	public AbstractBehavior getMark(int x, int y) {
		AbstractBehavior mark = marks.elementAt(x).elementAt(y);
		return mark;
	}

	public void insertMark(int x, int y, AbstractBehavior mark) {
		this.marks.elementAt(x).setElementAt(mark, y);
		mark.saveAt(this, new Point(x, y));
	}
	
	public int columns() {
		return columns;
	}

	public int rows() {
		return rows;
	}

	public Matrix copyMatrix() {
		Matrix m = new Matrix();
		for(int x = 0; x < columns; x++)
			for(int y = 0; y < rows; y++)
				m.marks.elementAt(x).setElementAt(getMark(x, y), y);
		return m;
	}

	public void restoreFrom(Matrix prevState) {
		for(int x = columns - 1; x >= 0; x--)
			for(int y = rows - 1; y >= 0; y--)
				insertMark(x, y, prevState.getMark(x, y));
	}


	public void swap(Point p1, Point p2) {
		AbstractBehavior b = getMark(p1);
		insertMark(p1, getMark(p2));
		insertMark(p2, b);
	}


	private void insertMark(Point p, AbstractBehavior mark) {
		insertMark(p.x, p.y, mark);
	}


	public AbstractBehavior getMark(Point position) {
		return getMark(position.x, position.y);
	}

}
