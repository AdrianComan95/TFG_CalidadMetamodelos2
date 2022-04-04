package plugin_validar.views;

import java.util.ArrayList;
import java.util.List;

import QuickFixes.IQuickfix;

public class Problem {
	
	String description;
	List<IQuickfix> quickfixes = new ArrayList<IQuickfix>();;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<IQuickfix> getQuickfixes() {
		return quickfixes;
	}
	public void addQuickfix(IQuickfix quickfix) {
		this.quickfixes.add(quickfix);
	}
}
