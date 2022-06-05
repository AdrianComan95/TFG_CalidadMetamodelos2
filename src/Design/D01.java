package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class D01 implements ICriterion {
	
	EPackage metamodelo;	

	public D01 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return "(D01) Atributo no se repite entre las clases de la misma jerarquia";
	}

	
	public ProblemType getProblemType() { return ProblemType.DESIGN; }

	@Override
	public List<Problem> check() {
		
		List<Problem> problems = new ArrayList<Problem>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
			      for (EAttribute attribute : ((EClass)classifier).getEAttributes()) {
			    	  for (EClass superClass : ((EClass)classifier).getEAllSuperTypes()) {
			    		  for (EAttribute attribute2 : ((EClass)superClass).getEAttributes()) {
			    			  if (attribute.getName().equals(attribute2.getName())) {
			    				  Problem problem = new Problem();
			    				  problem.setDescription("El atributo con el nombre '" + attribute.getName() + 
					    				  "' se repite en las clases " + classifier.getName() + "(" + classifier.getClassifierID() + ")"
					    				  + " y " + superClass.getName() + "(" + superClass.getClassifierID() + ")");
					    		  IQuickfix fix1 = new D01Fix(metamodelo,attribute);
								  IQuickfix fix2 = new D01Fix(metamodelo,attribute2);
								  problem.addQuickfix(fix1);
								  problem.addQuickfix(fix2);
								  problems.add(problem);
			    			  }
			    		  }
			    	  }   	  			    	  
			      }
			}
		}
				
		
		return problems;
	}

}
