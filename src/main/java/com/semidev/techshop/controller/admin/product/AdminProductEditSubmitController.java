package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidImageId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.Product;
import com.semidev.techshop.model.entity.Image;
import com.semidev.techshop.model.service.ImageService;
import com.semidev.techshop.model.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Controller
public class AdminProductEditSubmitController {

    @PostMapping("/admin/product/edit-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int productId,
        @RequestParam(name="brand-id", required=true) int brandId,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="image-url", required=true) ArrayList<String> imageURLList,
        @RequestParam(name="price", required=true) float price,
        @RequestParam(name="discount", required=false, defaultValue="0") float discount,
        @RequestParam(name="promotion", required=false, defaultValue="") String promotion,
        @RequestParam(name="description", required=false, defaultValue="") String description,
        @RequestParam(name="specification", required=false, defaultValue="") String specification,
        @RequestParam(name="slug", required=true) String slug
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedId", productId);
                session.setAttribute("submittedBrandId", brandId);
                session.setAttribute("submittedName", name);
                session.setAttribute("submitedImageURL", imageURLList);
                session.setAttribute("submittedPrice", price);
                session.setAttribute("submittedDiscount", discount);
                session.setAttribute("submittedPromotion", promotion);
                session.setAttribute("submittedDescription", description);
                session.setAttribute("submittedSpecification", specification);
                session.setAttribute("submittedSlug", slug);
                var matchedSlugProduct = ProductService.selectProductBySlug(slug);
                if (matchedSlugProduct == null || matchedSlugProduct.getId() == productId) {
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var product = Product.createInstance(productId, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                    ProductService.updateProduct(product);
                    var oldImageList = ImageService.selectImageByProductId(productId);
                    for (var image : oldImageList) {
                        ImageService.deleteFromImage(image);
                    }
                    for (var imageURL : imageURLList) {
                        if (imageURL.isEmpty()) {
                            /* do nothing */
                        }
                        else {
                            var imageId = ImageService.selectLatestImageId() + 1;
                            var image = Image.createInstance(imageId, productId, imageURL);
                            ImageService.insertIntoImage(image);
                        }
                    }
                    session.setAttribute("editInfo", "Successfully updated product");
                    session.setAttribute("submittedId", null);
                    session.setAttribute("submittedBrandId", null);
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submitedImageURL", null);
                    session.setAttribute("submittedPrice", null);
                    session.setAttribute("submittedDiscount", null);
                    session.setAttribute("submittedPromotion", null);
                    session.setAttribute("submittedDescription", null);
                    session.setAttribute("submittedSpecification", null);
                    session.setAttribute("submittedSlug", null);
                    return "redirect:" + "/admin/product";
                }
                else {
                    session.setAttribute("editError", "Slug already existed");
                    return "redirect:" + "/admin/product/edit?id=" + productId;
                }
            }
            catch (ExceptionInvalidBrandId exc) {
                session.setAttribute("editError", "Invalid brand id");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidImageURL exc) {
                session.setAttribute("editError", "Invalid image URL");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductDescription exc) {
                session.setAttribute("editError", "Invalid product description");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductDiscount exc) {
                session.setAttribute("editError", "Invalid product discount");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductEditedBy exc) {
                session.setAttribute("editError", "Invalid product edited-by");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductEditedDate exc) {
                session.setAttribute("editError", "Invalid product edited-date");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductId exc) {
                session.setAttribute("editError", "Invalid product id");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidImageId exc) {
                session.setAttribute("editError", "Invalid product image id");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductName exc) {
                session.setAttribute("editError", "Invalid product name");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductPrice exc) {
                session.setAttribute("editError", "Invalid product price");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductSlug exc) {
                session.setAttribute("editError", "Invalid product slug");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionInvalidProductSpecification exc) {
                session.setAttribute("editError", "Invalid product specification");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (ExceptionNullProductPromotion exc) {
                session.setAttribute("editError", "The product promotion cannot be null");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
            catch (SQLException exc) {
                session.setAttribute("editError", "Failed to update product");
                return "redirect:" + "/admin/product/edit?id=" + productId;
            }
        }
    }
    
}
