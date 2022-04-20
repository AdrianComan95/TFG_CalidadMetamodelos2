package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class D03Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EClassifier children;
	private EPackage metamodel;
	
	public D03Fix (EPackage metamodelo, EClassifier classifier, EClassifier children) {
		this.classifier = classifier;
		this.metamodel = metamodelo;
		this.children = children;
	}
	@Override
	public void execute() {
		
		((EClass)children).getEAttributes().addAll(((EClass)classifier).getEAttributes());
		((EClass)children).getEReferences().addAll(((EClass)classifier).getEReferences());

		EcoreUtil.delete(classifier);
		try {
			metamodel.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Borrar  la clase " + classifier.getName();
	}

}
