package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.*;
import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.UserFeedbackDto;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.dto.UserProductDto;
import com.shop.phoneshop.mappers.CatalogMapper;
import com.shop.phoneshop.mappers.ProductMapper;
import com.shop.phoneshop.mappers.UserFeedbackMapper;
import com.shop.phoneshop.mappers.UserProductMapper;
import com.shop.phoneshop.repos.*;
import com.shop.phoneshop.requests.FeedbackRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CatalogService {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final UserRepo userRepo;
    private final UserProductRepo userProductRepo;
    private final CookieService cookieService;
    private final UserFeedbackRepo userFeedbackRepo;
    private final PhotoRepo photoRepo;

    @Autowired
    public CatalogService(CategoryRepo categoryRepo, ProductRepo productRepo,
                          SubcategoryRepo subcategoryRepo, UserRepo userRepo,
                          UserProductRepo userProductRepo, CookieService cookieService,
                          UserFeedbackRepo userFeedbackRepo, PhotoRepo photoRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.subcategoryRepo = subcategoryRepo;
        this.userRepo = userRepo;
        this.userProductRepo = userProductRepo;
        this.cookieService = cookieService;
        this.userFeedbackRepo = userFeedbackRepo;
        this.photoRepo = photoRepo;
    }

    public CatalogDto getAllProducts(JwtAuthentication authentication) {
        List<Product> products = productRepo.findAll();
        return getCatalogDto(authentication, products);
    }

    private CatalogDto getCatalogDto(JwtAuthentication authentication, List<Product> products) {
        List<ProductDto> productDtos = products.stream()
                .map(product -> {
                    List<UserFeedback> userFeedbacks = product.getUserFeedbacks();
                    List<UserFeedbackDto> userFeedbackDtos = UserFeedbackMapper.fromUserFeedbacksToDtos(userFeedbacks);
                    return ProductMapper.fromProductToDto(product, authentication, userFeedbackDtos);
                })
                .collect(Collectors.toList());

        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            List<UserProduct> userProducts = userProductRepo.findAllByUser(user);
            List<UserProductDto> userProductDtos = UserProductMapper.fromUserProductsToDtos(userProducts, authentication);

            return CatalogMapper.fromProductDtosToCatalogDto(productDtos, userProductDtos);
        } else {
            Cookie[] cookies = cookieService.getAllCookies();
            List<UserProductDto> userProductDtos = new ArrayList<>();

            if (cookies == null) {
                return CatalogMapper.fromProductDtosToCatalogDto(productDtos, userProductDtos);
            }

            List<String> values = Arrays.stream(cookies)
                    .filter(c -> c.getName().contains("user_product_"))
                    .map(Cookie::getValue)
                    .toList();

            for (String value : values) {
                UserProductDto dto = new UserProductDto();
                JSONObject json = new JSONObject(URLDecoder.decode(value, StandardCharsets.UTF_8));
                dto.setPictureUrl((String) json.get("pictureUrl"));
                dto.setTitle((String) json.get("title"));
                dto.setPrice(((Number) json.get("price")).longValue());
                dto.setAmount(((Number) json.get("amount")).longValue());
                userProductDtos.add(dto);
            }

            return CatalogMapper.fromProductDtosToCatalogDto(productDtos, userProductDtos);
        }
    }

    public ProductDto getProduct(Long id, JwtAuthentication authentication) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        List<UserFeedback> userFeedbacks = product.getUserFeedbacks();
        List<UserFeedbackDto> userFeedbackDtos = UserFeedbackMapper.fromUserFeedbacksToDtos(userFeedbacks);

        return ProductMapper.fromProductToDto(product, authentication, userFeedbackDtos);
    }

    public ProductDto getProductFeedbacksSortedAscending(Long id, JwtAuthentication authentication) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        List<UserFeedback> userFeedbacks = product.getUserFeedbacks().stream()
                .sorted(Comparator.comparingLong(UserFeedback::getFeedback))
                .toList();
        List<UserFeedbackDto> userFeedbackDtos = UserFeedbackMapper.fromUserFeedbacksToDtos(userFeedbacks);

        return ProductMapper.fromProductToDto(product, authentication, userFeedbackDtos);
    }

    public ProductDto getProductFeedbacksSortedDescending(Long id, JwtAuthentication authentication) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        List<UserFeedback> userFeedbacks = product.getUserFeedbacks().stream()
                .sorted(Comparator.comparingLong(UserFeedback::getFeedback).reversed())
                .toList();
        List<UserFeedbackDto> userFeedbackDtos = UserFeedbackMapper.fromUserFeedbacksToDtos(userFeedbacks);

        return ProductMapper.fromProductToDto(product, authentication, userFeedbackDtos);
    }

    @Transactional
    public ProductDto addFeedback(FeedbackRequest request, JwtAuthentication authentication, Long id) {
        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            Product product = productRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

            UserFeedback userFeedback = new UserFeedback();
            userFeedback.setUser(user);
            userFeedback.setComment(request.getComment());
            userFeedback.setProduct(product);
            userFeedback.setFeedback(request.getFeedback());

            userFeedbackRepo.save(userFeedback);

            List<Photo> photos = new ArrayList<>();
            for(String picture: request.getPicturesUrls()) {
                Photo photo = new Photo();
                photo.setPictureUrl(picture);
                photo.setUserFeedback(userFeedback);
                photoRepo.save(photo);
                photos.add(photo);
            }
            userFeedback.setPhotos(photos);
            user.getUserFeedbacks().add(userFeedback);
//            product.getUserFeedbacks().add(userFeedback);
        }
        return getProduct(id, authentication);
    }

    public CatalogDto getAllProductsFromCategory(String title, JwtAuthentication authentication) {
        Category category = categoryRepo.findByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной категории не найдены"));
        List<Product> products = category.getSubcategories().stream()
                .flatMap(s -> s.getProducts().stream())
                .collect(Collectors.toList());
        return getCatalogDto(authentication, products);
    }

    public CatalogDto getAllProductsFromSubcategory(String title, JwtAuthentication authentication) {
        Subcategory subcategory = subcategoryRepo.findByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"));
        List<Product> products = new ArrayList<>(subcategory.getProducts());
        return getCatalogDto(authentication, products);
    }

    public CatalogDto getSmartphonesExtraProducts(JwtAuthentication authentication) {
        List<Product> headphones = subcategoryRepo.findByTitle("Headphones").orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"))
                .getProducts();
        List<Product> covers = subcategoryRepo.findByTitle("SmartphoneCovers").orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"))
                .getProducts();
        List<Product> products = new ArrayList<>(headphones);
        products.addAll(covers);
        return getCatalogDto(authentication, products);
    }
}
