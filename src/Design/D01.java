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
	
	String title = "Atributo se repite entre las clases de la misma jerarquia (D01)";

	public D01 (EPackage metamodelo) {
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
		List<String> allAtributesNames = new ArrayList<String>();
		List<EAttribute> allAtributesObjet = new ArrayList<EAttribute>();
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
			      for (EAttribute attribute : ((EClass)classifier).getEAttributes()) {
			    	  String nameAttribute = attribute.getName();
			    	  if (allAtributesNames.contains(nameAttribute)) {
			    		  Problem problem = new Problem();
			    		  EClass class1 = null;
						  EClass class2 = null;
						  EAttribute attribute2 = null;
			    		  //BUSCAR LAS DOS ATRIBUTOS INVOLUCRADAS
			    		  class1 = attribute.getEContainingClass();
			    		  for (EAttribute attributeInList : allAtributesObjet) {
			    			  if ((attribute.getName().equals( attributeInList.getName())) && (attribute != attributeInList)) {
			    				  attribute2 = attributeInList;
			    				  class2 = attributeInList.getEContainingClass();
			    				  break;
			    			  }
			    		  }
			    		  problem.setDescription("El atributo con el nombre '" + attribute.getName() + 
			    				  "' se repite en las clases " + class1.getName() + "(" + class1.getClassifierID() + ")"
			    				  + " y " + class2.getName() + "(" + class2.getClassifierID() + ")");
			    		  IQuickfix fix1 = new D01Fix(metamodelo,attribute);
						  IQuickfix fix2 = new D01Fix(metamodelo,attribute2);
						  problem.addQuickfix(fix1);
						  problem.addQuickfix(fix2);
						  problems.add(problem);
			    	  }else {
			    		  allAtributesNames.add(nameAttribute);
			    		  allAtributesObjet.add(attribute);
			    	  }
			      }
			}
		}
				
		
		return problems;
	}

}
