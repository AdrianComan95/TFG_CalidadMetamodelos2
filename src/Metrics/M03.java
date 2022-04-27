package Metrics;

import java.util.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class M03 implements ICriterion {

	EPackage metamodelo;
	int maxReferences;

	public M03(EPackage metamodelo, int maxReferences) {
		this.metamodelo = metamodelo;
		this.maxReferences = maxReferences;
	}

	@Override
	public String getTitle() {
		return "(M03) Clase referenciada muchas veces (" + maxReferences + "-max)";
	}

	public ProblemType getProblemType() {
		return ProblemType.METRIC;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		List<EClass> allReferences = new ArrayList<EClass>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass) {
				for (EReference reference : ((EClass) classifier).getEReferences()) {
					allReferences.add(reference.getEReferenceType());
				}

			}
		}

		Set<EClass> classSet = new HashSet<EClass>(allReferences);
		for (EClass classR : classSet) {
			int frequency = Collections.frequency(allReferences, classR);
			if (Collections.frequency(allReferences, classR) >= maxReferences) {
				Problem problem = new Problem();
				problem.setDescription("La clase " + classR.getName() + "(" + classR.getClassifierID() + ")"
						+ "es referida demasiadas veces (" + frequency + ")" );
				IQuickfix fix = new M03Fix(metamodelo, classR);
				problem.addQuickfix(fix);
				problems.add(problem);
			}
		}

		return problems;
	}

}
