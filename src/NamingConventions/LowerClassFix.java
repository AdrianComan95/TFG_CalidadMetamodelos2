package NamingConventions;

import java.io.IOException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.IQuickfix;

public class LowerClassFix implements IQuickfix {
	
	EClassifier classifier;
	EPackage metamodelo;
	
	public LowerClassFix (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		this.classifier.setName(upperCaseFirst(classifier.getName()));
		try {
			metamodelo.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Cambiar el nombre de " + classifier.getName() + " a " + upperCaseFirst(classifier.getName()); 
	}
	
	public static String upperCaseFirst(String val) {
	    char[] arr = val.toCharArray();
	    arr[0] = Character.toUpperCase(arr[0]);
	    return new String(arr);
	}

}
