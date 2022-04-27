package Metrics;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import Interfaces.IQuickfix;
public class M04Fix implements IQuickfix {

	private EClassifier classifier;
	private EPackage metamodelo;
	private EAttribute attribute;

	public M04Fix(EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		//TODO
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "//TODO";
	}

}
