package BestPractices;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import NamingConventions.LowerClassFix;
import plugin_validar.views.Problem;

public class BP02 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "Clase abstracta sin hijos(BP02)";
	
	public BP02 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	
	public ProblemType getProblemType() { return ProblemType.BEST_PRACTICE; }
	
	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			Boolean uninstantiable = true;
			if (classifier instanceof EClass) {
			     if (((EClass) classifier).isAbstract()) {
			    	 for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
			    	 	if (((EClass) classifier).isSuperTypeOf((EClass) classifier2) && !classifier2.equals(classifier)) {
			    	 		uninstantiable = false;
			    	 	}
			    	 			    	 		
			    	 }
			    	 if (uninstantiable == true) {
			    		 Problem problem = new Problem();
			    		 problem.setDescription("La clase " +classifier.getName() +
								   "(" +classifier.getClassifierID()  +")" + " es abstracta sin hijos");
			    		 IQuickfix fix = new BP02Fix(metamodelo,classifier);
						 problem.addQuickfix(fix);
						 problems.add(problem);
			    	 }
						   
			     } 
			}
			
		}		
		
		return problems;
	}
	
}
