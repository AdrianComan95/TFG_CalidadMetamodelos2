package Utils;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.swt.widgets.Shell;

import Interfaces.IQuickfix;

public class CreateSubclassFix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public CreateSubclassFix (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		
		AreaDialogBP02Fix3 dialog = new AreaDialogBP02Fix3(Shell.internal_new(null, 0));
		dialog.open();
		String newName = dialog.getNewName();
		
		if (newName != null) {
			if (!newName.isEmpty()) {
				EClass newClass = EcoreFactory.eINSTANCE.createEClass();
				newClass.setName(newName);
				newClass.setAbstract(false);
				newClass.getESuperTypes().add((EClass) classifier);
				
				metamodelo.getEClassifiers().add(newClass);
				
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
		return "Crear una subclase de " + classifier.getName();
	}
}
