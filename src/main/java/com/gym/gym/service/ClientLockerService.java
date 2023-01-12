package com.gym.gym.service;

import com.gym.gym.dto.ClientLockerDto;
import com.gym.gym.entity.Client;
import com.gym.gym.entity.ClientLocker;
import com.gym.gym.entity.Locker;
import com.gym.gym.mapper.ClientLockerMapper;
import com.gym.gym.repository.ClientLockerRepository;
import com.gym.gym.repository.ClientRepository;
import com.gym.gym.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log
@Transactional
@RequiredArgsConstructor
public class ClientLockerService {

    private final ClientLockerRepository clientLockerRepository;
    private final ClientRepository clientRepository;
    private final LockerRepository lockerRepository;

    private final ClientLockerMapper clientLockerMapper;

    public List<ClientLockerDto> getACLDto() {
        return clientLockerRepository.findAll()
                .stream()
                .map(clientLockerMapper::toDto)
                .collect(Collectors.toList());
    }


    public ClientLockerDto save(Long userId) {
        Client clientToSave = clientRepository.findClientByUserId(userId);
        Locker lockerToSave;

        boolean sex = clientToSave.isAWoman();

        if (sex) {
            lockerToSave = getAvailableWomanLockers().stream().findFirst().orElseThrow();
        } else {
            lockerToSave = getAvailableManLockers().stream().findFirst().orElseThrow();
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
        return clientLockerMapper.toDto((clientLockerRepository.save(clientLocker)));
    }

    public ClientLockerDto goHome(Long user) {
        Locker lockerToUpdate;
        ClientLocker clientLocker = clientLockerRepository.findAllByGoHomeIsNull()
                .stream()
                .filter(c -> c.getClient().getUserId().equals(user))
                .findFirst().orElseThrow();

        lockerToUpdate = clientLocker.getLocker();
        lockerToUpdate.setAvailable(true);
        lockerRepository.save(lockerToUpdate);
        clientLocker.setGoHome(Timestamp.valueOf(LocalDateTime.now()));

        Client client = clientRepository.findClientByUserId(user);
        client.setAverageTime(getAverageTime(user));
        clientRepository.save(client);
        clientLocker.setGoHome(Timestamp.valueOf(LocalDateTime.now()));
        return clientLockerMapper.toDto((clientLockerRepository.save(clientLocker)));


    }


    private List<Locker> getAvailableManLockers() {
        return lockerRepository.findAll().stream()
                .filter(l -> !l.isAWomenLocker())
                .filter(Locker::isAvailable)
                .collect(Collectors.toList());
    }

    private List<Locker> getAvailableWomanLockers() {
        return lockerRepository.findAll().stream()
                .filter(Locker::isAWomenLocker)
                .filter(Locker::isAvailable)
                .collect(Collectors.toList());
    }

    private int getAverageTime(Long userId) {

        Long num;
        List<Long> list = clientLockerRepository.findAll()
                .stream()
                .filter(c -> Objects.equals(c.getClient().getUserId(), userId))
                .map(c -> (c.getGoHome().toInstant().getEpochSecond() - c.getEntry().toInstant().getEpochSecond()))
                .toList();

        double average = list.stream().mapToLong(l -> l).average().orElseThrow();
        return (int) average;
    }


    private boolean checkIfWentHome(Long userId) {

        List<ClientLocker> clientLockers = clientLockerRepository.findAll().stream()
                .filter(c -> c.getClient().getUserId().equals(userId))
                .filter(c -> c.getGoHome().equals(null))
                .toList();
        return clientLockers.size() > 0;
    }

    public List<ClientLockerDto> getAllManClientLocker() {
        return toListDto(getAllManClientLockers());
    }

    private List<ClientLocker> getAllManClientLockers() {
        return clientLockerRepository.findAll().stream()
                .filter(l -> l.getLocker().isAWomenLocker() == false)
                .collect(Collectors.toList());
    }

    public List<ClientLockerDto> getAllWomanClientLocker() {
        return toListDto(getAllWomanClientLockers());
    }

    public List<ClientLocker> getAllWomanClientLockers() {
        return clientLockerRepository.findAll().stream()
                .filter(l -> l.getLocker().isAWomenLocker())
                .collect(Collectors.toList());
    }

    private List<ClientLockerDto> toListDto(List<ClientLocker> list) {
        return list.stream()
                .map(clientLockerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClientLockerDto> checkClientIsNotAboutFinishDtoMan() {
        return toListDto(checkClientIsNotAboutFinish(getAllManClientLockers()));
    }

    public List<ClientLockerDto> checkClientIsNotAboutFinishDtoWoman() {
        return toListDto(checkClientIsNotAboutFinish(getAllWomanClientLockers()));
    }

    private List<ClientLocker> checkClientIsNotAboutFinish(List<ClientLocker> clientLockers) {

        return clientLockers.stream()
                .filter(c -> c.getGoHome() == null)
                .filter(l -> checkApproximateExitTime(l.getEntry(), l.getClient().getAverageTime()))
                .toList();

    }

    private boolean checkApproximateExitTime(Timestamp entry, int averageTime) {
        long time = entry.getTime();

        Timestamp approximateTime2 = new Timestamp(time);
        time += averageTime;
        Timestamp approximateTime = new Timestamp(time);

        log.info("approximateTime2: " + approximateTime2.toString());
        log.info("approximateTime: " + approximateTime.toString());
        Integer int1 = approximateTime.compareTo(Timestamp.valueOf(LocalDateTime.now().minusMinutes(5)));
        Integer int2 = approximateTime.compareTo(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));

        log.info(int1.toString());
        log.info(int2.toString());


        if (approximateTime.compareTo(Timestamp.valueOf(LocalDateTime.now().minusMinutes(5))) > 0 &&
                approximateTime.compareTo(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5))) < 0) {
            return true;
        }
        return false;

    }



