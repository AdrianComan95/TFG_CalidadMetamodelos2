package plugin_validar.views;

import java.util.List;

public interface ICriterion {
	enum ProblemType { DESIGN, BEST_PRACTICE, NAMING_CONVENTION, METRIC };
    public List<Problem> check();
    public ProblemType getProblemType();
    public String getTitle();
    
}
