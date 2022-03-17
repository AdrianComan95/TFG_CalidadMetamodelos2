package plugin_validar.views;

import java.util.List;

public interface IProblem {
	enum ProblemType { DESIGN, BEST_PRACTICE, NAMING_CONVENTION, METRIC };
    public List<String> check();
    public ProblemType getProblemType();
    public String getTitle();
    
}
