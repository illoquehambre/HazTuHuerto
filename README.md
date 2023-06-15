# Haz Tu Huerto
## API REST con SPRING - V1 de lo que se´ra el proyecto final de 2ºDAM.

<img src="https://img.shields.io/badge/Spring--Framework-5.7-green"/> <img src="https://img.shields.io/badge/Apache--Maven-3.8.6-blue"/> <img src="https://img.shields.io/badge/Java-17.0-brightgreen"/>

<div>
 <img src="https://niixer.com/wp-content/uploads/2020/11/spring-boot.png" width="425" alt="Spring Logo"/>
 <img src="https://styles.redditmedia.com/t5_2su6s/styles/communityIcon_4g1uo0kd87c61.png" width="250" alt="React Logo"/>
 <img src="https://storage.googleapis.com/cms-storage-bucket/0dbfcc7a59cd1cf16282.png" width="200" alt="Flutter Logo"/>
</div>
___


## **Documentación**

:point_right: [Dirección SWAGGER-IO](http://localhost:8080/swagger-ui-hazTuHuerto.html)

:point_right: Se incluye también una colección de Postman para probar datos.


## **Introducción** :speech_balloon:

Este es un ejercicio práctico para el desarrollo de una API REST en lenguaje Java y manejando diferentes tecnologías en la que destaca **Spring** así como un frontend en JavaScript con el uso de React y una parte nativa para mvil usando Flutter.

También se ha prácticado el manejo de **PostMan**, **Swagger**, **Figma**.

El proyecto trata de realizar una API REST para la gestión de un huerto propio, sin embargo la aplicación está lejos de estar terminada y tener el 100% de las funcionalidades.



Se pueden realizar las siguientes funcionalidades: 	:point_right:
* Login de USUARIO
* Listado de USUARIO
* Busqueda de un USUARIO por su id
* Busqueda de un USUARIO por su nombre
* Creación de un nuevo USUARIO
* Edición del perfil de un USUARIO con subida de ficheros (NO operativo en el front)
* Edición de la contraseña de un USUARIO
* Creación de un USUARIO con rol ADMIN
* Borrado de un USUARIO en base a **Soft Delete**

* Listado de las PREGUNTAS
* Listado de las PREGUNTAS por usuario
* Búsqueda de un PREGUNTA por su id
* Creación de una nueva PREGUNTA con subida de ficheros (NO operativo en el front)
* Borrado de una PREGUNTA

* Listado de todas las RESPUESTAS
* Listado de las RESPUESTAS de una determinada PREGUNTA localizada por su id
* Búsqueda de una RESPUESTA buscada por su id
* Creación de un nueva RESPUESTA para una PREGUNTA
* Borrado de una RESPUESTA localizada por su id

* Listado de todas los HUERTOS del usuario
* Detalles del HUERTO con sus respectivo listado de parcelas
* Edit de un HUERTO por su id
* Creación de un nuevo HUERTO 
* Borrado de un HUERTO localizada por su id

* Detalles de una PARCELA con los detalles de su cultivo y las notas del mismo
* División de una PARCELA, de forma que se genera otra manteniendo el historiald e cultivos
* Edit de una PARCELA por su id
* Creación de una nueva PARCELA 
* Borrado de una PARCELA por su id
* Recogida del cultivo actual de la PARCELA, generando al mismo tiempo otro nuevo

* Creación de una NOTA
* Detalles de una NOTA
* Edit de una NOTA por su id
* Delete de una NOTA

*Cabe destacar que dichos endpoints implementan validación y gestión de errores personalizadas
 así como uso de Search Criteria y pageable en los métodos FindAll

---

## **Tecnologías utilizadas** 

Para realizar este proyecto hemos utilizado:

1. [Spring Boot 2.7.5](https://spring.io/) - Dependencias: **Spring-Web**, **JPA-HIBERNATE**, **H2 Database**, **Sprin-doc Open-api**, **Lombok**
2. [Apache Maven 3.8.6](https://maven.apache.org/)
3. [Postman](https://www.postman.com/)
4. [GitHub](https://github.com/)
5. [springdoc 1.6.13](https://springdoc.org/)
6. [Swagger](https://swagger.io/)
7. [React](https://es.reactjs.org)
8. [Figma](https://www.figma.com)
9. [Flutter](https://docs.flutter.dev)
10.[Docker](https://www.docker.com/products/docker-desktop/) 




### Ejemplos del Código Usado: 

**JAVA - SPRING**:
```Java
   @GetMapping(value = "/user/{name}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> image(@PathVariable String name) throws IOException {
        User user= userService.findByUsername(name);
        Resource img = storageService.loadAsResource(user.getAvatar());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(img);

    }

```

**Documentación**

```Java
    @Operation(summary = "This method upload a user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The user was not found",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "The data sended was not correct",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
```
**JAVASCRIPT - REACT**:
```JavaScript
    export default function Like(quest) {
        const apiUrl = `http://localhost:8080/question/`;
        const [location, setLocation] = useLocation();
        const [question, setQuestion]=useState(quest)
        const [like, setLike]=useState()

    async function fetchLike(apiUrl,id) {

        return fetch(apiUrl +id+ '/like', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
            }
        }).then(res => {

            if (res.ok) {
                return res.json();
            }
            throw new Error('Something went wrong');
        })
        .then((data) => {
            setQuestion(data)
            console.log(data)
            setLike(data.likedByLoguedUser)
        })
        .catch((error) => {
            console.log(error)
            setLocation('/Page404')
        });  
    };

        const handleSubmit = async (e) => {

            const response = await fetchLike(apiUrl,quest.id);

          };




        return (
            <div className="like">
                <img onClick={handleSubmit} src={like?'/img/heart.png':'/img/heart_1.png'}></img>
                <p>Likes: {question.score}</p>
            </div>

        );
    }
```
**DART - FLUTTER - BLOC**:
```
class _QuestionState extends State<Question> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
        child: GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          PageRouteBuilder(
            transitionDuration: const Duration(milliseconds: 500),
            transitionsBuilder:
                (context, animation, secondaryAnimation, child) {
              return FadeTransition(
                opacity: animation,
                child: child,
              );
            },
            pageBuilder: (context, animation, secondaryAnimation) {
              return QuestionDetailsPage(id: widget.question.id);
            },
          ),
        );
      },
      child: Card(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        margin: EdgeInsets.all(15),
        elevation: 10,
        child: Column(
          children: <Widget>[
            ListTile(
              contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
              title: Text(widget.question.title),
              subtitle: Text(widget.question.content),
              leading:
                  Text(widget.question.id.toString(), style: textTheme.bodySmall),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: <Widget>[
             // Espacio entre el primer Text y el segundo
                 Text('Answers: '+widget.question.answers.toString(), style: textTheme.bodySmall),
                
                
              ],
            ),
          ],
        ),
      ),
    ));
  }

```
---
## **Arranque**

Realiza un *git clone* de la siguiente dirección: 
*https://github.com/illoquehambre/HazTuHuerto*

```console
git clone https://github.com/illoquehambre/azTuHuerto.git
```

Dirígete hasta la carpeta:

> cd ./HazTuHuertoAPI/


**Primero** tienes que tener instalado Apache Maven y sería recomendable tener alguna IDE, como **Intellij IDEA** o **VisualStudio Code**

Ejecuta el siguiente comando:
    
    mvn compile
    
    
Ejecuta el siguiente comando:
    
    mvn clean

Ejecuta el comando:

    docker-compose up
    
Ejecuta el comando:

    mvn spring-boot:run
    
    
Si diese algún error, realiza el siguiente comando:  

    mvn dependencies:resolve
    ---> 100% 
    
Con esto ya podría realizar peticiones desde postman a la dirección http://localhost:8080
Una vez corriendo la api, dirijase a la carpeta haz_tu_huerto_front

    cd ../haz_tu_huerto_front
    
Ejecute el siguiente comando:

    npm install

Ejecute el siguiente comando:

    npm start

Con esto el front web estaría corriendo y se podría acceder desde un navegador a part de la ruta http://localhost:3000

En cuanto a la parte mobil, dirijase a la carpeta haz_tu_huerto_mobile_2

     cd ../haz_tu_huerto_mobile_2
     
Ejecute el siguiente comando:

    flutter pub get

Ejecute el siguiente comando:

    flutter run
    
 Tenga en cuenta que para esto deberá disponer previamente instalado tanto flutter como react, asi como cualquier otro medio posiblemenete necesario como puede ser el caso de un emulador.

___
## **Autor**

Este proyecto ha sido realizado por: 

* [Ignacio Moreno Gómez - GITHUB](https://github.com/illoquehambre)


Proyecto fin de grado como programador Junior FullStack.

### **Thump up :+1: And if it was useful for you, star it! :star: :smiley:**

___
## **TODO**

Revisar el documento TODO.txt el cual indica todo aquello que no se ha podido hacer todavía así como los posibles errores que pueden salir

[ ] Fix possible future errors

Este proyecto no se encuentra actualmente finalizado, la version actual es una demo provisional de la app completa. La cual seguirá en desarrollo durante el futuro próximo.
___


REadme inspired by [Rogelio - GITHUB](https://github.com/RogeMB)
