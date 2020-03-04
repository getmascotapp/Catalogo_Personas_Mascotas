package catalogo_personas_mascotas
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.CREATED
import catalogo_personas_mascotas.Person
import grails.gorm.transactions.Transactional

class PersonController {
    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    // metodo para mostrar lista de todos los datos registrados en la tabla Person
    def index() {
        // devuelve la lista de personas al arreglo personList
        def personList=Person.list()
        return [personList:personList]
    }
    def show(Long id){
        def person=Person.get(id)
        def petList=Pet.findAllByPerson(person)
        return [person:person,petList:petList]

    }
    def create(){}
    def save(){
        def person= new Person(params)
        if (person.validate()) {
            person.save()
            flash.message='Se guardo correctamente'
            redirect action: 'index'
        }
        else {
            render( view:'create',model:[person:person])
        }

    }

    def edit(Long id){
        def person=Person.get(id)
        return [person:person]
    }

    def update(Long id){
        Person person= Person.get(id)
        person.properties= params
        if(person.validate()){
            person.save(flush : true) // se utiliza el flush para que se conserve el dato y no genere otro
            flash.message='Se edito correctamente el dato con el nombre '+person.name
            redirect action: 'index'
        }else{
            render( view:'edit',model:[person:person])
        }

    }


}
