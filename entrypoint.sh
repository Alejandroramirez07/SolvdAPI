#!/bin/bash
echo "Solvd API Tests"
java -version
echo ""
echo "Running Maven tests..."
mvn clean test
exit $?