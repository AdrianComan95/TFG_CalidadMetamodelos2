package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import plugin_validar.views.Problem;

public class N08 implements ICriterion {

	EPackage metamodelo;
	IDictionary dictionary;

	public N08(EPackage metamodelo, IDictionary dictionary) {
		this.metamodelo = metamodelo;
		this.dictionary = dictionary;
	}

	@Override
	public String getTitle() {
		return "(N08) Cada atributo booleano tiene una frase verbal";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			for (EAttribute attribute : ((EClass) classifier).getEAttributes()) {
				if (attribute.getEAttributeType().getName().equals("EBoolean")) {
					String[] words = attribute.getName().split("(?=[A-Z])");
					if (words.length == 2) {
						IIndexWord idxWord1 = dictionary.getIndexWord(words[0].toLowerCase(), POS.VERB);
						IIndexWord idxWord2 = dictionary.getIndexWord(words[1].toLowerCase(), POS.VERB);
						if (idxWord1 != null && idxWord2 != null) {
							break;
						}
					}
					Problem problem = new Problem();
					problem.setDescription(
							"El atributo booleano con el nombre " + attribute.getName() + " no es una frase verbal");
					problems.add(problem);
				}

			}
		}

		return problems;
	}

}
