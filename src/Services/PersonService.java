package Services;

import DatabaseManager.DatabaseManager;
import Repository.PersonRepository;
import Model.Person;

import java.util.List;


public class PersonService {
    private final PersonRepository personRepository;
    private final AuditService auditService;

    public PersonService() {
        this.auditService = AuditService.getInstance();
        this.personRepository = PersonRepository.getInstance();
    }

    public Person findById(int id, DatabaseManager dbManager) {
        auditService.logAction("Person find by id ");
        return personRepository.findById(id, dbManager);
    }

    public List<Person> findAll(DatabaseManager dbManager) {
        auditService.logAction("Person find all ");
        return personRepository.findAll(dbManager);
    }

    public void create(String name, String email, DatabaseManager dbManager) {
        Person newPerson = new Person(0, name, email);
        personRepository.create(newPerson, dbManager);
        auditService.logAction("Person created ");
        System.out.println(newPerson + " has been created");
    }

    public void update(int id, String name, String email, DatabaseManager dbManager) {
        if (personRepository.findById(id, dbManager) == null) {
            System.out.println("Person with id " + id + " does not exist");
            return;
        }
        Person updatedPerson = new Person(id, name, email);
        personRepository.update(updatedPerson, dbManager);
        auditService.logAction("Person updated ");
        System.out.println("Person has been updated to " + updatedPerson);
    }

    public void delete(int id, DatabaseManager dbManager) {
        Person person = personRepository.findById(id, dbManager);
        if (person == null) {
            System.out.println("Person with id " + id + " does not exist");
            return;
        }

        personRepository.delete(id, dbManager);
        auditService.logAction("Person deleted ");
        System.out.println(person + " has been deleted");
    }
}