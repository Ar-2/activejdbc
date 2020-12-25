/*
Copyright 2009-2019 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/


package org.javalite.activejdbc.associations;

import org.javalite.activejdbc.Model;

import java.util.Map;

import static org.javalite.activejdbc.ModelDelegate.metaModelOf;

/**
 * @author Igor Polevoy
 */
public class OneToManyAssociation extends Association {

    public static final String FK = "FK";
    public static final String PK = "PK";

    private String fkName;
    private String pkName;

    public OneToManyAssociation(Map<String, Object> map) throws ClassNotFoundException {
        super(map);
        fkName = (String) map.get(FK);
        pkName = (String) map.get(PK);
    }

    /**
     * @param sourceModelClass source class, the one that has many targets
     * @param targetModelClass target class - many targets belong to source.
     * @param fkName name of a foreign key in teh target table.
     */
    public OneToManyAssociation(Class<? extends Model> sourceModelClass, Class<? extends Model> targetModelClass, String fkName, String pkName) {
        super(sourceModelClass, targetModelClass);
        this.fkName = fkName;
        this.pkName = (pkName == null || pkName.isEmpty()) ? metaModelOf(targetModelClass).getIdName() : pkName;
    }

    public OneToManyAssociation(Class<? extends Model> sourceModelClass, Class<? extends Model> targetModelClass, String fkName) {
        this(sourceModelClass, targetModelClass, fkName, null);
    }

    public String getFkName() {
        return fkName;
    }

    public String getPkName() {
        return pkName;
    }

    @Override
    public String toString() {
        return getSourceClass().getSimpleName() + "  ----------<  " + getTargetClass().getSimpleName() + ", type: " + "has-many";
    }

    @Override
    public boolean equals(Object other) {

        if (other == null || !other.getClass().equals(getClass())) {
            return false;
        } else {
            OneToManyAssociation otherAss = (OneToManyAssociation) other;
            return otherAss.fkName.equals(fkName)
                    && otherAss.pkName.equals(pkName)
                    && otherAss.getSourceClass().equals(getSourceClass())
                    && otherAss.getTargetClass().equals(getTargetClass());
        }
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        map.put(FK, fkName);
        map.put(PK, pkName);
        return map;
    }

}
