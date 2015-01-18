package com.kelmer.view.tree;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;

import com.kelmer.formats.AnyFile;
import com.kelmer.formats.FileTypes;
import com.kelmer.formats.resource.image.core.ImageResource;
import com.kelmer.view.imageViewer.ImageViewerPanel;

public class FileTree extends Tree {

    private static final Logger logger = Logger.getLogger(FileTree.class);

    TreeItem rootItem = null;
    // FIXME: ESTO ES TRAMPA....
    ImageViewerPanel imageViewer = null;

    public FileTree(Composite parent, int style) {
        super(parent, style);
        this.setLayoutData(BorderLayout.WEST);
    }

    public FileTree(Shell shell, int border, ImageViewerPanel imageViewer) {
        this(shell, border);
        this.imageViewer = imageViewer;
    }

    public void addFile(final AnyFile file) {
        if (null == rootItem) {
            addRoot();
        }
        TreeItem item = new TreeItem(rootItem, SWT.NONE);
        item.setData(file);
        item.setText(file.getFileName());

       this.addListener(SWT.MouseUp, new Listener() {
        
        @Override
        public void handleEvent(Event event) {
           TreeItem[] item = FileTree.this.getSelection();
           if(item.length >0 ){
               TreeItem selection = item[0];
               if(selection != rootItem){
                   AnyFile file = (AnyFile) selection.getData();
                   if(file.getType() == FileTypes.IMAGE)
                   {
                       ImageResource image = (ImageResource) file;
                       BufferedImage bImage = image.toBitmap();
                       imageViewer.setImage(bImage);
                   }
               }
           }
           
        }
    });
    }

    private void addRoot() {
        rootItem = new TreeItem(this, SWT.NONE);
        rootItem.setText("ROOT");
    }

    @Override
    protected void checkSubclass() {
        // TODO Auto-generated method stub
    }

}
