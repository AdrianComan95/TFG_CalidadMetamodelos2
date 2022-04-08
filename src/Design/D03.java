package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import BestPractices.BP01Fix;
import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import NamingConventions.LowerClassFix;
import plugin_validar.views.Problem;

public class D03 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "Clase abstracta con un solo hijo (D03)";

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
			    		 IQuickfix fix = new D03Fix(metamodelo,classifier,children);
						 problem.addQuickfix(fix);
						 problems.add(problem);
			    	 }
						   
			     } 
			}
			
		}			
		
		return problems;
	}

}
