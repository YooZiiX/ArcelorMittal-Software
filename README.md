# ArcelorMittal Project

This software engineering project is a partnership between **IMT Mines Alès** and **ArcelorMittal**,
a well-known company in the world of metallurgy.

The project is based on the following specifications:

- Read CSV
- Store CSV data in database
- Compute Orowan model every 200ms with last available value
- Compute average of last 5 orowan friction coefficient every 1s and store them into database
- Human-Machine Interface:
  - For every one:
    - Login / Logout
  - For the worker:
    - Stand ID
    - Compute time of orowan
    - Friction coefficient factor
  - For the process engineer:
    - Add / Remove / Update User
    - Add / Remove / Update User rights
    - Change application settings:
      - Enable / Disable stand
      - Change level 2 inputs range
  

- Improve Orowan


- Human-Machine Interface:
  - Add curves for the operator:
    - Friction
    - Roll speed
    - Sigma
  - Create a password protection for the users


- GRPC:
  - Use the LII simulator based gRpc
  - Use the orowan model based on gRpc
  

- Time Series Database:
  - Store Level II data into a Time Series Database
  - Store Orowan results into a Time Series Database
  - Show data from the Time Series Database into the Human-Machine Interface

*More information is available in PDF format in the "project" folder.*

As we saw in the specifications, the project requires a Human-Machine Interface,
so we are going to use the **MVC** architecture.

What's the **MVC** architecture?

MVC mean's : **M**odel **V**iew **C**ontroller,
a **M**odel contains the data to be displayed on Interface,
a **V**iew contains the presentation of the graphical Interface,
and a **C**ontroller contains the logic for user actions.

## CSV Files

Firstly, we set up a database using WampServer to store the data from the various CSV files.
Be carefull with float-type numbers, because the CSV float separator is '**,**' and the SQL separator is '**.**',
so you need to replace '**,**' per '**.**'.

For every SQL queries, we will use **PreparedStatement** to avoid usebug.

## Orowan

What's **Orowan** ? 

**Orowan** is a model that takes certain values as input to calculate other values,
such as the Friction coefficient.
In this project, **Orowan** is an executable, which must be used with Java.

As required by the specifications, we have to use **Orowan** every 200ms,
Then store values in the WampServer SQL database.

And every 5 iterations, we compute the average Friction coeffiecient value.

## User

Through our specifications, we can see that we need to implement CRUD functionnality, for User and User rights.
(**C**reate - **R**etrieve - **U**pdate - **D**elete)
