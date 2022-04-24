package BestPractices;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.IQuickfix;

public class BP05Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public BP05Fix (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		(((EClass) classifier)).setAbstract(true);
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Convertir la clase " + classifier.getName() + " en clase abstract";
	}
}
