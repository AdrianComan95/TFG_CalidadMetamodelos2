package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class D07 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(D07) Atributos heredados anulados";

	public D07 (EPackage metamodelo) {
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
		    	  for (EAttribute attribute1 : ((EClass)classifier).getEAttributes()) {
		    		  for (EAttribute attribute2 : ((EClass)classifier).getEAllAttributes()) {
		    			  int count = 0;
		    			  if (attribute1.getName().equals(attribute2.getName())) {
		    				  count ++;
		    			  }
		    			  //SI COUNT LLEGA A DOS, ESE ATRIBUTO LO DEFINE LA CLASE Y ALGUNO DE SUS PADRES
		    			  if (count == 2) {
		    				  Problem problem = new Problem();
		    				  problem.setDescription("La atributo "+ attribute1.getName() + " de la clase " + classifier.getName() + "(" 
		    				  + classifier.getClassifierID() + ")" + "es un atributo heredado y anulado en la clase");
				    		  IQuickfix fix = new D07Fix(metamodelo,classifier, attribute1);
							  problem.addQuickfix(fix);
							  problems.add(problem);	
		    			  }
		    		  }
    				  	    			  		    		  
		    	  }   	  			    	  
			 }
		}
		
		return problems;
	}

}
