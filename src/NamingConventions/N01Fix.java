package NamingConventions;

import org.eclipse.emf.ecore.EClassifier;

import QuickFixes.IQuickfix;

public class N01Fix implements IQuickfix {
	
	EClassifier classifier;
	
	public N01Fix (EClassifier classifier) {
		this.classifier = classifier;
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
