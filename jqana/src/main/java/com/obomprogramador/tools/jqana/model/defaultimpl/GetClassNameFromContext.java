package com.obomprogramador.tools.jqana.model.defaultimpl;

import org.antlr.v4.runtime.tree.ParseTree;

import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ClassDeclarationContext;

/**
 * Class to get a class name from a context.
 * 
 * @author Cleuton Sampaio
 * 
 */
public final class GetClassNameFromContext {
    
    private GetClassNameFromContext() {
        
    }
    
    
    /**
     * Get the class name. Parses the Context children looking for the class
     * name.
     * 
     * @param ctx
     *            ClassDeclaractionContext to get the class name from.
     * @return Class name.
     */
    public static String getClassName(ClassDeclarationContext ctx) {
        String className = null;
        boolean findClass = false;
        for (ParseTree p : ctx.children) {
            if (p.getText().equals("class")) {
                findClass = true;
            } else {
                if (findClass) {
                    className = p.getText();
                    break;
                }
            }
        }
        return className;
    }
}
