package plugin_validar.views;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;

public class Design {
	
	EPackage metamodelo;
	
	
	public Design (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	
public ArrayList<String> D03 () {
		//TODO
		ArrayList<String> problem = new ArrayList<String>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		
		for (EClassifier classifier : classifiers) {
			//int count = 0;
			if (classifier instanceof EClass) {
				if((((EClass) classifier).getESuperTypes()).size() == 1) {
					   problem.add("La clase " + (((EClass) classifier).getESuperTypes()).get(0).getName() + " es abstracta con un solo hijo");		
				}
					
					/**if (((EClass) classifier).isAbstract()) {
			    	 for (EClassifier classifier2 : classifiers) {
			    		 System.out.println("abstracta" + classifier2.getName() + count);
			    		 System.out.println(((EClass) classifier2).getEGenericSuperTypes());
			    	 	if (((EClass) classifier2).isSuperTypeOf((EClass) classifier) && !classifier.equals(classifier2)) {
			    	 		System.out.println("d03" + classifier2.getName()+ count);
			    	 		count++;	
			    	 	}
			    	 			    	 		
			    	 }
			    	 if (count == 1)
						   problem.add("La clase " + classifier.getName()+ " es abstracta con un solo hijo");
			     } **/
			}
			
		}		
		
		return problem;
		
	}

}
