package server.service.habit.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;
import server.exception.habit.transport.PublicTransportNotFoundException;
import server.exception.habit.transport.TravelByBikeNotFoundException;
import server.repository.habit.transport.PublicTransportRepository;
import server.repository.habit.transport.TravelByBikeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TransportServiceTest {

    @Mock
    PublicTransportRepository publicTransportRepository;

    @Mock
    TravelByBikeRepository travelByBikeRepository;

    @InjectMocks
    TransportService transportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createTravelByBike() {
        TravelByBike travelByBike = TravelByBike.builder().build();

        when(travelByBikeRepository.save(any(TravelByBike.class))).thenReturn(travelByBike);
        TravelByBike created = transportService.createTravelByBike(travelByBike);

        verify(travelByBikeRepository, times(1)).save(travelByBike);
        verifyNoMoreInteractions(travelByBikeRepository);
    }

    @Test
    void findTravelByBikeById() {
        TravelByBike travelByBike = TravelByBike.builder().build();

        when(travelByBikeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(travelByBike));
        TravelByBike found = transportService.findTravelByBikeById((long) 1);

        verify(travelByBikeRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(travelByBikeRepository);
    }

    @Test
    void findTravelByBikeByIdNotFound() {
        when(travelByBikeRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(TravelByBikeNotFoundException.class, () -> transportService.findTravelByBikeById((long) 1));
    }

    @Test
    void deleteTravelByBike() {
        TravelByBike travelByBike = TravelByBike.builder().build();

        transportService.deleteTravelByBike(travelByBike);

        verify(travelByBikeRepository, times(1)).delete(travelByBike);
        verifyNoMoreInteractions(travelByBikeRepository);
    }

    @Test
    void updateTravelByBike() {
        TravelByBike travelByBike = TravelByBike.builder().build();

        transportService.updateTravelByBike(travelByBike);

        verify(travelByBikeRepository, times(1)).save(travelByBike);
        verifyNoMoreInteractions(travelByBikeRepository);
    }

    @Test
    void createPublicTransport() {
        PublicTransport publicTransport = PublicTransport.builder().build();

        when(publicTransportRepository.save(any(PublicTransport.class))).thenReturn(publicTransport);
        PublicTransport created = transportService.createPublicTransport(publicTransport);

        verify(publicTransportRepository, times(1)).save(publicTransport);
        verifyNoMoreInteractions(publicTransportRepository);
    }

    @Test
    void findPublicTransportById() {
        PublicTransport publicTransport = PublicTransport.builder().build();

        when(publicTransportRepository.findById((long) 1)).thenReturn(Optional.ofNullable(publicTransport));
        PublicTransport found = transportService.findPublicTransportById((long) 1);

        verify(publicTransportRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(publicTransportRepository);
    }

    @Test
    void PublicTransportByIdNotFound() {
        when(publicTransportRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(PublicTransportNotFoundException.class, () -> transportService.findPublicTransportById((long) 1));
    }

    @Test
    void deletePublicTransport() {
        PublicTransport publicTransport = PublicTransport.builder().build();

        transportService.deletePublicTransport(publicTransport);

        verify(publicTransportRepository, times(1)).delete(publicTransport);
        verifyNoMoreInteractions(publicTransportRepository);
    }

    @Test
    void updatePublicTransport() {
        PublicTransport publicTransport = PublicTransport.builder().build();

        transportService.updatePublicTransport(publicTransport);

        verify(publicTransportRepository, times(1)).save(publicTransport);
        verifyNoMoreInteractions(publicTransportRepository);
    }
}