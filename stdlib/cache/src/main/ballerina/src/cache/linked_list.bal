// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

// This is a linked list data structure implementation, which is used for the eviction algorithm of the cache.

public type Node record {|
    any value;
    Node? prev = ();
    Node? next = ();
|};

public type LinkedList record {
    Node? head;
    Node? tail;
};

public function addLast(LinkedList list, Node node) {
    if (list.tail is ()) {
        list.head = node;
        list.tail = list.head;
        return;
    }

    Node tailNode = <Node>list.tail;
    node.prev = tailNode;
    tailNode.next = node;
    list.tail = node;
}

public function addFirst(LinkedList list, Node node) {
    if (list.head is ()) {
        list.head = node;
        list.tail = list.head;
        return;
    }

    Node headNode = <Node>list.head;
    node.next = headNode;
    headNode.prev = node;
    list.head = node;
}

public function remove(LinkedList list, Node node) {
    if (node.prev is ()) {
        list.head = node.next;
    } else {
        Node prev = <Node>node.prev;
        prev.next = node.next;
    }

    if (node.next is ()) {
        list.tail = node.prev;
    } else {
        Node next = <Node>node.next;
        next.prev = node.prev;
    }
    node.next = ();
    node.prev = ();
}

public function removeLast(LinkedList list) returns Node? {
    if (list.tail is ()) {
        return ();
    }
    Node tail = <Node>list.tail;
    Node predecessorOfTail = <Node>tail.prev;
    list.tail = predecessorOfTail;
    predecessorOfTail.next = ();
    tail.prev = ();

    return tail;
}

public function clear(LinkedList list) {
    list.head = ();
    list.tail = ();
}
