package Interfaces;

import java.util.List;

import plugin_validar.views.Problem;

public interface ICriterion {
	enum ProblemType { DESIGN, BEST_PRACTICE, NAMING_CONVENTION, METRIC };
    public List<Problem> check();
    public ProblemType getProblemType();
    public String getTitle();
    
}
