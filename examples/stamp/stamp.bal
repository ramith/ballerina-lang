import ballerina/io;

type Employee record {
    string name;
    string status;
    string batch;
};

type Teacher record {
    string name;
    int age;
    string status;
    string batch;
    string school;
};

type Foo record{
    string [] a;
    !...
};

public function main() {

    // This stamps the anydata type array to an int array
    anydata[] anydataArray = [1, 2, 3, 4];
    int[]|error intArray = int[].stamp(anydataArray);
    if (intArray is int[]) {
        io:println("Element type of the array is Int");
    }

    // This stamps the int type array to json
    int[] intArrayValue = [1, 2, 3, 4];
    json|error jsonValue = json.stamp(intArrayValue);
    if (jsonValue is json[]) {
        io:println("Int array is stamped as JSON array");
    }

    // This stamps the Teacher type variable as the Employee since fields
    // are matching.
    Teacher t1 = { name: "Raja", age: 25, status: "single",
                            batch: "LK2014", school: "Hindu College" };
    Employee e = Employee.stamp(t1);
    string school = <string> e.school;
    io:println("School of the Employee is " + school);

    // This stamps the map type variable as the record type
    map<anydata> anydataMap = { name: "Raja", age: 25, status: "single",
                            batch: "LK2014", school: "Hindu College" };
    Teacher|error teacherValue = Teacher.stamp(anydataMap);
    if (teacherValue is Teacher) {
        io:println("Map is stamped as Teacher record type");
    }

    // This stamps the tuple type variable as the int array
    (int,int) intTuple = (1, 2);
    int[]|error valueArray = int[].stamp(intTuple);
    if (valueArray is int[]) {
        io:println("Tuple is stamped as int array");
    }

    // This stamps the json variable as record type
    json j1 = {a:["a", "b"]};
    Foo|error recordValue = Foo.stamp(j1);
    if (recordValue is Foo) {
        io:println("JSON value is stamped as Foo");
    }
}
