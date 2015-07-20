# selenium-test-automation-ci

## Running from a command line

Executing a build on a CI server is the same as running the build from a
command line prompt.

The most important difference is that the execution is triggered on a change
in the version control system and executed on another machine.

This means that an execution will always be done. Any mistakes that prevents
the build to succeeds will be caught. Any tweaks done on the developers host
will not be available and the comment it works on my machine is invalid.

This project is executed fro a prompt using the command

```
./gradlew clean build
```

## Running from a CI server

Setting up a CI server is done using these steps:

* Download Jenkins from [http://jenkins-ci.org/](http://jenkins-ci.org/)
* Move the downloaded war a directory where it can live and be executed
* Start Jenkins with `java -jar jenkins.war`
* Access Jenkins on [http://localhost:8080/](http://localhost:8080/)
* Install the Git plugin
  * Manage Jenkins
  * Manage plugins
   Select Updates
    * Update the JUnit plugin
  * Select Available
    * Select and install: GIT plugin
    * Select and install: Gradle plugin
  * Restart Jenkins
* Create a new job
  * Call it Cross browse`
  * Use the type - Freestyle project
  * Ok
  * Set Source Code Management to Git
  * Add the url `git@github.com:tsundberg/selenium-test-automation-ci.git`
  * Build Triggers - Poll SCM
  * Set the schedule to '* * * * *' - i.e. poll every minute
  * Add a build step - Invoke Gradle script
    * Use Gradle Wrapper
    * Tasks: clean build
  * Post-build Actions
    * Publish JUnit test result report
    * Test report XMLs - `**/TEST*.xml`
    * Save
* Execute the build immediately - Build Now

Any changes done to the project will now be picked up and executed.
