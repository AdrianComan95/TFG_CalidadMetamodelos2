package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class D05Fix1 implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public D05Fix1 (EPackage metamodelo, EClassifier classifier) {
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
		return "Eliminar la clase " + classifier.getName();
	}

}
