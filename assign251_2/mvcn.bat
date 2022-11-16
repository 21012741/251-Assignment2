@echo on
call mvn compile
call mvn test
call mvn install
call mvn jacoco:report
call mvn dependency:list
pause