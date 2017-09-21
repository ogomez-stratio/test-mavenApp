<img src="http://ic.sanitas.dom/git/data-science/scd-smile/raw/develop/resources/logosanitas.gif" align = "left"/>
<img src="http://ic.sanitas.dom/git/data-science/scd-smile/raw/develop/resources/logo-stratio-blue.png" align= "right"/>


# Sanitas Connected Data
# NPS Services
## Tabla de contenidos

- [Introducción](#Introducción)
- [Configuración](#configuracion)
- [API](#api)
- [Getting Started](#getting-started)
    - [Entorno Local](#entorno-local)
        - [Prerequisitos](#prerequisitos)
        - [Instalación ydespliegue](#instalacion-y-despliegue)
- [Casos de uso](#casosdeuso)
- [Changelog](#changelog)


## Introducción

El cometido del servicio **NPS** es conocer la relación de un cliente con Sanitas a través de las encuestas.

En esas encuestas, consultaremos las notas de recomendación de un cliente agrupada por canales.

*Nota: En esta versión(V 0.0.2) el microservicio consultará una base de datos y otro sistema será el encargado de rellena esa tabla.


<img src="http://ic.sanitas.dom/git/data-science/scd-nps/raw/develop/resources/arquitectura.png" align="center"/>


## API

### Actuator
 It adds several production grade services to your application with little effort on your part. In this guide, you’ll build an application and then see how to add these services.
 Here are some of the most common endpoints Boot provides out of the box:
- /health: Shows application health information (a simple ‘status’ when accessed over an unauthenticated connection or full message details when authenticated). It is not sensitive by default.
- /info: Displays arbitrary application info. Not sensitive by default.
- /metrics: Shows ‘metrics’ information for the current application. It is also sensitive by default.
- /trace: Displays trace information (by default the last few HTTP requests).
- /env: Information about the envioroment.

### SCDDataNps

- Microservicio de la capa de Datos encargado de la interacción (solo lectura) con los datos de la aplicación.

#### Data Alert Controller

|  | POST            | GET       | PUT         | PATCH          | DELETE |
| ----------- | --------------- | --------- | ----------- | -------------- | ------ |
| `/touchpoint-nps/{clientId}/channels/scores`       | | Consulta la tabla touchpoint_nps por **clientId** y devuelve las última nota de recomendación en el último año y agrupado por **canales** | |  |  |

> Los canales se configurarán en el fichero application{-profile}.yml.

```
Ejemplo Configuración:
  scd:
   channels:
      -
        channel: VOZ
        types:
          - VOZ
          - CALLBACK
      -
        channel: CORREO
        types:
          - EMAIL
      -
        channel: WEB
        types:
          - MISANITAS
      -
        channel: OFICINAS
        types:
          - OFICINAS
      -
        channel: ASINTENCIAL
        types:
          - URGENCIAS
          - CONSULTAS
          - DENTAL
          - HOSPITALIZACION
          - PRUEBAS
```

## Getting Started
### Entorno Local

Siguiendo estas instrucciones podrás tener un copia del proyecto funcionando en tu entorno local (docker compose).

#### Prerequisitos

* Debes configurar en tu id el soporte para lombok. Más información
 [aquí](https://projectlombok.org/)

* Debes ejecutar e intalar (maven clean install) el proyectos de scd-commons que podras encontrar [aquí](http://ic.sanitas.dom/git/data-science/scd-commons.git)

* La configuración necesaría para este entorno se encuentra en los properties application.yml y application-dev.yml

* Tener instalado docker y docker compose. Mas info [aquí](https://docs.docker.com/engine/installation/)

#### Instalación y despliegue

* Descarga y descomprime el proyecto, o bien clonalo: [scd-nps](http://ic.sanitas.dom/git/data-science/scd-nps.git)
* Ve a la carpeta `/environment`
* Ejecuta  los scripts de construcción y arranque

```
./build-images.sh && ./handle-containers.sh start

```
Estos scripts arrancarán todos los recursos necesarios para la ejecución de la aplicación, también efecturarán la carga de la tabla maestra de preguntas smile.

* Comprueba el estado de los servicios.

```
curl scddatanps:8091/admin/health


```

#### Modos de Empaquetado

  - Las aplicaciones pueden construirse de dos formas distintas:
   * **jar**, para su empaquetado como imagen docker corriendo como una aplicación springboot;
   * **war**, para su despliegue de forma convencional en un servidor de aplcicaciones.

  - Para ello existen dos perfiles disntintos dentro del pom.xml a los que se puede invocar con la opcion -P de Maven. Con el objetivo de facilitar
  la construcción de estos war se ha creado un script (war_build_images.sh) que hara dicha construcción dejando el war dentro de la carpeta target.
  Se puede ejecutar desde la carpeta environment:

```
 ./war_build_images.sh
```

## Casos de Uso

<img src="http://ic.sanitas.dom/git/data-science/scd-nps/raw/develop/resources/arquitectura.png" align="center"/>