    public List<Long> getLockerManIdWhichAreGoHome(){

     return  checkClientIsNotAboutFinish(getAllManClientLockers()).stream()
                .map(l->l.getLocker().getLockerId())
                .toList();
    }

    public List<Long> getAvailableLockerManId(){
        return getAvailableManLockers().stream()
                .map(Locker::getLockerId)
                .toList();
    }

    public List<Long> getAvailableLockerWomanId(){
        return getAvailableWomanLockers().stream()
                .map(Locker::getLockerId)
                .toList();
    }


    public List<Long> getLockerWomenIdWhichAreGoHome(){

        return  checkClientIsNotAboutFinish(getAllWomanClientLockers()).stream()
                .map(l->l.getLocker().getLockerId())
                .toList();
    }

}



//
//    private List<Long> checkClientIsNotAboutFinishLockerId(boolean isAWoman) {
//        if (isAWoman) {
//            return checkClientIsNotAboutFinish(getAllWomanClientLocker()).stream()
//                    .map(c -> c.getLocker().getLockerId())
//                    .toList();
//
//        }
//        return checkClientIsNotAboutFinish(getAllManClientLocker()).stream()
//                .map(c -> c.getLocker().getLockerId())
//                .toList();
//    }

//    private List<Long> getAvailableLocker(boolean isAWoman){
//        if (isAWoman){
//            return lockerRepository.findAll().stream()
//                    .filter(Locker::isAWomenLocker)
//                    .map(Locker::getLockerId)
//                    .toList();
//        }
//
//        return lockerRepository.findAll().stream()
//                .filter(locker -> !locker.isAWomenLocker())
//                .map(Locker::getLockerId)
//                .toList();
//    }


//    private Locker getOptimalLocker(boolean isAWoman){
//List<Long> availableLocker = getAvailableLocker(isAWoman);
//List<Long> clientIsNotAboutFinishLockerId = checkClientIsNotAboutFinishLockerId(isAWoman);
//        for (int i = 0; i <availableLocker.size() ; i++) {
//            for (int j = 0; j <clientIsNotAboutFinishLockerId.size() ; j++) {
//                availableLocker.get(i)>clientIsNotAboutFinishLockerId.get()
//            }
//        }
//
//    }

//private boolean lockerSuitable(boolean isAWoman, Long lockerId){
//    Stream<Long> longStream = checkClientIsNotAboutFinishLockerId(isAWoman).stream()
//            .filter(l -> l > lockerId);
//
//}
