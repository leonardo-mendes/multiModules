package controllersTests;

import com.google.gson.Gson;
import configTests.CarControllerConfig;
import controllers.CarController;
import domains.Car;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import services.CarService;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CarControllerConfig.class)
public class CarControllerTests {

    private static final String API_URL = "/car";
    private Gson gson;
    private MockMvc mockMvc;

    private Car buildValidCar() {
        return new Car("Valid Car");
    }

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Before
    public void setUp() {
        gson = new Gson();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void ShouldListAllCars() throws Exception {
        when(carService.findAll()).thenReturn(Arrays.asList(buildValidCar()));
        mockMvc.perform(get(API_URL)).andExpect(status().isOk());
        verify(carService, times(1)).findAll();
    }

    @Test
    public void ShouldFindACarById() throws Exception {
        when(carService.findById(anyInt())).thenReturn(buildValidCar());
        mockMvc.perform(get(API_URL + "/{id}", 1)).andExpect(status().isOk());
        verify(carService, times(1)).findById(anyInt());
    }

    @Test(expected = Exception.class)
    public void ShouldNotFindACarById() throws Exception {
        when(carService.findById(anyInt())).thenReturn(buildValidCar());
        mockMvc.perform(get(API_URL + "/{id}", null)).andExpect(status().isOk());
    }

    @Test
    public void ShouldCreateACar() throws Exception {
        when(carService.insert(any())).thenReturn(buildValidCar());
        mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(buildValidCar()))).andExpect(status().isNoContent());
        verify(carService, times(1)).insert(any());
    }

    @Test
    public void ShouldNotCreateACarWithInvalidBody() throws Exception {
        when(carService.insert(any())).thenReturn(buildValidCar());
        mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(null))).andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldUpdateACar() throws Exception {
        when(carService.update(anyInt(), any())).thenReturn(buildValidCar());
        mockMvc.perform(put(API_URL + "/update/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(buildValidCar()))).andExpect(status().isOk());
        verify(carService, times(1)).update(anyInt(), any());
    }

    @Test(expected = Exception.class)
    public void ShouldNotUpdateACarWithInvalidId() throws Exception {
        when(carService.update(anyInt(), any())).thenReturn(buildValidCar());
        mockMvc.perform(put(API_URL + "/update/{id}", null).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(buildValidCar()))).andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldNotUpdateACarWithInvalidCar() throws Exception {
        when(carService.update(anyInt(), any())).thenReturn(buildValidCar());
        mockMvc.perform(put(API_URL + "/update/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(null))).andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldDeleteACar() throws Exception {
        doNothing().when(carService).delete(anyInt());
        mockMvc.perform(delete(API_URL + "/delete/{id}", 1)).andExpect(status().isNoContent());
        verify(carService, times(1)).delete(anyInt());
    }

    @Test(expected = Exception.class)
    public void ShouldNotDeleteACarWithInvalidId() throws Exception {
        doNothing().when(carService).delete(anyInt());
        mockMvc.perform(delete(API_URL + "/delete/{id}", null)).andExpect(status().isBadRequest());
    }

}
