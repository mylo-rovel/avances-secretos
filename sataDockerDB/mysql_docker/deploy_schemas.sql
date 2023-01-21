-- Deploy fresh database tabels:
\i '/docker-entrypoint-initdb.d/tables/db_tables.sql'

\i '/docker-entrypoint-initdb.d/seed/seed.sql'