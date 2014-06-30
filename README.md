 README
========

## Requisites
- Java
- Gradle
- Preferred IDE/Text editor
- Internet connection

## Basic commands
- `$ ./gradlew test`: run tests
- `$ ./gradlew build`: builds the project
- `$ java -jar build/libs/horse-racing-0.1.0.jar`: runs the server

Point your browser to [http://localhost:8080/horse-racing](http://localhost:8080/horse-racing) to read the wording of the exercise.

## Design decisions, assumptions
- the result of the validation goes to the output field
- if the input contains ball throw for an absent lane, it will be an invalid input (the user maybe mistyped the lane number, after fixing the input will be valid) => so the example input is invalid in this case
