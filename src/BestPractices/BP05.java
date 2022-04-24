package BestPractices;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class BP05 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(BP05) Clase no abstracta con subclases y sin superclases. No puede tener referencias";
	
	public BP05 (EPackage metamodelo) {
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
			if (classifier instanceof EClass) {
			     if (!((EClass) classifier).isAbstract() 
			    		 && ((EClass)classifier).getEAllSuperTypes().isEmpty()
			    		 && !((EClass)classifier).getEAllReferences().isEmpty()) {	
			    	 
				    		 Problem problem = new Problem();
				    		 problem.setDescription("La clase " +classifier.getName() + "(" + classifier.getClassifierID() + ")"
				    				 + " es concreta, sin superclases y con referencias");
				    		 IQuickfix fix1 = new BP05Fix(metamodelo,classifier);
							 problem.addQuickfix(fix1);
							 
			     }
						   
			 } 
		}
	
		
		return problems;
	}
	
}
