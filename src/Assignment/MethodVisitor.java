package Assignment;

import org.eclipse.jdt.*;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

import java.util.*;

public class MethodVisitor extends ASTVisitor {
	HashSet<MethodDeclaration> MethodList = new HashSet<>();
	ProjectAST javaAST;
	
	public MethodVisitor(IJavaProject javaProject){
		javaAST = new ProjectAST(javaProject);
	}
	/*public boolean visit(MethodDeclaration node) {
		MethodList.add(node);
		List<Type> exceptionList = node.thrownExceptionTypes();
		
		IMethod currentMethod = (IMethod) node.resolveBinding().getJavaElement();
		
		CallHierarchy callHierarchy = CallHierarchy.getDefault();
	    IMember[] members = { currentMethod };
	    MethodWrapper[] callees = callHierarchy.getCalleeRoots(members);
	    
		return super.visit(node);
	}*/
	
	public boolean visit(MethodDeclaration node) {
		DependencyMapBuilder(node);
		return true;
	}
	
	public void DependencyMapBuilder(MethodDeclaration node) {
		IMethod currentMethod = (IMethod) node.resolveBinding().getJavaElement();
	}
	
	public HashSet<MethodDeclaration> getMethodList(){
		return MethodList;
	}
}
