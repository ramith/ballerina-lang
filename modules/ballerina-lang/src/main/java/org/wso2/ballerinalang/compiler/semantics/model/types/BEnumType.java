/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/

package org.wso2.ballerinalang.compiler.semantics.model.types;

import org.ballerinalang.model.types.EnumType;
import org.ballerinalang.model.types.TypeKind;
import org.wso2.ballerinalang.compiler.semantics.model.symbols.BTypeSymbol;
import org.wso2.ballerinalang.compiler.util.Name;
import org.wso2.ballerinalang.compiler.util.TypeDescriptor;
import org.wso2.ballerinalang.compiler.util.TypeTags;

import java.util.List;

/**
 * @since 0.94
 */
public class BEnumType extends BType implements EnumType {

    public List<BEnumType.EnumField> fields;

    public BEnumType(BTypeSymbol tSymbol, List<BEnumType.EnumField> fields) {
        super(TypeTags.ENUM, tSymbol);
        this.fields = fields;
    }

    public String getDesc() {
        return TypeDescriptor.SIG_ENUM + getQualifiedTypeName() + ";";
    }

    @Override
    public List<BEnumType.EnumField> getFields() {
        return fields;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.ENUM;
    }

    /**
     * @since 0.94
     */
    public static class BEnumField implements EnumField {

        public Name name;

        public BEnumField(Name name) {
            this.name = name;
        }

        @Override
        public Name getName() {
            return name;
        }
    }
}
