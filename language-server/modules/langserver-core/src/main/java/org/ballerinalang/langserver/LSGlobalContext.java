/*
 * Copyright (c) 2018, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.langserver;

import org.ballerinalang.langserver.commons.LSOperation;
import org.ballerinalang.langserver.compiler.LSContextImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Global context for Language server.
 * 
 * @since 0.970.0
 */
public class LSGlobalContext extends LSContextImpl {
    private final Map<Key<?>, Object> props = new HashMap<>();

    public LSGlobalContext(LSOperation operation) {
        super(operation);
    }

    @Override
    public <V> void put(Key<V> key, V value) {
        props.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(Key<V> key) {
        return (V) props.get(key);
    }
}
