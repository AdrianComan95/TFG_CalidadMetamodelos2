package NamingConventions;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;

import Interfaces.IQuickfix;

public class N02Fix implements IQuickfix {

	private EClassifier classifier;
	private EClassifier classifier2;
	private EPackage metamodelo;
	private EAttribute attribute;

	public N02Fix(EPackage metamodelo, EClassifier classifier, EAttribute attribute, EClassifier classifier2) {
		this.classifier = classifier;
		this.classifier2 = classifier2;
		this.metamodelo = metamodelo;
		this.attribute = attribute;
	}

	@Override
	public void execute() {
		((EClass) classifier).getEStructuralFeatures().remove(attribute);

		EReference reference = EcoreFactory.eINSTANCE.createEReference();
		reference.setName(classifier2.getName());
		reference.setEType(classifier2);

		((EClass) classifier).getEStructuralFeatures().add(reference);

		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getDescription() {
		return "Convertir atributo en referencia";
	}

}
