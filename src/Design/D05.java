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
import plugin_validar.views.Problem;

public class D05 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(D05) No hay clases irrelevantes (abstractas y subclase de una clase concreta)";

	public D05 (EPackage metamodelo) {
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
			if (classifier instanceof EClass && ((EClass)classifier).isAbstract()) {
		    	  for (EClass parent : ((EClass)classifier).getESuperTypes()) {
		    		  if(!parent.isAbstract()) {
	    				  Problem problem = new Problem();
	    				  problem.setDescription("La clase " + classifier.getName() + "(" + classifier.getClassifierID() + ")"
			    				  + "es irrelevante, es subclase de " + parent.getName() + ", la cual es abstracta");
			    		  IQuickfix fix1 = new D05Fix1(metamodelo,classifier);
						  problem.addQuickfix(fix1);
						  IQuickfix fix2 = new ChangeClassToConcreteFix(metamodelo,classifier);
						  problem.addQuickfix(fix2);
						  problems.add(problem);	    			
		    		  }
		    	  }   	  			    	  
			 }
		}
		
		return problems;
	}

}
