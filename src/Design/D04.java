package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class D04 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(D04) No existen ciclos de composición";

	public D04 (EPackage metamodelo) {
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
		List<EClass> classes = new ArrayList<EClass>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
		    	  for (EReference reference : ((EClass)classifier).getEReferences()) {
		    		  if(reference.isContainment()) {
		    			  if (classes.contains(reference.getEReferenceType())) {
		    				  Problem problem = new Problem();
		    				  problem.setDescription("La clase " + classifier.getName() + "(" + classifier.getClassifierID() + ")"
				    				  + "genera un ciclo de composición");
				    		  IQuickfix fix = new D04Fix(metamodelo,classifier,reference);
							  problem.addQuickfix(fix);
							  problems.add(problem);
		    			  }else {
		    				  classes.add(reference.getEReferenceType());
		    			  }
		    		  }
		    	  }   	  			    	  
			 }
		}
		
		return problems;
	}

}
