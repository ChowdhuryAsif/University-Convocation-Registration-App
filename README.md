![WC UNIVERSITY](https://i.imgur.com/ZQz7KpX.png "WC Uni LOGO")

# University-Convocation-Registration-App

Overview: This is a web application consisting of several microservices. Users of various roles  can interact with the system by using the web frontend. As data source, NoSQL database is used (MongoDB). The backend microservices are deployed on Heroku. The frontend is deployed there as well. All the services and UI are done using Spring Boot Java Framework.


### Software Used:
* Intellij Idea Ultimate 2019.2

### Language Used:
* Java

### Frameworks Used:
* Spring Boot 2.1.7
* Vaadin


### Frontend Link: 
* https://wc-uni-convo.herokuapp.com/login

## Backend Microservices Links (with Swagger):
-----------------------------------------------------------
### Discovery Server: 
* https://wc-uni-discovery-server.herokuapp.com/                              

### Gateway Server: 
* https://wc-uni-gateway-server.herokuapp.com/

### Authentication Server: 
* https://wc-uni-authorization.herokuapp.com/api/v1/authorization
### Student Server: 
* https://wc-uni-students.herokuapp.com/swagger-ui.html
### Academic(Program) Server: 
* https://wc-uni-programs.herokuapp.com/swagger-ui.html
### Employee Server: 
* https://wc-uni-employees.herokuapp.com/api/v1/employees


## Databases:
* student (For storing Student data)
* employee (For storing Employee data)
* program (For storing Program and Course data)
* authorization (For storing user credentials)


# Role Activity:
### Student:
* Apply for convocation if eligible (depends on his/her program’s minimum credits for graduation criteria). Otherwise a notification will be shown
* Make payment for convocation.

### Academic Deputy Registrar:
* View all the programs and courses offered in the university
* Create program
* Create courses. Course needs to be offered in any one of the created program. A program combo box makes it sure.

### Admission Officer:
* Add students in different programs. Only into one of the programs created in the system, a student can be admitted. 
* Initial login credentials for the students will auto generated with their student ID as username & password.

### Program Coordinator:
* View students registered in program he is coordinating
* View their grades and credits completed
* Select students and register them into different courses offered in that particular program.

### Examination Officer:
* View students registered in the programs according to his/her own departments (e.i. CSE exam officer can view only CSE students)
* Select student and submit grades for the courses the selected student registered.
* As soon as the officer grades a course, it goes to gradedCourseList.
* As soon as the grading of a subject is done, the student grid with CGPA information updates in DB, to show in UI have to refresh once. (It will do real time update in future)

### Human Resources Deputy Registrar:
* View all the employees in all the departments
* Add employee into any department and create initial credentials.

