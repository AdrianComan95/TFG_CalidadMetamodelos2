package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class N05 implements ICriterion {

	EPackage metamodelo;
	int maxLenght;

	public N05(EPackage metamodelo, int maxLenght) {
		this.metamodelo = metamodelo;
		this.maxLenght = maxLenght;
	}

	@Override
	public String getTitle() {
		return "(N05) Elementos con nombres demasiado largos (" + maxLenght + "-max)";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass) {
					for (EAttribute attribute : ((EClass) classifier).getEAttributes()) {
						if (attribute.getName().length() >= maxLenght) {
							Problem problem = new Problem();
							problem.setDescription("El nombre del atributo " + classifier.getName() + "." + attribute.getName()
									+ " es demasiado largo");
							IQuickfix fix = new N05Fix(metamodelo, attribute);
							problem.addQuickfix(fix);
							problems.add(problem);
						}

					}
					for (EReference reference : ((EClass) classifier).getEReferences()) {
						if (reference.getName().length() >= maxLenght) {
							Problem problem = new Problem();
							problem.setDescription("El nombre de la referencia " + reference.getName()
									+ " es demasiado largo");
							IQuickfix fix = new N05Fix(metamodelo, reference);
							problem.addQuickfix(fix);
							problems.add(problem);
						}

					}
				}
			}

		return problems;
	}

}
