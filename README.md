# U-Fund:  NYS Paws and Claws Foundation
# Modify this document to expand any and all sections that are applicable for a better understanding from your users/testers/collaborators (remove this comment and other instructions areas for your FINAL release)

An online U-Fund system built in Java 21=> and Angular 17.0
  
## Team

- Jonah Witte
- Kayla Van Bortel
- Max Klot
- Ryan Richter


## Prerequisites

- Java 21.0
- Maven
- Angular 17.0


## How to run it

1. Clone the repository and go to the root directory.
2. For the first time only, run `npm i` in the `ufund-ui/angular` directory
3. Execute `mvn compile exec:java`
4. Open in your browser `http://localhost:8080/`
5. From the `ufund-ui/angular` directory, run `ng serve --open`
6. The app will then open in your browser. 
7. Alternatively, go [our website](https://pawsnclaws.pages.dev) to use the latest deployed version of the app! 

## Known bugs and disclaimers

On some Chrome browsers with certain font packs installed, the website icons fail to load (only in the [cloud hosted app](https://pawsnclaws.pages.dev)).

Admin username: "admin", password: "adm1n!"

Handling surplus:  
As we are not accountable for the physical storage space of the Paws & Claws foundation, we are not accepting surplus donations at this time. A Helper is not capable of funding more of a Need than its quantity allows.

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory

## License

MIT License

See LICENSE for details.
