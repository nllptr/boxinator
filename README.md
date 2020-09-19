# boxinator

## OBS

- Does not take account for extremely long lists. Could be fixed with pagination on both backend and frontend.
- In a real world scenario, the database connection needs to be secured. Here I use the root user and password, which is not recommended "in real life".
- Also note, that in a real world, database credentials should not be committed to source control. Since this project uses a containerized database, created by the launch script, there should not be any chance of connecting to an already existing database and corrupt existing data.
- I'm using Double types for weights and costs. In a production setting, it might make more sense to use a more exact representation, such as BigDecimal, but that would require more 