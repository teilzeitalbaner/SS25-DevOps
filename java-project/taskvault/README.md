### RoastRepublic

## Table of Contents

1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [How to run](#howtorun)

### General Info

***
In today's fast-paced world, staying organized and keeping track of tasks is essential to productivity and success.
JavaTaskVault is a task management tool that helps its users achieve this. It allows users to efficiently manage tasks
and keep track of their to-do list. Larger tasks can be easily divided by subtasks, allowing for a clearer processing of
tasks. Different topics? No problem, categories can be used to create different tasks so that you can devote yourself
entirely to one task and its subtasks. This allows several tasks to be entered in one category, and the tasks themselves
can then be processed one by one using subtasks. Recurring tasks can be processed with the DailyTasks; these are
repeated daily and make it easier to build up habits.
The following sections describe the features and functionalities of JavaTaskVault.

## Technologies

***
A list of technologies used within the project:

* [Java](https://www.java.com/de/)
* [Git](https://git-scm.com/)
* [JUnit5](https://junit.org/junit5/)
* [Docker]()
* [Hibernate]()
* [PostgreSQL]()
* [Lombock]()
* [Maven]()

## Installation

***
Pull repo from git:

```
$ git clone https://git.ai.fh-erfurt.de/prgj2-24/taskvault.git
```

***
To run tests:
```
mvn clean test

```
***
To compile:
```
mvn clean install
```

## How-to-run
```
Run: "docker-compose up -d"
Start WebServer ("src/main/java/de/fherfurt/taskvault/api/WebApplication.java“)
Shutdown: "docker-compose down"
```
