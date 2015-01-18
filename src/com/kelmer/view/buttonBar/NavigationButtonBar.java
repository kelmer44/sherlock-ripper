package com.kelmer.view.buttonBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class NavigationButtonBar extends Composite{

    private Button prevButton;
    private Label imageName;
    private Button nextButton;

    public NavigationButtonBar(Composite parent, int style) {
        super(parent, style);
        this.setLayout(new GridLayout(3, false));
        
        initComponents();
    }

    private void initComponents() {
        prevButton = new Button(this, SWT.NONE);
        prevButton.setText("Previous");

        imageName = new Label(this, SWT.NONE);
        imageName.setText("Image Name");

        nextButton = new Button(this, SWT.NONE);
        nextButton.setText("Next");
    }

    public Button getPrevButton() {
        return prevButton;
    }

    public void setPrevButton(Button prevButton) {
        this.prevButton = prevButton;
    }

    public Label getImageName() {
        return imageName;
    }

    public void setImageName(Label imageName) {
        this.imageName = imageName;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public void setNextButton(Button nextButton) {
        this.nextButton = nextButton;
    }

}
