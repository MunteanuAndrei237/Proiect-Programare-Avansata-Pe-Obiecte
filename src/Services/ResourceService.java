package Services;

import Repository.ResourceRepository;
import DatabaseManager.DatabaseManager;
import Model.Resource;

//This class does not have CRUD implemented because it is not necessary for the project , it is used for the borrowing and returning of resources
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService() {
        this.resourceRepository = ResourceRepository.getInstance();
    }

    public Resource findById(int id, DatabaseManager dbManager) {
        return resourceRepository.findById(id, dbManager);
    }

    public void modifyInStock(int id, DatabaseManager dbManager, int quantity) {
        resourceRepository.modifyInStock(id, dbManager, quantity);
    }

    public void modifyToReturn(int id, DatabaseManager dbManager, int quantity) {
        resourceRepository.modifyToReturn(id, dbManager, quantity);
    }
}
