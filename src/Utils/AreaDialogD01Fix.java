package Utils;


import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AreaDialogD01Fix extends TitleAreaDialog {

    private Text txtNewName;

    private String oldName;
    private String className;
    private String newName;

    public AreaDialogD01Fix(Shell parentShell) {
        super(parentShell);
    }
    
    public AreaDialogD01Fix(Shell parentShell, String oldName, String className) {
        super(parentShell);
        this.oldName = oldName;
        this.className = className;
    }

    @Override
    public void create() {
        super.create();
        setTitle("Nuevo nombre para el atributo '" + this.oldName + "'" + " en la clase " + className);
        setMessage("Ya existe otro atributo con el mismo nombre en la misma jerarquia", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createFirstName(container);

        return area;
    }

    private void createFirstName(Composite container) {
        Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("Nuevo nombre");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        txtNewName = new Text(container, SWT.BORDER);
        txtNewName.setLayoutData(dataFirstName);
    }



    @Override
    protected boolean isResizable() {
        return true;
    }

    // save content of the Text fields because they get disposed
    // as soon as the Dialog closes
    private void saveInput() {
        newName = txtNewName.getText();
    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    public String getNewName() {
        return newName;
    }
}