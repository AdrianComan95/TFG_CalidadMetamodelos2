package NamingConventions;

import java.io.IOException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Shell;

import Interfaces.IQuickfix;
import Utils.AreaDialogN05Fix;

public class N09Fix implements IQuickfix {

	private EPackage metamodelo;
	private EClassifier classifier;

	public N09Fix(EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		AreaDialogN05Fix dialog = new AreaDialogN05Fix(Shell.internal_new(null, 0), classifier.getName());
		dialog.open();
		String newName = dialog.getNewName();
		if (newName != null) {
			if (!newName.isEmpty()) {
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
		return "Cambiar el nombre de la clase " + classifier.getName();
	}

}
