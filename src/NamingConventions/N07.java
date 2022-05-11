package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import Interfaces.ICriterion;
import edu.mit.jwi.IDictionary;
import plugin_validar.views.Problem;

public class N07 implements ICriterion {

	EPackage metamodelo;
	IDictionary dictionary;

	public N07(EPackage metamodelo, IDictionary dictionary) {
		this.metamodelo = metamodelo;
		this.dictionary = dictionary;
	}

	@Override
	public String getTitle() {
		return "(N06) Cada caracterustuca está nombrada en camle-case";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			for (EAttribute atribute : ((EClass) classifier).getEAttributes()) {
				String[] words = feature.getName().split("(?=[A-Z])");
				if (!(words.length >= 2)) {
					Problem problem = new Problem();
					problem.setDescription(
							"La caracteristica " + feature.getName() + " no está nombrada en camel-case");
					problems.add(problem);
				}
				
			}
		}

		return problems;
	}

}
