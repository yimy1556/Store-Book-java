package com.storebook.storebook.controller;

import com.storebook.storebook.entity.Store;
import com.storebook.storebook.service.StoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /*
     * Devuelve un JSON con los datos de las librerías.
     */
    @GetMapping()
    public List<Map<String, Object>> storesFindAll() {

        return storeService.findAll().stream().map(Store::storeDTO).collect(Collectors.toList());
    }

    /*
     * Ingresa una nueva librería en la base de datos.
     */
    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveStore(@RequestBody Store store) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.save(store).storeDTO());
    }

    /*
     * Devuelve un JSON con los datos de la librería indicada por su ID.
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<Map<String, Object>> storeFindById(@PathVariable Long storeId) {
        Optional<Store> store = storeService.findById(storeId);

        if (store.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(store.get().storeDTO());
    }
    /*
     * Devuelve un JSON con los datos de la librería Borrada por su ID.
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Store> storeDelete(@PathVariable Long storeId) {
        Optional<Store> store = storeService.findById(storeId);

        if (store.isPresent()) return ResponseEntity.noContent().build();

        storeService.delateById(storeId);

        return ResponseEntity.ok().body(store.get());
    }
    /*
     * Devuelve un JSON con los datos de la librería actualizada.
     */
    @PatchMapping()
    public ResponseEntity<Map<String, Object>> storeUpdate(@RequestBody Store storeUpdate) {
        Optional<Store> optionalStore = storeService.findById(storeUpdate.getId());

        if (optionalStore.isPresent()) return ResponseEntity.noContent().build();

        BeanUtils.copyProperties(storeUpdate, optionalStore.get());

        return this.saveStore(optionalStore.get());
    }
}
