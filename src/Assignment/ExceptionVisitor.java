package Assignment;

import org.eclipse.jdt.*;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TryStatement;

public class ExceptionVisitor extends ASTVisitor {
	public boolean visit(TryStatement node) {
		return super.visit(node);
	}
}
