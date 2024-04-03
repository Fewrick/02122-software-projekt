package dk.dtu.controller;

import javafx.scene.control.Button;

public class SudokuButton extends Button {
	private boolean editable;
	private int MyValue;

	public SudokuButton(int i) {
		super();
		this.editable = i == 0;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	// A method to get the value of a button
	public int getMyValue() {
		return MyValue;
	}

	// A method to change the value of a button
	public void setMyValue(int MyValue) {
		this.MyValue = MyValue;
	}
}
