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

public class D02 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(D02) Clase aislada";

	public D02 (EPackage metamodelo) {
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
			
			if (classifier instanceof EClass && ((EClass)classifier).getEAllSuperTypes().isEmpty() 
					&& ((EClass)classifier).getEAllReferences().isEmpty()  ) {
				Boolean isolated = true;
				for (EClassifier classifier2 : classifiers) {  
					if (((EClass) classifier).isSuperTypeOf((EClass) classifier2)){
						isolated = false;
						break;
					}
				}
				for (EClassifier classifier2 : classifiers) {
					if (!isolated) {
						break;
					}
					for (EReference reference: ((EClass)classifier2).getEAllReferences()) {
						if(reference.getEReferenceType() == classifier) {
							isolated = false;
							break;
						}
					}
				}
				if (isolated) {
					Problem problem = new Problem();
		    		problem.setDescription("La clase " +classifier.getName() +
							  "(" +classifier.getClassifierID()  +")" +
		    				" esta aislada, es decir no tiene ninguna asiciación o jerarquia");
		    		IQuickfix fix = new D02Fix(metamodelo,classifier);
					problem.addQuickfix(fix);
					problems.add(problem);
				}
			}
		}
				
		
		return problems;
	}

}
