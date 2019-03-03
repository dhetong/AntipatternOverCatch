package Assignment;

import java.util.Set;

import org.eclipse.jdt.core.IMethod;

public class DependencyStore {
	private String methodName;
	private String className;
	private Set<String> DependencyMethodList;
	private Set<String> ExceptionList;
	
	public void Initialization(String myMethod, String myClass) {
		methodName = myMethod;
		className = myClass;
	}
	
	public void AddException(String exception) {
		ExceptionList.add(exception);
	}
	
	public Set<String> GetException(){
		return ExceptionList;
	}
}
