package com.gym.gym.service;

import com.gym.gym.dto.ClientLockerDto;
import com.gym.gym.entity.Client;
import com.gym.gym.entity.ClientLocker;
import com.gym.gym.entity.Locker;
import com.gym.gym.exception.exceptions.EntityNotFoundException;
import com.gym.gym.exception.messages.ExceptionMessages;
import com.gym.gym.mapper.ClientLockerMapper;
import com.gym.gym.repository.ClientLockerRepository;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientLockerService {

    private final ClientLockerRepository clientLockerRepository;
    private final ClientRepository clientRepository;
    private final LockerRepository lockerRepository;

//    private final ClientLockerMapper clientLockerMapper;


//    @Bean
//    private ClientLockerMapper mapper(){
//        return new ClientLockerMapper(clientRepository, lockerRepository);
//    }

    public List<ClientLockerDto> getACLDto() {
        return clientLockerRepository.findAll()
                .stream()
                .map(c->ClientLockerMapper.toDto(c))
                .collect(Collectors.toList());
    }


    public ClientLockerDto save(Long userId) {
        Client clientToSave = clientRepository.findClientByUserId(userId);
        Locker lockerToSave;

        boolean sex = clientToSave.isAWoman();

        if (sex) {
            lockerToSave = lockerRepository.findLockerByLockerId(getOptimalIdLocker(true));
        } else {
            lockerToSave = lockerRepository.findLockerByLockerId(getOptimalIdLocker(false));
        }
        lockerToSave.setAvailable(false);
        lockerRepository.save(lockerToSave);


        if (checkIfWentHome(userId)) {
            throw new RuntimeException();
        }

        ClientLocker clientLocker = ClientLocker.builder()
                .locker(lockerToSave)
                .client(clientToSave)
                .entry(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ClientLockerMapper.toDto((clientLockerRepository.save(clientLocker)));
    }

    public ClientLockerDto goHome(Long user) {
        Locker lockerToUpdate;
        ClientLocker clientLocker = clientLockerRepository.findAllByGoHomeIsNull()
                .stream()
                .filter(c -> c.getClient().getUserId().equals(user))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException(ExceptionMessages.ENTITY_NOT_FOUND.getMessage()));

        lockerToUpdate = clientLocker.getLocker();
        lockerToUpdate.setAvailable(true);
        lockerRepository.save(lockerToUpdate);
        clientLocker.setGoHome(Timestamp.valueOf(LocalDateTime.now()));

        Client client = clientRepository.findClientByUserId(user);
        client.setAverageTime(getAverageTime(user));
        clientRepository.save(client);
        clientLocker.setGoHome(Timestamp.valueOf(LocalDateTime.now()));
        return ClientLockerMapper.toDto((clientLockerRepository.save(clientLocker)));


    }


    private List<Locker> getAvailableLockers(boolean isAWoman) {
        return lockerRepository.findAll().stream()
                .filter(l -> l.isAWomenLocker() == isAWoman)
                .filter(Locker::isAvailable)
                .collect(Collectors.toList());
    }

    private int getAverageTime(Long userId) {

        List<Long> list = clientLockerRepository.findAll()
                .stream()
                .filter(c -> Objects.equals(c.getClient().getUserId(), userId))
                .map(c -> (c.getGoHome().toInstant().getEpochSecond() - c.getEntry().toInstant().getEpochSecond()))
                .toList();

        double average = list.stream()
                .mapToLong(l -> l)
                .average()
                .orElseThrow(()-> new EntityNotFoundException(ExceptionMessages.AVERAGE_TIME_IS_UNAVAILABLE.getMessage()));
        return (int) average;
    }


    private boolean checkIfWentHome(Long userId) {

        List<ClientLocker> clientLockers = clientLockerRepository.findAll().stream()
                .filter(c -> c.getClient().getUserId().equals(userId))
                .filter(c -> c.getGoHome().equals(null))
                .toList();
        return clientLockers.size() > 0;
    }


    public List<ClientLocker> getAllClientLockers(boolean isAWoman) {
        return clientLockerRepository.findAll().stream()
                .filter(l -> l.getLocker().isAWomenLocker() == isAWoman)
                .collect(Collectors.toList());
    }


    private List<ClientLocker> checkClientIsNotAboutFinish(List<ClientLocker> clientLockers) {

        return clientLockers.stream()
                .filter(c -> c.getGoHome() == null)
                .filter(l -> checkApproximateExitTime(l.getEntry(), l.getClient().getAverageTime()))
                .toList();

    }

    private boolean checkApproximateExitTime(Timestamp entry, int averageTime) {
        long time = entry.getTime();

        time += averageTime;
        Timestamp approximateTime = new Timestamp(time);


        if (approximateTime.compareTo(Timestamp.valueOf(LocalDateTime.now().minusMinutes(5))) > 0 &&
                approximateTime.compareTo(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5))) < 0) {
            return true;
        }
        return false;

    }


    private List<Long> getAvailableLockerId(boolean isAWoman) {
        return getAvailableLockers(isAWoman).stream()
                .map(Locker::getLockerId)
                .toList();
    }

    private List<Long> getLockerIdWhichAreGoHome(boolean isAWoman) {

        return checkClientIsNotAboutFinish(getAllClientLockers(isAWoman)).stream()
                .map(l -> l.getLocker().getLockerId())
                .toList();
    }


    private List<Long> getLockerIdWhichJustArrived(boolean isAWoman) {
        return clientLockerRepository.findAllByGoHomeIsNull().stream()
                .filter(c -> c.getClient().isAWoman() == isAWoman)
                .filter(c -> c.getEntry().compareTo(Timestamp.valueOf(LocalDateTime.now().minusMinutes(5))) > 0)
                .map(c -> c.getLocker().getLockerId())
                .toList();
    }

    private Long getOptimalIdLocker(boolean isAWoman) {
        Long optimalIdLocker;
        List<Long> finishAndJustArrived = new LinkedList<>();
        finishAndJustArrived.addAll(getLockerIdWhichAreGoHome(isAWoman));
        finishAndJustArrived.addAll(getLockerIdWhichJustArrived(isAWoman));
        List<Long> getOptimalIdLockers = getOptimalNumber(getAvailableLockerId(isAWoman), finishAndJustArrived);

        if (getOptimalIdLockers.isEmpty()) {
            optimalIdLocker = getAvailableLockerId(isAWoman).stream().findFirst().orElseThrow();
        } else {
            optimalIdLocker = getOptimalIdLockers.stream().findFirst().orElseThrow();
        }

        return optimalIdLocker;
    }


    private List<Long> getOptimalNumber(List<Long> available, List<Long> busy) {
        List<Long> goodLocker = new LinkedList<>();

        for (int i = 0; i < available.size(); i++) {
            if (busy.contains(available.get(i) - 1) || busy.contains(available.get(i) + 1)) {
                continue;
            } else goodLocker.add(available.get(i));
        }

        return goodLocker;
    }

    public boolean existsById(Long idClient) {
        Client clientByUserId = clientRepository.findClientByUserId(idClient);

        if (clientByUserId == null){
            return false;
        }
        return true;

    }

    public boolean stillTraining(Long idClient){

        return getACLDto().stream()
                .filter(c -> c.getGoHome()==null)
                .anyMatch(c->c.getIdClient().equals(idClient));

    }
}

