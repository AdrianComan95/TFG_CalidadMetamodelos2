package BestPractices;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import QuickFixes.IQuickfix;

public class BP02Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	
	public BP02Fix (EPackage metamodelo, EClassifier classifier) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
