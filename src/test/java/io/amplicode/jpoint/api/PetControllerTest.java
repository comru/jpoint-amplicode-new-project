package io.amplicode.jpoint.api;

import io.amplicode.jpoint.model.Owner;
import io.amplicode.jpoint.model.PetType;
import io.amplicode.jpoint.repository.OwnerRepository;
import io.amplicode.jpoint.repository.PetRepository;
import io.amplicode.jpoint.repository.PetTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link PetController}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private OwnerRepository ownerRepository;
    private Long dogTypeId;
    private Long ownerId;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    public void setup() {
        PetType dogType = new PetType();
        dogType.setName("dog");
        dogTypeId = petTypeRepository.save(dogType).getId();

        Owner owner = new Owner()
                .setId(1L)
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setCity("Moscow")
                .setAddress("CMT")
                .setTelephone("9271110000");
        ownerId = ownerRepository.save(owner).getId();
    }

    @AfterEach
    void tearDown() {
        petRepository.deleteAll();
        petTypeRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    public void find() throws Exception {
        String q = "alex";

        mockMvc.perform(get("/rest/pets")
                        .param("q", q))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void saveTest() throws Exception {
        String petDto = """
                {
                	"name": "Buddy",
                	"birthDate": "2020-04-01",
                	"ownerId": %s,
                	"petTypeId": %s
                }""".formatted(ownerId, dogTypeId);

        mockMvc.perform(post("/rest/pets")
                        .content(petDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


        mockMvc.perform(get("/rest/pets")
                        .param("q", "ivanov"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
