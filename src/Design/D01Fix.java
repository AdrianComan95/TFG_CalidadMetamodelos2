package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.swt.widgets.Shell;

import Interfaces.IQuickfix;
import Utils.AreaDialogD01Fix;

public class D01Fix implements IQuickfix {
	
	private EPackage metaModel;
	private EAttribute attribute;
	
	
	public D01Fix (EPackage metaModel, EAttribute attribute) {
		this.metaModel = metaModel;
		this.attribute = attribute;
	}
	@Override
	public void execute() {
		AreaDialogD01Fix dialog = new AreaDialogD01Fix(Shell.internal_new(null, 0), attribute.getName(), attribute.getEContainingClass().getName());
		dialog.open();
		String newName = dialog.getNewName();
		if (newName == null || !newName.isEmpty()) {
			try {
				metaModel.eResource().save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String getDescription() {
		return "Cambiar el nombre del atributo '" + attribute.getName() 
				+ "' en la clase " + attribute.getEContainingClass().getName();
	}

}
