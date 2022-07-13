package Deal.Services;

import Deal.Entity.Application;
import Deal.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    public void save (Application application){
        repository.save(application);
    }
}
