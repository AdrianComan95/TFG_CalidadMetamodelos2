package BestPractices;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class BP02Fix1 implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public BP02Fix1 (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		EcoreUtil.delete((((EClass) classifier)));
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Borrar  la clase " + classifier.getName();
	}
}
