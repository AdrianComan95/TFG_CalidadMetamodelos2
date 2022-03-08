package plugin_validar.views;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

public class BestPractices {
	EPackage metamodelo;
	
	
	public BestPractices (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
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
	
	public ArrayList<String> BP01 () {
		
		ArrayList<String> problem = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			ArrayList<EClass> parents = new ArrayList<EClass>();
			if (classifier instanceof EClass) {
			     parents = parentsR((EClass) classifier);
			}
			EClass classproblem = diamondProblem(parents);
			if (classproblem != null) {
				problem.add("La clase " + classifier.getName() + " hereda de " 
						+ classproblem.getName() + " por mas de un camino");
			}			
			
		}		
		
		return problem;
		
	}
	
public ArrayList<String> BP02 () {
		
		ArrayList<String> problem = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			Boolean uninstantiable = true;
			if (classifier instanceof EClass) {
			     if (((EClass) classifier).isAbstract()) {
			    	 for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
			    	 	if (((EClass) classifier2).isSuperTypeOf((EClass) classifier) && !classifier.equals(classifier2)) {
			    	 		uninstantiable = false;
			    	 	}
			    	 			    	 		
			    	 }
			    	 if (uninstantiable == true)
						   problem.add("La clase " + classifier.getName()+ " es abstracta sin hijos");
			     } 
			}
			
		}		
		
		return problem;
		
	}

}
