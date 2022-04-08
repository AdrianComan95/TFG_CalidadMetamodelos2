package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class D03Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public D03Fix (EPackage metamodelo, EClassifier classifier, EClassifier children) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}
	@Override
	public void execute() {
		//Quiter ESuperType a children
		
		EcoreUtil.delete(classifier);
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
