package com.kelmer.view.imageViewer;

import java.awt.image.BufferedImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.kelmer.formats.resource.image.core.ImageResource;

public class ImageViewerPanel extends Composite {

    private Label imageLabel;

    public ImageViewerPanel(Composite parent, int style) {
        super(parent, SWT.BORDER);
        initComponents();
    }

    public void initComponents() {
        setLayout(new GridLayout(2, false));
        imageLabel = new Label(this, SWT.BORDER);
        imageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        imageLabel.setText("New Label");
        
        Composite composite = new Composite(this, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        
        Label fileNameLabel = new Label(composite, SWT.NONE);
        fileNameLabel.setText("New Label");
        
        Label fileSizeLabel = new Label(composite, SWT.NONE);
        fileSizeLabel.setText("New Label");
        
        Button btnSaveBitmap = new Button(composite, SWT.NONE);
        btnSaveBitmap.setText("Save Bitmap");
        
        Button btnZoomX = new Button(composite, SWT.NONE);
        btnZoomX.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        btnZoomX.setText("Zoom x2");
        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);

    }

    public Label getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(Label imageLabel) {
        this.imageLabel = imageLabel;
    }

    
    public void setImage(BufferedImage image) {
        
        ImageData convertToSWT = ImageResource.convertToSWT(image);
        this.imageLabel.setImage(new Image(Display.getCurrent(), convertToSWT));
        this.imageLabel.setSize(image.getWidth(), image.getHeight());
    }
}
