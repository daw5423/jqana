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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * This is an auxiliary class, used to list each class' member.
 * 
 * @author Cleuton Sampaio
 * 
 */
public class Member implements Comparable<Member> {
    
    /**
     * Enumeration for MEMBER_TYPE.
     * Member name is the key if it is a method, then its parameters are included in the form:
     * <methodname>:<type 1>:<type 2>:...:<type 3>
     * @author Cleuton Sampaio.
     *
     */
    public enum MEMBER_TYPE {
        METHOD, VARIABLE, GETTER_SETTER
    };

    public String packageName;
    public String className;
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
        String stringVersion = "[name: " + name + ", type: " + type
                + ", references: [" + referencedMembers.toString() + "]]";
        return stringVersion;
    }

}
