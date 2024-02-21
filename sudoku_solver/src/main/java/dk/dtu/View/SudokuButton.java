package dk.dtu.View;

import javafx.scene.control.Button;

public class SudokuButton extends Button {

    private int MyValue;

	public SudokuButton(int MyValue) {
		this.MyValue = MyValue;
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
