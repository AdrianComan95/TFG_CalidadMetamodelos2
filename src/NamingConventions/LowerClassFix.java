package NamingConventions;

import org.eclipse.emf.ecore.EClassifier;

import QuickFixes.IQuickfix;

public class LowerClassFix implements IQuickfix {
	
	EClassifier classifier;
	
	public LowerClassFix (EClassifier classifier) {
		this.classifier = classifier;
	}

	@Override
	public void execute() {
		this.classifier.setName(upperCaseFirst(classifier.getName()));
		System.out.println(classifier);
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
