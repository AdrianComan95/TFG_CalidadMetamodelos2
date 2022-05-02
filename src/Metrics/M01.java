package Metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import plugin_validar.views.Problem;

public class M01 implements ICriterion {

	EPackage metamodelo;
	int maxAtributes;

	// String title = "(M01) Sobrecarca de atributos maximos (" + maxAtributes +
	// "-max)";

	public M01(EPackage metamodelo, int maxAtributes) {
		this.metamodelo = metamodelo;
		this.maxAtributes = maxAtributes;
	}

	@Override
	public String getTitle() {
		return "(M01) Sobrecarca de atributos maximos (" + maxAtributes + "-max)";
	}

	public ProblemType getProblemType() {
		return ProblemType.METRIC;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass) {
				if (((EClass) classifier).getEAttributes().size() >= maxAtributes) {
					Problem problem = new Problem();
					problem.setDescription("La clase " + classifier.getName() 
							+"(" +classifier.getClassifierID()  +")" + "tiene mas de " + maxAtributes
							+ " atributos.");
					problems.add(problem);
				}

			}
		}

		return problems;
	}

}
