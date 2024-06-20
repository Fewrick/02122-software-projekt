package dk.dtu.controller;

import javafx.scene.control.Button;

public class SudokuButton extends Button {
	private boolean editable;

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

}
