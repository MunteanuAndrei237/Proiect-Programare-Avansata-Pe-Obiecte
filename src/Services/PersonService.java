package Services;

import DatabaseManager.DatabaseManager;
import Repository.PersonRepository;
import Model.Person;
import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService() {
        this.personRepository = new PersonRepository();
    }

    public Person findById(int id, DatabaseManager dbManager) {
        return personRepository.findById(id, dbManager);
    }

    public List<Person> findAll(DatabaseManager dbManager) {
        return personRepository.findAll(dbManager);
    }

    public void create( String name, String email, DatabaseManager dbManager) {
        int personId = personRepository.findAll(dbManager).size() + 1;
        Person newPerson = new Person(personId, name, email);
        personRepository.create(newPerson, dbManager);
    }

    public void update(int id, String name, String email, DatabaseManager dbManager) {
        Person updatedPerson = new Person(id, name, email);
        personRepository.update(updatedPerson, dbManager);
    }

    public void delete(int id, DatabaseManager dbManager) {
        personRepository.delete(id, dbManager);
    }
}