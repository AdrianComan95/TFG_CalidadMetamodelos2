package Utils;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.IQuickfix;

public class ChangeClassToConcreteFix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public ChangeClassToConcreteFix (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		(((EClass) classifier)).setAbstract(false);
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Convertir la clase " + classifier.getName() + " en concreta";
	}
}
