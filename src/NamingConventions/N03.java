package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import plugin_validar.views.Problem;

public class N03 implements ICriterion {

	EPackage metamodelo;
	IDictionary dictionary;

	public N03(EPackage metamodelo, IDictionary dictionary) {
		this.metamodelo = metamodelo;
		this.dictionary = dictionary;
	}

	@Override
	public String getTitle() {
		return "(N03) Cada asiciación binaria debe nombrarse con una frase verbal";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			for (EReference reference : ((EClass) classifier).getEReferences()) {
				String[] words = reference.getName().split("(?=[A-Z])");
				if (words.length == 2) {
					IIndexWord idxWord1 = dictionary.getIndexWord(words[0].toLowerCase(), POS.VERB);
					IIndexWord idxWord2 = dictionary.getIndexWord(words[1].toLowerCase(), POS.VERB);
					if (idxWord1 != null && idxWord2 != null) {
						break;
					}
				}
				Problem problem = new Problem();
				problem.setDescription(
						"La asiciación binaria con el nombre " + reference.getName() + " no es una frase verbal");
				problems.add(problem);
			}
		}

		return problems;
	}

}
