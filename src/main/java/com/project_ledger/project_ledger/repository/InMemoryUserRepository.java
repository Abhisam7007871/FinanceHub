package com.project_ledger.project_ledger.repository;

import com.project_ledger.project_ledger.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.getAndIncrement());
        }
        users.put(user.getId(), user);
        usersByEmail.put(user.getEmail().toLowerCase(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email.toLowerCase()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(email.toLowerCase());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteById(Long id) {
        User user = users.remove(id);
        if (user != null) {
            usersByEmail.remove(user.getEmail().toLowerCase());
        }
    }

    @Override
    public boolean existsById(Long id) { return users.containsKey(id); }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        List<User> result = new ArrayList<>();
        ids.forEach(id -> findById(id).ifPresent(result::add));
        return result;
    }

    @Override
    public long count() { return users.size(); }

    @Override
    public void delete(User user) { deleteById(user.getId()); }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() { users.clear(); usersByEmail.clear(); }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            @SuppressWarnings("unchecked")
            S saved = (S) save(entity);
            result.add(saved);
        }
        return result;
    }

    @Override
    public void flush() {}

    @Override
    @SuppressWarnings("unchecked")
    public <S extends User> S saveAndFlush(S entity) { return (S) save(entity); }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) { return saveAll(entities); }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) { deleteAll(entities); }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) { deleteAllById(ids); }

    @Override
    public void deleteAllInBatch() { deleteAll(); }

    @Override
    @Deprecated
    public User getOne(Long id) { return getReferenceById(id); }

    @Override
    @Deprecated
    public User getById(Long id) { return getReferenceById(id); }

    @Override
    public User getReferenceById(Long id) { return findById(id).orElse(null); }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) { return Optional.empty(); }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) { return new ArrayList<>(); }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) { return new ArrayList<>(); }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public <S extends User> long count(Example<S> example) { return 0; }

    @Override
    public <S extends User> boolean exists(Example<S> example) { return false; }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<User> findAll(Sort sort) { return findAll(); }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return new PageImpl<>(findAll());
    }
}