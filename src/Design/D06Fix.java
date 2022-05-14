package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class D06Fix implements IQuickfix {

	private EClassifier classifier;
	private EPackage metamodelo;
	private EReference reference;

	public D06Fix(EPackage metamodelo, EClassifier classifier, EReference reference) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
		this.reference = reference;
	}

	@Override
	public void execute() {
		EcoreUtil.delete(reference);

		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Borrar  la referencia " + reference.getName() + " en la clase " + classifier.getName();
	}

}
