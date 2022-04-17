package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class D02Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public D02Fix (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}
	@Override
	public void execute() {
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

