/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package wishlist.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wishlist.exception.AlreadyInWishlist;
import wishlist.model.Wishlist;
import wishlist.service.WishlistService;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "wishlist")
@AllArgsConstructor
public class WishListController {

    private final WishlistService wishlistService;

    @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<Wishlist>> findAllItemsByCustomerId(HttpServletRequest request) {
        return new ResponseEntity<>(wishlistService.findAllProductsByCustomerId(request), HttpStatus.OK);
    }

    @PostMapping(path = "add/{id}")
    public ResponseEntity<Mono<?>> addProductToWishlist(@PathVariable("id") long productId, HttpServletRequest servletRequest) throws AlreadyInWishlist {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(productId, servletRequest));
    }

    @DeleteMapping(path = "delete")
    public ResponseEntity<Mono<Void>> deleteAllProductsFromWishlist(HttpServletRequest servletRequest) {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .lastModified(Instant.now())
                .body(wishlistService.deleteAllByEmail(servletRequest));

    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<Mono<Void>> deleteProductFromWishlist(@PathVariable("id") String id) {
        return ResponseEntity.ok(wishlistService.deleteItemFromWishlist(id));
    }
}
