package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import Interfaces.IQuickfix;

public class D08Fix implements IQuickfix {

	private EClassifier classifier;
	private EPackage metamodelo;
	private EStructuralFeature feature;

	public D08Fix(EPackage metamodelo, EClassifier classifier, EStructuralFeature feature) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
		this.feature = feature;
	}

	@Override
	public void execute() {

		((EClass) classifier).getEStructuralFeatures().remove(feature);

		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Borrar  la pripiedad (atributo o referencia) " + feature.getName();
	}

}
