package com.kelmer.view;

import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import com.kelmer.formats.resource.image.express.bg.BGImage;
import com.kelmer.formats.resource.image.holmes.rrm.RRMImage;
import com.kelmer.view.buttonBar.NavigationButtonBar;
import com.kelmer.view.imageViewer.ImageViewerPanel;
import com.kelmer.view.tree.FileTree;

public class MainWindow {

    private static final Logger logger = Logger.getLogger(MainWindow.class);

    protected Shell shell;

    private FileTree fileTree;

    private ImageViewerPanel imageViewer;

    private NavigationButtonBar navButtonBar;

    private Composite contentPanel;

    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setMinimumSize(new Point(800, 600));
        shell.setSize(450, 300);
        shell.setText("SWT Application");
        shell.setLayout(new BorderLayout(0, 0));


        createMenu();

        createToolBar();

        createContent();

        fileTree = new FileTree(shell, SWT.BORDER, imageViewer);
    }

    public FileTree getFileTree() {
        return fileTree;
    }

    public void setFileTree(FileTree fileTree) {
        this.fileTree = fileTree;
    }

    public ImageViewerPanel getImageViewer() {
        return imageViewer;
    }

    public void setImageViewer(ImageViewerPanel imageViewer) {
        this.imageViewer = imageViewer;
    }

    public NavigationButtonBar getNavButtonBar() {
        return navButtonBar;
    }

    public void setNavButtonBar(NavigationButtonBar navButtonBar) {
        this.navButtonBar = navButtonBar;
    }

    private void createContent() {
        contentPanel = new Composite(shell, SWT.NONE);
        contentPanel.setLayout(new FillLayout(SWT.VERTICAL));

        imageViewer = new ImageViewerPanel(contentPanel, SWT.NONE);
        imageViewer.setLayout(new FillLayout(SWT.HORIZONTAL));
        navButtonBar = new NavigationButtonBar(contentPanel, SWT.NONE);
    }

    private void createMenu() {
        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);

        MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
        fileMenu.setText("File");

        Menu menu = new Menu(fileMenu);
        fileMenu.setMenu(menu);

        MenuItem openMenuItem = new MenuItem(menu, SWT.NONE);
        openMenuItem.setText("Open...");

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem exitMenuItem = new MenuItem(menu, SWT.NONE);
        exitMenuItem.setText("Exit");
    }

    private void createToolBar() {
        ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
        toolBar.setLayoutData(BorderLayout.NORTH);

        ToolItem openFileToolItem = new ToolItem(toolBar, SWT.NONE);
        openFileToolItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/sun/java/swing/plaf/motif/icons/TreeOpen.gif"));
        openFileToolItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog fd = new FileDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
                fd.setText("Open");
                fd.setFilterPath("C:/");
                String[] filterExt = { "*.RRM" };
                fd.setFilterExtensions(filterExt);
                String selected = fd.open();
                logger.debug(selected);
                if (selected != null) {
                    RRMImage rrm = new RRMImage(selected);
                    BufferedImage bImage = rrm.toBitmap();
                    imageViewer.setImage(bImage);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        openFileToolItem.setText("Open file");

        ToolItem openFolderToolItem = new ToolItem(toolBar, SWT.NONE);
        openFolderToolItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dd = new DirectoryDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
                dd.setText("Open folder");
                dd.setFilterPath("G:\\Aventuras Gráficas\\The Last Express\\util\\BG2BMP\\test");
                String selected = dd.open();
                if (selected != null) {
                    File folder = new File(selected);
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
                            File f = listOfFiles[i];
                            logger.debug("File " + f);
                            if(FilenameUtils.getExtension(f.getName()).equals("RRM")){
                                MainWindow.this.getFileTree().addFile(new RRMImage(f.getAbsolutePath()));
                            }
                            else if (FilenameUtils.getExtension(f.getName()).equals("BG")) {
                                MainWindow.this.getFileTree().addFile(new BGImage(f.getAbsolutePath()));
                            }
                        }
                    }
                }

            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        openFolderToolItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/sun/java/swing/plaf/windows/icons/NewFolder.gif"));
        openFolderToolItem.setText("Open folder");

        ToolItem openResourceToolItem = new ToolItem(toolBar, SWT.NONE);
        openResourceToolItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
        openResourceToolItem.setHotImage(null);
        openResourceToolItem.setText("Open resource");
    }

}
