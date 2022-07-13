package Deal.Services;

import Deal.Entity.Client;
import Deal.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public void save (Client client){
        repository.save(client);
    }
}
