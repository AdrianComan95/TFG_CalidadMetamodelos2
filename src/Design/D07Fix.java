package Design;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Interfaces.IQuickfix;

public class D07Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	private EAttribute attribute;
	
	public D07Fix (EPackage metamodelo, EClassifier classifier, EAttribute attribute) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
		this.attribute = attribute;
	}
	@Override
	public void execute() {
		EcoreUtil.delete(attribute);
		
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Borrar el atributo " + attribute.getName() + " de la clase "
				+ classifier.getName();
	}

}
