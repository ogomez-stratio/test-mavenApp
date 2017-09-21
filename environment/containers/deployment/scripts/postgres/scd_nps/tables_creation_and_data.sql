DO
$body$
BEGIN

CREATE SCHEMA IF NOT EXISTS seguros AUTHORIZATION sanitas;

IF NOT EXISTS (
     SELECT 1
       FROM   information_schema.tables
       WHERE  table_schema = 'seguros'
       AND    table_name = 'touchpoint_nps'
  )
THEN

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
-- SET row_security = off;

SET search_path = pg_catalog, nps;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE seguros.touchpoint_nps
(
  id_touchpoint_nps serial primary key,
  des_canal text NOT NULL,
  id_cliente bigint NOT NULL,
  fec_datalake timestamp without time zone NOT NULL,
  fec_estado timestamp without time zone NOT NULL,
  sw_autorizado text,
  des_tipo_canal text,
  fec_control timestamp without time zone,
  id_interaccion bigint,
  id_linea bigint,
  fec_carga timestamp without time zone,
  des_motivo text,
  des_nota_recomendacion integer,
  des_nota_satisfaccion integer,
  fec_operacional timestamp without time zone,
  des_unidad_organizativa text,
  id_origen bigint,
  id_sesion bigint,
  id_version_fuente text,
  des_tipo text
);

ALTER TABLE seguros.touchpoint_nps OWNER TO sanitas;

END IF;

END;
$body$;