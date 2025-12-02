#!/bin/bash
# Recreates DB, runs queries without indexes, creates indexes, runs queries again.

echo "Recreating tables and loading data..."
psql -h localhost -p $PGPORT $USER"_DB" < create.sql > /dev/null

sleep 5
echo "Query time without indexes"
cat <(echo '\timing') queries.sql | psql -h localhost -p $PGPORT $USER"_DB" | grep Time | awk -F "Time" '{print "Query" FNR $2;}'

echo "Creating indexes..."
psql -h localhost -p $PGPORT $USER"_DB" < index.sql > /dev/null

echo "Query time with indexes"
cat <(echo '\timing') queries.sql | psql -h localhost -p $PGPORT $USER"_DB" | grep Time | awk -F "Time" '{print "Query" FNR $2;}'