
/**
 * jQana - Open Source Java(TM) code quality analyzer.
 * 
 * Copyright 2013 Cleuton Sampaio de Melo Jr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project website: http://www.jqana.com
 */
package com.obomprogramador.tools.jqana.parsers;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obomprogramador.tools.jqana.antlrparser.JavaBaseListener;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.AnnotationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ClassDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.CompilationUnitContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.FieldDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.FormalParametersContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.LocalVariableDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.LocalVariableDeclarationStatementContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodBodyContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.PackageDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.StatementContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.VariableDeclaratorContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.VariableDeclaratorIdContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.VariableDeclaratorsContext;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.GetClassNameFromContext;

import com.obomprogramador.tools.jqana.parsers.Member.MEMBER_TYPE;

/**
 * Implementation of JavaBaseListener (ANTLR4) that checks LCOM4 value for a class.
 * 
 * How does it work?
 * It creates a class' members array, containing each method or variable. For each method, 
 * the listener verifies any of its references, for example: referenced methods and variables, and so on.
 * This array is returned to the parser, that calculated LCOM4.
 * 
 * It classifies each class member according to its MEMBER_TYPE.
 * @see GobalConstants.MEMBER_TYPE
 * 
 *  
 * @author Cleuton Sampaio.
 *
 */
public class Lcom4Listener extends JavaBaseListener {

	protected Logger logger;
	protected List<Member> membersTable;
	protected JavaParser parser;
	protected String [] prefixos = {"get", "set", "is", "has"};
	protected List<String>getterSetterPrefix = Arrays.asList(prefixos);
	protected String mainPackageName;
	public String mainClassName;
	protected boolean alreadyGotMainClass;
	protected boolean overrideAnnotation;
	
	public Lcom4Listener(List<Member> members, JavaParser p) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.membersTable = members;
		this.parser = p;
	}

	

	@Override
	public void enterClassDeclaration(@NotNull ClassDeclarationContext ctx) {
		if (!alreadyGotMainClass) {
			mainClassName = GetClassNameFromContext.getClassName(ctx);
			alreadyGotMainClass = true;
		}
		

	}



	@Override
	public void enterPackageDeclaration(@NotNull PackageDeclarationContext ctx) {
		mainPackageName = ctx.getText().substring(7);
		logger.debug(mainPackageName);;
	}



	/**
	 * Get the field's name, which is a tricky task.
	 */
	@Override
	public void enterFieldDeclaration(@NotNull FieldDeclarationContext ctx) {
		String name = "";
		
		for (ParseTree subTree : ctx.children) {
			if (subTree instanceof VariableDeclaratorsContext) {
				for (ParseTree st2 : ((VariableDeclaratorsContext) subTree).children) {
					if (st2 instanceof VariableDeclaratorContext) {
						for (ParseTree st3 : ((VariableDeclaratorContext) st2).children) {
							if (st3 instanceof VariableDeclaratorIdContext) {
								name = st3.getText();
								break;
							}
						}
						if (name.length() > 0) {
							break;
						}
					}
					if (name.length() > 0) {
						break;
					}
				}
				if (name.length() > 0) {
					break;
				}
			}
		}
		Member member = new Member();
		member.className = mainClassName;
		member.name = name;
		member.packageName = this.mainPackageName;
		member.type = MEMBER_TYPE.VARIABLE;
		this.membersTable.add(member);
	}


	
	
	@Override
	public void enterAnnotation(@NotNull AnnotationContext ctx) {
		if (ctx.getText().equals("@Override")) {
			this.overrideAnnotation = true;
		}
	}



	/**
	 * We add each method to the members array. Then we will check for references.
	 */
	@Override
	public void enterMethodDeclaration(@NotNull MethodDeclarationContext ctx) {
		String methodName = "<no name>";
		for (ParseTree subTree : ctx.children) {
			if (subTree instanceof TerminalNodeImpl) {
				methodName = subTree.toString();
			}
			else if (subTree instanceof FormalParametersContext) {
				break;
			}
		}
		
		ParseTree body = null;
		for (ParseTree subTree : ctx.children) {
			if (subTree instanceof MethodBodyContext) {
				body = subTree;
				break;
			}
		}
		
		if (this.overrideAnnotation) {
			logger.debug("*** Inherited method ignored: " + methodName);
		}
		else {
			logger.debug("- Method found: " + methodName);
			Member member = new Member();
			member.className = mainClassName;
			member.name = methodName;
			member.packageName = this.mainPackageName;
			member.type = MEMBER_TYPE.METHOD;
			member.body = body;
			this.membersTable.add(member);			
		}

		this.overrideAnnotation = false;
	}
	
	/**
	 * This is a special inner listener, used to check for a method's references.
	 * @author Cleuton Sampaio
	 *
	 */
	class Lcom4MethodListener extends JavaBaseListener {
		private Member member;
		public Lcom4MethodListener(Member member) {
			this.member = member;
		}
		
		/**
		 * We have to check any expression, looking for other methods and variables references.
		 */
		@Override
		public void enterExpression(@NotNull ExpressionContext ctx) {
			if (ctx.children.size() == 1) {
				String exprMember = ctx.getText();
				logger.debug("Expression Member: " + exprMember);	
				Member nMember = new Member();
				member.className = mainClassName;
				member.packageName = mainPackageName;
				nMember.name = exprMember;
				if (membersTable.contains(nMember)) {
					if (!member.referencedMembers.contains(exprMember)) {
						int indx = membersTable.indexOf(nMember);
						nMember = membersTable.get(indx);
						if (nMember.type == MEMBER_TYPE.GETTER_SETTER) {
							member.referencedMembers.add(nMember.targetVariable);
						}
						else {
							member.referencedMembers.add(nMember);	
						}
						logger.debug("Class Member reference added: " + exprMember);
					}
				}
			}
		}
		
		

	}

	/**
	 * Now, that we finished analysing the class, we need to verify each found method's referencies.
	 * So, we instantiate our special listener and walk each method's tree.
	 */
	@Override
	public void exitCompilationUnit(@NotNull CompilationUnitContext ctx) {
		
		for (Member m : this.membersTable) {
			ParseTreeWalker walker = new ParseTreeWalker();
			if (m.type == MEMBER_TYPE.METHOD) {
				if (checkForGetterSetter(m)) {
					m.type = MEMBER_TYPE.GETTER_SETTER;
				}
				else {
			        Lcom4MethodListener ml = new Lcom4MethodListener(m);
			        walker.walk(ml, m.body); 
				}
			}
		}
		
		System.out.println("FIM");
	}


	private boolean checkForGetterSetter(Member m) {
		boolean isGetterSetter = false;
		for (Member n : this.membersTable) {
			if (n.type == MEMBER_TYPE.VARIABLE) {
				if (StringUtils.containsIgnoreCase(m.name, n.name)) {
					//StringUtils.strip("  abcyx", "xyz") = "  abc"
					String beginMethodName = StringUtils.stripEnd(m.name.toLowerCase(), n.name.toLowerCase());
					if (this.getterSetterPrefix.contains(beginMethodName)) {
						isGetterSetter = true;
						m.targetVariable = n;
					}
					break;
				}
			}
		}
		return isGetterSetter;
	}
	


}
