package BestPractices;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class BP01 implements ICriterion {
	
	EPackage metamodelo;
	
	public BP01 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return "(BP01) No hay rutas generalizadas redundantes (Problema de diamante)";
	}
	
	public ProblemType getProblemType() { return ProblemType.BEST_PRACTICE; }

	private ArrayList<EClass> parentsR (EClass classifier){
		ArrayList<EClass> parents = new ArrayList<EClass>();
		
		for (EClass classfierSuper : ((EClass)classifier).getESuperTypes ()) {
			parents.add(classfierSuper);
	    	parents.addAll(parentsR(classfierSuper));
	    }
		
		return parents;
		
	}
	
	private EClass diamondProblem(ArrayList<EClass> parents){
        for(int i=0;i<parents.size()-1;i++){
            for(int j=i+1;j<parents.size();j++){
                if(parents.get(i).equals(parents.get(j))){
                    return parents.get(i);
                }
            }
        }
        return null;
    }
	
	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			ArrayList<EClass> parents = new ArrayList<EClass>();
			if (classifier instanceof EClass) {
			     parents = parentsR((EClass) classifier);
			}
			EClass classproblem = diamondProblem(parents);
			if (classproblem != null) {
				Problem problem = new Problem();
				problem.setDescription("La clase " + classifier.getName() + 
						"(" +classifier.getClassifierID()  +")" + " hereda de " 
						+ classproblem.getName() + " por mas de un camino");
				List<EClass> classParents = ((EClass)classifier).getESuperTypes();
				//BUSCAMOS LOS 2 PADRES QUE GENERAN EL PROBLEMA
				EClass parent1 = null;
				EClass parent2 = null;
				for (EClass parent : classParents) {
					ArrayList<EClass> parentsAll = parentsR(parent);
					if (parentsAll.contains(classproblem)) {
						if (parent1 == null) {
							parent1 = parent;
						}
						else if(parent2 == null) {
							parent2 = parent;
						}
					}
				}
				IQuickfix fix1 = new BP01Fix(metamodelo,classifier, parent1);
				IQuickfix fix2 = new BP01Fix(metamodelo,classifier, parent2);
				problem.addQuickfix(fix1);
				problem.addQuickfix(fix2);
				problems.add(problem);
			}			
			
		}		
		
		return problems;
	}

}
