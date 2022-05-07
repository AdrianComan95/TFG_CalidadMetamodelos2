package NamingConventions;

import java.io.IOException;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Shell;

import Interfaces.IQuickfix;
import Utils.AreaDialogN05Fix;

public class N05Fix implements IQuickfix {

	private EPackage metamodelo;
	private EStructuralFeature element;

	public N05Fix(EPackage metamodelo, EStructuralFeature element) {
		this.element = element;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		AreaDialogN05Fix dialog = new AreaDialogN05Fix(Shell.internal_new(null, 0), element.getName());
		dialog.open();
		String newName = dialog.getNewName();
		if (newName != null) {
			if (!newName.isEmpty()) {
				element.setName(newName);
				try {
					metamodelo.eResource().save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public String getDescription() {
		return "Cambiar el nombre del elemento";
	}

}
