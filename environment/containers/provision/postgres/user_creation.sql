DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'sanitas') THEN

    	CREATE USER sanitas WITH PASSWORD 'Sanitas2016';

   END IF;
END
$body$;