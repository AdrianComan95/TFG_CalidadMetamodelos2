package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import Utils.ChangeClassToConcreteFix;
import Utils.CreateSubclassFix;
import plugin_validar.views.Problem;

public class D03 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(D03) Ninguna clase abstracta es superior a una sola";

	public D03 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	
	public ProblemType getProblemType() { return ProblemType.DESIGN; }

	@Override
	public List<Problem> check() {
		
		List<Problem> problems = new ArrayList<Problem>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		
		for (EClassifier classifier : classifiers) {
			Integer childrens = 0;
			EClass children = null;
			if (classifier instanceof EClass) {
			     if (((EClass) classifier).isAbstract()) {
			    	 for (EClassifier classifier2 : classifiers) {
			    	 	if (((EClass) classifier).isSuperTypeOf((EClass) classifier2) && !classifier2.equals(classifier)) {
			    	 		childrens++;
			    	 		children = (EClass) classifier2;
			    	 	}
			    	 			    	 		
			    	 }
			    	 if (childrens == 1) {
			    		 Problem problem = new Problem();
			    		 problem.setDescription("La clase " +classifier.getName() +
								   "(" +classifier.getClassifierID()  +")" + " es abstracta con un solo hijo (" +children.getName() + ")");
			    		 IQuickfix fix1 = new D03Fix(metamodelo,classifier,children);
						 problem.addQuickfix(fix1);
						 IQuickfix fix2 = new CreateSubclassFix(metamodelo,classifier);
						 problem.addQuickfix(fix2);
			    		 IQuickfix fix3 = new ChangeClassToConcreteFix(metamodelo,classifier);
						 problem.addQuickfix(fix3);
						 problems.add(problem);
			    	 }
						   
			     } 
			}
			
		}			
		
		return problems;
	}

}
