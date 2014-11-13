CREATE KEYSPACE IF NOT EXISTS mapper_module
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

/* Fill in the ??? part */
CREATE TABLE IF NOT EXISTS mapper_module.users(
    login text,
    name text,
    age int,
    PRIMARY KEY(???));
