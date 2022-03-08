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
		ArrayList<String> problem = new ArrayList<String>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
				if((((EClass) classifier).getESuperTypes()).size() == 1) {
					   problem.add("La clase " + (((EClass) classifier).getESuperTypes()).get(0).getName() + " es abstracta con un solo hijo");		
				}
			}
			
		}		
		
		return problem;
		
	}

}
