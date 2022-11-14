# ebf-test-backend
1.create postgresql database <databasename>
2.edit application.properties file with :
  -server port
  -database name with <databasename>
  - postgresql username and password
3.cd /pathToProject/
4.mvn clean install 
5.cd /target
6.java -jar test-0.0.1-SNAPSHOT.jar
7. the backend is running and 2 companies are created  
  -Test Company_1 with user [usrname=admin,password=admin]  
  -Test Company_2 with user [usrname=admin2,password=admin]
8.Abailable Apis are : (Authentication with refresh token )
  - Get : /api/employees/list : params : filter , page , pageSize 
  -POST : /api/employees/save : {name,surname,email,address,salary}
  -DELETE : /api/employees/delete/{employeeId}
  -GET : /api/employees/average
