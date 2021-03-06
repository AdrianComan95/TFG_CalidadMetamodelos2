package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import Interfaces.ICriterion;
import edu.mit.jwi.IDictionary;
import plugin_validar.views.Problem;

public class N06 implements ICriterion {

	EPackage metamodelo;

	public N06(EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}

	@Override
	public String getTitle() {
		return "(N06) Cada elemento est? nombrado en camle-case";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			for (EStructuralFeature feature : ((EClass) classifier).getEStructuralFeatures()) {
				String[] words = feature.getName().split("(?=[A-Z])");
				if (!(words.length >= 2)) {
					Problem problem = new Problem();
					problem.setDescription(
							"La caracteristica " + feature.getName() + " no est? nombrada en camel-case");
					problems.add(problem);
				}
				
			}
		}

		return problems;
	}

}
