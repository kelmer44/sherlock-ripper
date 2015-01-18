package com.kelmer.view;

import java.awt.image.BufferedImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.kelmer.formats.resource.image.core.ImageResource;
import com.kelmer.formats.resource.image.holmes.rrm.RRMImage;

public class RunnerHelper {

    public static void main(String[] args) {
        Display display = Display.getDefault();
        Shell shell =  new Shell();
        shell.setSize(450, 300);
        shell.setText("SWT Application");
        
        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBounds(10, 10, 414, 242);
        lblNewLabel.setText("New Label");
        
        RRMImage rrm = new RRMImage("G:\\Aventuras Gráficas\\Sherlock Holmes\\Escalpelo Mellado\\Spanish\\ESPANOL\\RES01.RRM");
        BufferedImage bImage = rrm.toBitmap();

        ImageData convertToSWT = ImageResource.convertToSWT(bImage);
        lblNewLabel.setImage(new Image(display, convertToSWT));
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

}
