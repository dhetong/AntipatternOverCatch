package Assignment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;

import Assignment.MethodVisitor;

public class SourceCodeScan extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws  ExecutionException{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] projects = root.getProjects();
		try {
			for (IProject project : projects)
			{
				if (!project.getName().equals("Anti_Pattern"))
				{
					if (project.isOpen() && project.isNatureEnabled("org.eclipse.jdt.core.javanature"))
					{
						processProject(project);
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private void processProject(IProject project) throws CoreException{
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragment[] packages = javaProject.getPackageFragments();
				
		for (IPackageFragment mypackage : packages){
			
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE)
			{
				for (ICompilationUnit unit : mypackage.getCompilationUnits())
				{
					CompilationUnit cunit = ASTBuilder(unit);
				    MethodVisitor visitor = new MethodVisitor(javaProject);
				    cunit.accept(visitor);
				}
			}
		}
	}
	/*public void MyScaner() {
		String myPath = "test/";
		File myDir = new File(myPath);
		File[] sourceFiles = myDir.listFiles();
		for(File sourceFile : sourceFiles) {
			if(sourceFile.isFile() && sourceFile.getName().endsWith(".java")) {
				
			}
		}
		
	}*/

	/*private void mapBuilder(File sourceFile) {
		CompilationUnit sourceAST = ASTBuilder(sourceFile);
		MethodVisitor myVisitor = new MethodVisitor();
		sourceAST.accept(myVisitor);
	}*/
	
	private CompilationUnit ASTBuilder(ICompilationUnit unit) throws CoreException {
		/*byte[] myInput = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(sourceFile.getName()));
			myInput = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(myInput);
            bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
        astParser.setSource(new String(myInput).toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));*/
		@SuppressWarnings("deprecation")
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setResolveBindings(true);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setBindingsRecovery(true);
		@SuppressWarnings("rawtypes")
		Map options = JavaCore.getOptions();
		astParser.setCompilerOptions(options);
		astParser.setSource(unit);
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
        return result;
	}
}
