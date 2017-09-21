# HOW-TO Get SANITAS Environment Up and Running

This document describes the procedure to get the Sanitas environment up & running in a Linux system (Ubuntu 16.04 is recommended).

Sanitas environment is composed by the following systems:
- HDFS (2.7.2)
- Kafka (0.9.0.1)
- Zookeeper (3.4.6)
- Mesos (0.28.1)  1 master + 1 slave
- Stratio Ingestion (0.7.0)
- Stratio Sparta (1.3.0)
- ~~Stratio PostgresBD (0.8.0) 1 GTM + 1 Coordinator + 1 Datanode~~ Using single node Postgres, to save resources
- Stratio Viewer (0.20.0)
- Provision container (create policies, topics, hdfs folders...)
- Zookeeper Web UI (see <https://github.com/tobilg/docker-zookeeper-webui>)

These systems are run in docker containers, so it is necessary to have docker installed in your system to handle and run them.

## Install docker engine and docker compose
If docker is already installed in your system you can safely skip this step. You can check if docker is installed by running in a Linux shell (versions shown can differ):

```bash
$ docker --version
Docker version 1.12.3, build 6b644ec
$ docker-compose --version
docker-compose version 1.9.0, build 2585387
```

If docker is not installed in your system please install it by following [the procedure described at Docker website](https://docs.docker.com/engine/installation/linux/) corresponding to your Linux distribution.

## Get docker images contents from github
All the contents required to build Sanitas docker images is in [this project github repository](https://github.com/Stratio/sanitas-phase1). To get a copy of those contents run:

```bash
$ git clone https://github.com/Stratio/sanitas-phase1.git
```

This will download all contents inside a new folder `sanitas-phase1`.

## Build docker images
Building Sanitas docker images is straightforward:

```bash
$ cd sanitas-phase1/environment
./build_images.sh
```
*WARNING*: Plese, remember building the images every time you perform changes on their contents, regardless if those changes are done by you or by merging code updates with `git pull`.

Once the images are built you are ready to start working with the Sanitas environment.

## Get environment up & running
The environment is handled using the script in `sanitas-phase1/environment/handle_containers.sh`, which executes different actions with the docker containers to start, stop, reset, and clean the environment:

- Start (and create if needed) the containers:
```bash
$ cd sanitas-phase1/environment
$ ./handle_containers.sh start
```

- Stop the containers (maybe in a not very gracefull way :sweat_smile:):
```bash
$ cd sanitas-phase1/environment
$ ./handle_containers.sh stop
```

- Reset the processing containers keeping the existing data in HDFS and PostgresDB:
```bash
$ cd sanitas-phase1/environment
$ ./handle_containers.sh reset
```

- Clean all existent containers and associated data (**USE WITH CAUTION**)
```bash
$ cd sanitas-phase1/environment
$ ./handle_containers.sh clean-all
```
- Start Ingestion agents (Do it for each agent)
```bash
$ docker exec -it scd-deployment /bin/bash
$ ./ingestion/start_agent.sh <agent_name> <agent_port>
```

## Data ingestion - Loading data into the environment
To insert data into your running Sanitas environment you must copy the file containing the data to the corresponding `spool_dir`. The system will automatically detect that new a file is present, and will read its contents to insert them into the system.

To do so you should use the `load_files.sh` script: it copies the given file to the destination:

```bash
$ cd sanitas-phase1/environment
$ ./load_files.sh FULL_FILE_PATH DEST_SOURCE
```

`FILE_TO_MOVE_FULL_PATH`is the full path of the file with the data to insert. `DEST_SOURCE` is the destination source, _i.e._ the folder where the file will be read from. To see the valid folders you can run `$ load_files.sh` without params, the script will list the available destination folders.

Each folder is watched by a _flume_ agent (which are running on their own docker image). When the agent detects the new file, it reads its data and forwards it to the corresponding _kafka topic_ (kafka is also running in a docker image) as defined in its configuration (flume agents' configurations can be found in `sanitas-phase1/environment/containers/ingestion/conf/`).

In folder `sanitas-phase1/etl/sample_data` there are some sample data files that can be used for testing purposes. You can use the following commands to load those sample files:

```bash
$ cd sanitas-phase1/environment
./load_files.sh ../etl/sample_data/MAESTRO_PRESTADOR_CLAVE_201610061751.csv maestro-prestador-clave
./load_files.sh ../etl/sample_data/MAESTRO_DENTAL_CENTRO_PRESTADOR_201610131641.csv maestro-dental-centro-prestador
./load_files.sh ../etl/sample_data/MAESTRO_DENTAL_MUNICIPIOS_201610270900.csv maestro-dental-municipio
./load_files.sh ../etl/sample_data/MAESTRO_CALLCENTER_AGENTES_201610271613.csv maestro-callcenter-agentes

./load_files.sh ../etl/sample_data/CLIENTE_SEGUROS_201610131308.csv cliente-seguros

./load_files.sh ../etl/sample_data/PACIENTE_DENTAL_201610211157.csv paciente-dental

./load_files.sh ../etl/sample_data/TOUCHPOINT_GASTO_MEDICO_201610111545.csv touchpoint-gasto-medico
./load_files.sh ../etl/sample_data/TOUCHPOINT_AUTORIZACIONES_201610071206.csv touchpoint-autorizaciones
./load_files.sh ../etl/sample_data/TOUCHPOINT_COPAGOS_201610111703.csv touchpoint-copagos
./load_files.sh ../etl/sample_data/TOUCHPOINT_PRESTACIONES_201610111638.csv touchpoint-prestaciones
./load_files.sh ../etl/sample_data/TOUCHPOINT_PRESUPUESTOS_DENTAL_201610241046.csv touchpoint-presupuestos-dental
./load_files.sh ../etl/sample_data/TOUCHPOINT_ACTOS_DENTAL_201610241044.csv touchpoint-actos-dental
./load_files.sh ../etl/sample_data/TOUCHPOINT_GESTOR_CONTACTOS_201610241225.csv touchpoint-gestor-contactos
./load_files.sh ../etl/sample_data/TOUCHPOINT_GENESYS_LLAMADAS_201611161642.csv touchpoint-genesys-llamadas
./load_files.sh ../etl/sample_data/TOUCHPOINT_GENESYS_MAILS_RECIBIDOS_201611171207.csv touchpoint-genesys-mails-recibidos
./load_files.sh ../etl/sample_data/TOUCHPOINT_GENESYS_MAILS_ENVIADOS_201611151300.csv touchpoint-genesys-mails-enviados

./load_files.sh ../etl/sample_data/TOUCHPOINT_NPS_NO_ASISTENCIALES_201611221054.csv touchpoint-nps
./load_files.sh ../etl/sample_data/TOUCHPOINT_RECLAMACIONES_201611231515.csv touchpoint-reclamaciones
./load_files.sh ../etl/sample_data/TOUCHPOINT_RECIBOS_201612131422.csv touchpoint-recibos
./load_files.sh ../etl/sample_data/TOUCHPOINT_SITECATALYST_201610260000.csv touchpoint-sitecatalyst

./load_files.sh ../etl/sample_data/MAESTRO_EMPLEADOS_SMILE_201703141401.csv smile-employee
./load_files.sh ../etl/sample_data/MAESTRO_SMILE_EVENT_201703210842.csv smile-event
./load_files.sh ../etl/sample_data/MAESTRO_SMILE_EVENT_LOCATION_201703161118.csv smile-event-location
./load_files.sh ../etl/sample_data/MAESTRO_SMILE_USER_201703151300.csv smile-user
```

## UI (and others) URLs
The following table depicts the URLs to access the UIs of the different parts that form the Sanitas environment:

| Service        | Port           | URL (docker) |
| ------------- |:-------------:| -----:|
| Docker UI | 9000 | <http://localhost:9999/> |
| HDFS | 50070 | <http://hadoop:50070/explorer.html> |
| Stratio Sparta UI | 9090 | <http://sparta:9090/> |
| Stratio Sparta Swagger UI | 9091 | <http://sparta:9091/swagger> |
| Stratio Viewer | 9000 | <http://viewer:9000/> |
| Mesos Master | 5050 | <http://mesos-master:5050/> |
| Mesos Dispatcher | 8081 | <http://mesos-master:8081/> |
| Zookeeper Web UI | 8085 | <http://localhost:8085/> |
| Stratio Ingestion agents | [port] | See subsection below |

### Stratio Ingestion agents URLs
Stratio Ingestion agents (_flume_ agents) do not have a UI of their own, but it is possible to check some of their metrics with their URLs. All these URLs have the form `http://ingestion:<port>/metrics`. Ports are (usually) in the range from 4015 to 4035. To know the ports of each agent you must open a session to the `ingestion` docker container and check the logs in folder `/var/log/sds/ingestion`. So, for example, to check the port of the `cliente-seguros` agent you can run:
```bash
$ docker exec -i -t  ingestion /bin/bash
root@ingestion:/opt/sds/ingestion# more /var/log/sds/ingestion/cliente-seguros.log | grep Started
15 Dec 2016 15:09:18,195 INFO  [conf-file-poller-0] (org.mortbay.log.Slf4jLog.info:67)  - Started SelectChannelConnector@0.0.0.0:4815
```
that output shows that the agent metrics can be accessed at port 4815 (`http://ingestion:4815/metrics`). Metrics are returned as a JSON document.

## Some useful commands
This is a list of commands that are definetely useful when operating with the Sanitas environment:

- List running docker containers. With this command you can know the containers' names, which you'll need to connect to them afterwards.
```bash
$ docker ps
docker ps
CONTAINER ID  IMAGE           COMMAND              CREATED          STATUS         PORTS                           NAMES
5b2cdf44967c  sanitas-viewer  "/etc/bootstrap.sh"  11 minutes ago   Up 11 minutes  22/tcp, 0.0.0.0:9000->9000/tcp  viewer
(...)
```

- Enter in one container (open a shell session in the container as `root`)
```bash
$ docker exec -i -t [CONTAINER_NAME_OR_ID] /bin/bash
```
For example, to open a shell in the kafka docker container:
```bash
$ docker exec -i -t scd-kafka /bin/bash
root@kafka:/opt#
```

- Restart a container
```bash
$ docker restart [CONTAINER_NAME_OR_ID]
```

- List kafka topics in use
```bash
$ docker exec -i -t kafka bash
root@kafka:/opt# cd kafka_2.11-0.10.0.1
root@kafka:/opt/kafka_2.11-0.10.0.1# ./bin/kafka-topics.sh --list --zookeeper zookeeper:2181
```

- Run a console consumer for kafka topic `touchpoints`, so check which data is going through that topic
```bash
$ docker exec -i -t kafka bash
root@kafka:/opt# cd kafka_2.11-0.10.0.1
root@kafka:/opt/kafka_2.11-0.10.0.1# ./bin/kafka-console-consumer.sh --zookeeper zookeeper:2181 --topic touchpoint --from-beginning
```

- Run spark shell:
```bash
$ docker exec -i -t mesos-master bash
root@mesos-master:/#/opt/sds/spark/bin/spark-shell --master mesos://mesos-master:5050
```

- Read data from HDFS in spark shell:
```bash
$ docker exec -i -t mesos-master bash
root@mesos-master:/#/opt/sds/spark/bin/spark-shell --master mesos://mesos-master:5050
scala> sqlContext.read.parquet("hdfs://hadoop:8020/seguros/data/datasets/touchpoint/*")
```

- Connect to database in Postgres (`sanitasdb`) and query the tables defined there along with their descriptions:
```bash
$ psql -h postgres -U sanitas sanitasdb
sanitasdb=> \d+
                                 List of relations
 Schema |               Name                | Type  |  Owner  |    Size    |          Description   
--------+-----------------------------------+-------+---------+------------+---------------------------------------------
 public | agg_client_amount                 | table | sanitas | 8192 bytes | Gastos por cliente y día | Orígenes de datos (...)
(...)
(14 rows)
```

## Save/restore dashboards built with Stratio Viewer
It is possible to save the dashboards built with Stratio Viewer so they can be restored afterwards if necessary, for example if the platform is reinstalled (which can remove all created dashboards). Dashboards are saved and restored as JSON files.

To save the dashboard configuration open the Viewer page with your browser, if you have installed it locally it should be at (http://viewer:9000). Then click on the `SETTINGS` option at the bottom of the main menu on the left. A new menu is shown, in that menu click on `Export`. The Export web page is shown, just click on `export all content`. A JSON file is created and downloaded automatically. That file contains the full description of the dashboard: pages, widgets in each page, data sources, data views...

To restore the dashboard on the Viewer menu at the left click again on `SETTINGS` and choose the `Import` option. A page is shown asking to choose the file with the dashboard description to upload it. That'd be the JSON file saved before. Choose that file and click `upload`. The dashboard will be restored. If the restoration is done on a dashboard that already had some pages configured then those pages will be shown alongside the restored ones (the same apply to the rest of elements restored).

### Use the already present dashboard definition after installing the platform
Just after the installation of the Stratio platform the dashboard is empty, but this project has a JSON file with a predefined dashboard for demonstration purposes. We can use the restoration mechanism defined in the previous section to install that file. The file is present at `sanitas-phase1/environment/containers/viewer/viewer-export.json`. You only need to import that file using the mechanism defined in the previous section (`SETTINGS` -> `Import` -> choose file and `upload`).

## Where to go from here?
Once the environment is installed in your system you can handle Stratio Sparta policies, use Stratio Viewer for data visualization, etc. A detailed description of how the Stratio components work can be found at http://docs.stratio.com/ .

Also, we suggest to check the README document in the `sanitas-phase1/analytics` folder to learn how to perform analysis tasks.
