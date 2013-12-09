package com.obomprogramador.tools.jqana.parsers;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

public class Member implements Comparable<Member> {
	public enum MEMBER_TYPE {METHOD, VARIABLE, GETTER_SETTER};
	public String name;
	public MEMBER_TYPE type;
	public Member targetVariable;
	public List<Member> referencedMembers = new ArrayList<Member>();
	public ParseTree body;
	
	@Override
	public int hashCode() {
		return (this.type.ordinal() + 3) + this.name.hashCode(); 
	}

	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((Member) obj).name);
	}

	@Override
	public int compareTo(Member o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		String stringVersion = "[name: " + name 
					+ ", type: " + type
					+ ", references: [" + referencedMembers.toString()
					+ "]]";
		return stringVersion;
	}
	
	
}