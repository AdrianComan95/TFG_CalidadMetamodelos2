package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class D09 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(D09) Clase contenidad en dos clases, cuando está obligatoriamente en una de ellas";

	public D09 (EPackage metamodelo) {
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
			if (classifier instanceof EClass) {
		    	  for (EReference reference : ((EClass)classifier).getEReferences()) {
		    		  if (reference.isContainment()) {
		    			  
		    			  //TODO
		    			  
		    			  Problem problem = new Problem();
	    				  problem.setDescription("La propiedad (atributo o referencia) "+ feature.getName() + " de la clase " + classifier.getName() + "(" 
	    				  + classifier.getClassifierID() + ")" + "tiene una cardanilidad maxima menor que 0");
			    		  IQuickfix fix = new D08Fix(metamodelo,classifier, feature);
						  problem.addQuickfix(fix);
						  problems.add(problem);	
		    		  }
		    		  
		         }	    		   	  			    	  
			 }
		}
		
		return problems;
	}

}
