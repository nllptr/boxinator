# boxinator

Example program using mysql, Spring Boot and React.

To run, make sure you have Docker installed and run `docker-compose up` in the project's root folder. Give it some time, especially on the first run, since images have to be downloaded and built.

## Some things to note:

- The backend service might stop and restart a few times, until the database service has started properly. 2-3 restarts is not uncommon, since the database service can take quite some time to finish starting on the first run.

- This example application does not take account for extremely long lists of shipments. Could be fixed with pagination on both backend and frontend.

- In a real world scenario, the database connection needs to be secured. Here I use the root user and password, which is not recommended "in real life".

- Also note, that in the real world, database credentials should not be committed to source control.

- I'm using Double types for weights and costs. In a production setting, it might make more sense to use a more exact representation, such as BigDecimal, but for the same of this demo, I figured Doules are ok.