package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.local;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.StorageService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorageService implements StorageService {

    private final Map<String, Resource> storage;

    public InMemoryStorageService() {
        this.storage = new ConcurrentHashMap<>();
    }

    public void clear() {
        this.storage.clear();
    }

    public Map<String, Resource> storage() {
        return storage;
    }

    public void reset() {
        this.storage.clear();
    }

    @Override
    public void deleteAll(Collection<String> names) {
        names.forEach(this.storage::remove);
    }

    @Override
    public Optional<Resource> get(String name) {
        return Optional.ofNullable(this.storage.get(name));
    }

    @Override
    public List<String> list(String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }
        return this.storage.keySet().stream()
                .filter(it -> it.startsWith(prefix))
                .toList();
    }

    @Override
    public void store(String name, Resource resource) {
        this.storage.put(name, resource);
    }
}
