package Assignment;

import java.util.HashSet;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

public class ProjectAST {
	private IJavaProject javaProject;
	
	public ProjectAST(IJavaProject javaProject){
		this.javaProject = javaProject;
	}
	
	public HashSet<IMethod> getCallees(HashSet<IMethod> methodsSet){
		HashSet<IMethod> callees = new HashSet<IMethod>();
		HashSet<IMethod> methodsVisited = new HashSet<IMethod>();
		
		int constraints = IJavaSearchScope.SOURCES;
		IJavaElement[] javaElements = new IJavaElement[] {javaProject};
		IJavaSearchScope searchCode = SearchEngine.createJavaSearchScope(javaElements,constraints);
		
		CallHierarchy callHierarchy = CallHierarchy.getDefault();
		callHierarchy.setSearchScope(searchCode);
		
		while (!methodsSet.isEmpty()) {
			IMember[] members = { (IMember) methodsSet.toArray()[0] };
			IMethod firstiMethod = (IMethod)methodsSet.toArray()[0];
			
			if(methodsVisited.contains(firstiMethod)){
				methodsSet.remove(firstiMethod);
				continue;
			}
			
			MethodWrapper[] methodWrappers = callHierarchy.getCalleeRoots(members);
			for (MethodWrapper method_wrapper : methodWrappers) {
				MethodWrapper[] methodwrapper_temp = method_wrapper.getCalls(new NullProgressMonitor());
				HashSet<IMethod> temp = getIMethods(methodwrapper_temp);
				
				callees.addAll(temp);
				methodsSet.addAll(temp);
			}
			methodsVisited.add(firstiMethod);
			methodsSet.remove(firstiMethod);
		}
		
		return callees;		
	}
	
	private HashSet<IMethod> getIMethods(MethodWrapper[] methodWrappers){
		HashSet<IMethod> callmethod = new HashSet<IMethod>();
		for (MethodWrapper method_wrapper : methodWrappers) {
			IMethod im = null;
			try {
				if (method_wrapper.getMember().getElementType() == IJavaElement.METHOD)
					im = (IMethod) method_wrapper.getMember();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (im != null) {
				callmethod.add(im);
			}
		}
		return callmethod;		
	}
}
