package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import plugin_validar.views.IProblem;

public class LowerClass implements IProblem  {
	
	EPackage metamodelo;
	
	String title = "Clase empieza por letra en minuscula";

	public LowerClass (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	public ProblemType getProblemType() { return ProblemType.NAMING_CONVENTION; }
	
	@Override
	public List<String> check() {
		List<String> problems = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String primeraLetra = classifier.getName().substring(0, 1);
			if(primeraLetra.equals(primeraLetra.toLowerCase()))
				problems.add("La clase '" + classifier.getName() + "' no empieza con mayúscula");
		}		
		
		return problems;
	}
}
