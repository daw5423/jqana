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
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.CompilationUnitContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.ExpressionContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.FieldDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.FormalParametersContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.LocalVariableDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.LocalVariableDeclarationStatementContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodBodyContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.MethodDeclarationContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.StatementContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.VariableDeclaratorContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.VariableDeclaratorIdContext;
import com.obomprogramador.tools.jqana.antlrparser.JavaParser.VariableDeclaratorsContext;
import com.obomprogramador.tools.jqana.parsers.Member.MEMBER_TYPE;

public class Lcom4Listener extends JavaBaseListener {

	protected Logger logger;
	protected List<Member> membersTable;
	protected JavaParser parser;
	protected String [] prefixos = {"get", "set", "is", "has"};
	protected List<String>getterSetterPrefix = Arrays.asList(prefixos);
	
	public Lcom4Listener(List<Member> members, JavaParser p) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.membersTable = members;
		this.parser = p;
	}


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
		member.name = name;
		member.type = MEMBER_TYPE.VARIABLE;
		this.membersTable.add(member);
	}


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
		
		Member member = new Member();
		member.name = methodName;
		member.type = MEMBER_TYPE.METHOD;
		member.body = body;
		this.membersTable.add(member);
	}
	
	class Lcom4MethodListener extends JavaBaseListener {
		private Member member;
		public Lcom4MethodListener(Member member) {
			this.member = member;
		}
		@Override
		public void enterExpression(@NotNull ExpressionContext ctx) {
			if (ctx.children.size() == 1) {
				String exprMember = ctx.getText();
				logger.debug("Expression Member: " + exprMember);	
				Member nMember = new Member();
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
