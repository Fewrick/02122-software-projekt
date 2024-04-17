package dk.dtu.controller;

import javafx.scene.control.Button;

public class SudokuButton extends Button {
	private boolean editable;
	private boolean draft;

	public SudokuButton(int i) {
		super();
		this.editable = i == 0;
		this.draft = false;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setDraft(boolean value) {
		this.draft = value;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
