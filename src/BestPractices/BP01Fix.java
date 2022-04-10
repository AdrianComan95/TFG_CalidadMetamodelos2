package BestPractices;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.IQuickfix;

public class BP01Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	private EClass parent;
	
	public BP01Fix (EPackage metamodelo, EClassifier classifier, EClass parent) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
		this.parent = parent;
	}

	@Override
	public void execute() {
		System.out.println(((EClass)classifier).getESuperTypes().remove(parent));
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Eliminar la relación de SuperType de " +classifier.getName() + " con " + parent.getName() ;
	}

}
