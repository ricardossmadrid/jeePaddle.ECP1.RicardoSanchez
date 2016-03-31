# Tecnología Spring: Proyecto Padel
#### Back-end con Tecnologías de Código Abierto (SPRING)
#### [Máster en Ingeniería Web por la U.P.M.](http://miw.etsisi.upm.es)

> Proyecto básicos para el uso de la tecnología Spring


#### ECP1. Persistencia
> A Modificar la capa de persistencia para que los tokens caduquen en 1 hora. Se deberá ofrecer una funcionalidad de eliminación de tokens caducados:

* Se añade a la clase Token el atributo "Calendar expirationTime", que se inicializa con una hora después de la hora de creación del atributo. Además, se incluyen las funciones setter, getter e isExpired, que devuelve true si el token ha caducado.
* Se modifica el tipo devuelto de findByUser en el TokenDao, de Token a List<Token>.
* Se incluye la query deleteExpiredTokens de TokenDao.

> B Ampliar la capa de persistencia para poder ofrecer un servicio de clases de padel. El diseño es abierto:

* Se crea una entidad Training con los atributos especificados. Se incluyen los métodos addPlayer y removePlayer.
* Se crea el Dao TrainingDao, con la función que elimina entrenamientos por id.

> C Modificar o realizar los test de las mejorar anteriores:

* Se crea un test para la comprobación de la correcta creación del expirationToken y otro para comprobar si está o no expirado un token.
* Se construye test para la comprobación del borrado de los tokens caducados.
* Se incluyen los test de la funcionalidad de Training TrainingDaoITest, creando datos en DaosService.


#### ECP1. Api
> A Modificar la capa de negocio para que la validación de tokens incluya la mejora de caducidad:

* Se implementa una nueva manera de validar un usuario en función de la validez del token que tiene en UserDetailsServiceImpl.

> B Ampliar la capa de negocio para poder ofrecer un servicio de clases de padel, incluyendo la seguridad. El diseño es abierto:

* Se crea TrainingResource como API de la funcionalidad de entrenamiento. Incluyendo algunas nuevas excepciones al paquete business.api.exceptions.
* Se añade función "exist" al UserController.
* Se crea un CreateTrainingWrapper y un TrainingWrapper.
* Se implementa el controlador de la funcionalidad entrenamiento, TrainingController.

> C Modificar o realizar los test de las mejorar anteriores:
* Se crea un usuario Trainer para realizar los test oportunos.
* Se implementan algunos de los test necesarios para comprobar la funcionalidad del entrenamiento. 


#### ECP1. Web
> A Realizar la capa Web de algunas de las operaciones de la aplicación de Paddle, sin seguridad:
* Se configura el proyecto para el uso de vistas mediante tecnologías JSP.
* Se crea la vista para observar las pistas existentes mediante una tabla. 



###### Autor: Ricardo Sánchez Sánchez U.P.M.

