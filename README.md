# ebf-test-backend
# create postgresql database <databasename>
# edit application.properties file with :
#   -server port
#   -database name with <databasename>
#  - postgresql username and password
# cd /pathToProject/
# mvn clean install 
# cd /target
# java -jar test-0.0.1-SNAPSHOT.jar
# the backend is running and 2 companies are created  
#   -Test Company_1 with user [usrname=admin,password=admin]  
#   -Test Company_2 with user [usrname=admin2,password=admin]
# Abailable Apis are : (Authentication with refresh token )
#   - Get : /api/employees/list : params : filter , page , pageSize 
#   -POST : /api/employees/save : {name,surname,email,address,salary}
#   -DELETE : /api/employees/delete/{employeeId}
#   -GET : /api/employees/average
