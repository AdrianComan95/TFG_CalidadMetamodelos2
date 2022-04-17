package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.IQuickfix;

public class D04Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	private EReference reference;
	
	public D04Fix (EPackage metamodelo, EClassifier classifier, EReference reference) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
		this.reference = reference;
	}
	@Override
	public void execute() {
		((EClass)classifier).getEAllReferences().remove(reference);
		
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Borrar  la referencia a " + reference.getEReferenceType().getName() + " en la clase "
				+ classifier.getName();
	}

}
