package BestPractices;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import plugin_validar.views.IProblem;

public class BP02 implements IProblem {
	
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
	public List<String> check() {
		List<String> problem = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			Boolean uninstantiable = true;
			if (classifier instanceof EClass) {
			     if (((EClass) classifier).isAbstract()) {
			    	 for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
			    	 	if (((EClass) classifier).isSuperTypeOf((EClass) classifier2) && !classifier2.equals(classifier)) {
			    	 		uninstantiable = false;
			    	 	}
			    	 			    	 		
			    	 }
			    	 if (uninstantiable == true)
						   problem.add("La clase " +classifier.getName() +
								   "(" +classifier.getClassifierID()  +")" + " es abstracta sin hijos");
			     } 
			}
			
		}		
		
		return problem;
	}
	
}
