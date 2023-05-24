package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.EditUser;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.CreateVegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.VegetableGardenRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.GardenSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.QuestionSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VegetableGardenService {

    private final VegetableGardenRepository vegetableGardenRepository;
    private final UserService userService;
    private final StorageService storageService;


    public List<VegetableGarden> findAll() {

        List<VegetableGarden> result = vegetableGardenRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return vegetableGardenRepository.findAll();
    }

    public PageDto<VegetableGardenResponse> search(Pageable pageable, User owner) {
        /*
        GardenSpecificationBuilder gardenSpecificationBuilder =
                new GardenSpecificationBuilder(params);
        Specification<VegetableGarden> spec =  gardenSpecificationBuilder.build(); //Spec queda nulo, porque?
*/
        Page<VegetableGardenResponse> pageGardenResponse = vegetableGardenRepository.findAllByOwner(owner,pageable).map(VegetableGardenResponse::fromGarden);
        if(pageGardenResponse.isEmpty())
            throw new EntityNotFoundException("No Gardens with this search criteria");
        return new PageDto<>(pageGardenResponse);
    }
    public VegetableGarden findById(Long id) {
        return vegetableGardenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }
   /* public List<VegetableGarden> findByUser(User user) {

        List<VegetableGarden> result = vegetableGardenRepository.findAllByOwner(user);

        if (result.isEmpty())
            throw new EntityNotFoundException("No hay huertos pa este usuario");

        return vegetableGardenRepository.findAll();

    }*/
    public VegetableGarden save(CreateVegetableGarden newHuerto, User user, MultipartFile file) {
        String fileName = storageService.store(file);
        return vegetableGardenRepository.save(
                VegetableGarden.builder()
                        .name(newHuerto.getName())
                        .latitude(newHuerto.getLatitude())
                        .longitude(newHuerto.getLongitude())
                        .owner(user)
                        .img(fileName)
                        .build());

    }
    public void deleteById(Long id) {
        //Esto es una gitanada, perdón
        if (vegetableGardenRepository.existsById(id)){
            vegetableGardenRepository.deleteById(id);
        }

    }

    public VegetableGarden edit(VegetableGarden garden, CreateVegetableGarden newGarden, MultipartFile file, User user) {
        if(userService.checkUserLogedInGarden(user.getId(), garden.getId())) {
            storageService.deleteFile(garden.getImg());
            String fileName = storageService.store(file);
            garden.setName(newGarden.getName());
            garden.setImg(fileName);
            garden.setLongitude(newGarden.getLongitude());
            garden.setLatitude(newGarden.getLatitude());
        }
        return vegetableGardenRepository.save(garden);
    }
}
